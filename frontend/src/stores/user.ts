import {defineStore} from 'pinia';
import type {LoginForm} from "@/types/UserTypes";
import {login} from "@/api/user.ts";

export const useUserStore = defineStore('user', {
    state: () => ({
        isLogged: false,
    }),
    actions: {
        async login(loginForm: LoginForm) {
            try {
                const token = await login(loginForm);
                localStorage.setItem('token', token);
                this.isLogged = true;
            } catch (error) {
                console.log('登录失败', error);
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
    getters: {}
});
