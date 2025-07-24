import {defineStore} from 'pinia';
import type {LoginForm, UserStatus} from "@/types/user";
import {login} from "@/api/user.ts";

export const useUserStore = defineStore('user', {
    state: () => ({
        userStatus: null as UserStatus | null,
    }),
    actions: {
        initializeUserStatus() {
            const storedUserStatus = localStorage.getItem('user-status');
            if (storedUserStatus) {
                this.userStatus = JSON.parse(storedUserStatus);
            }
        },
        async submitLoginForm(loginForm: LoginForm) {
            try {
                this.userStatus = await login(loginForm);
                localStorage.setItem('user-status', JSON.stringify(this.userStatus));
            } catch (error) {
                console.log('登录失败', error);
            }
        },
        clearUser() {
            this.userStatus = null;
            localStorage.removeItem('user-status');
        }
    },
    getters: {
        isLogged(state) {
            return state.userStatus !== null
        }
    }
});
