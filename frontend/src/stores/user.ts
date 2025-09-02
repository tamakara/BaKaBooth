import {defineStore} from 'pinia';
import type {LoginFormDTO, RegisterFormDTO} from "@/types/UserTypes";
import {login, register} from "@/api/user.ts";

export const useUserStore = defineStore('user', {
    state: () => ({
        isLogged: false,
    }),
    actions: {
        async login(loginForm: LoginFormDTO) {
            try {
                const token = await login(loginForm);
                localStorage.setItem('token', token);
                this.isLogged = true;
            } catch (error) {
                console.log('登录失败', error);
            }
        },

        async register(registerForm: RegisterFormDTO) {
            try {
                const token = await register(registerForm);
                localStorage.setItem('token', token);
                this.isLogged = true;
            } catch (error) {
                console.log('注册失败', error);
            }
        },

        async check() {
            this.isLogged = localStorage.getItem('token') !== null
            return this.isLogged
        },

        logout() {
            localStorage.removeItem('token');
            this.isLogged = false;
        }
    },
    getters: {
        token: () => localStorage.getItem('token') || ''
    }
});
