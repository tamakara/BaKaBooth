import {defineStore} from 'pinia';
import type {LoginForm, UserInfo} from "@/types/user";
import {login} from "@/api/user.ts";

export const useUserStore = defineStore('user', {
    state: () => ({
        isLogged: false,
        userInfo: null as UserInfo | null,
    }),
    actions: {
        async submitLoginForm(loginForm: LoginForm) {
            try {
                const token = await login(loginForm);
                localStorage.setItem('token', token);
                this.isLogged = true
            } catch (error) {
                console.log('登录失败', error);
            }
        },
        clearUser() {
            localStorage.removeItem('token');
            this.isLogged = false
        }
    },
    getters: {}
});
