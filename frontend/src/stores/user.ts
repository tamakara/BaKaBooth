import {defineStore} from 'pinia';
import type {LoginFormDTO, RegisterFormDTO, UserVO} from "@/types/user";
import {login, register, getUserVO} from "@/services/user.ts";

export const useUserStore = defineStore('user', {
    state: () => ({
        isLogged: false,
        user: null as UserVO | null,
    }),
    actions: {
        async login(loginForm: LoginFormDTO) {
            try {
                const token = await login(loginForm);
                localStorage.setItem('token', token);
                this.isLogged = true;
                // 登录成功后获取用户信息
                await this.fetchUserInfo();
                return token;
            } catch (error) {
                console.log('登录失败', error);
                throw error;
            }
        },

        async register(registerForm: RegisterFormDTO) {
            try {
                const token = await register(registerForm);
                localStorage.setItem('token', token);
                this.isLogged = true;
                // 注册成功后获取用户信息
                await this.fetchUserInfo();
                return token;
            } catch (error) {
                console.log('注册失败', error);
                throw error;
            }
        },

        async fetchUserInfo() {
            try {
                if (this.isLogged) {
                    this.user = await getUserVO();
                }
            } catch (error) {
                console.log('获取用户信息失败', error);
            }
        },

        async check() {
            const token = localStorage.getItem('token');
            if (!token) {
                this.isLogged = false;
                this.user = null;
                return false;
            }

            try {
                // 通过获取用户信息来验证token是否有效
                this.user = await getUserVO();
                this.isLogged = true;
                return true;
            } catch (error) {
                // token无效，清除本地存储
                console.log('Token已失效，请重新登录', error);
                this.logout();
                return false;
            }
        },

        logout() {
            localStorage.removeItem('token');
            this.isLogged = false;
            this.user = null;
        }
    },
    getters: {
        token: () => localStorage.getItem('token') || '',
        isLoggedIn: (state) => state.isLogged,
        currentUser: (state) => state.user
    }
});
