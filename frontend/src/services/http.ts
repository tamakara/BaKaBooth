// frontend/src/services/http.ts
import axios from 'axios';

const baseURL = 'http://localhost:8080';

const http = axios.create({
    baseURL,
    timeout: 15000,
});

http.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        if (config.headers && typeof (config.headers as any).set === 'function') {
            (config.headers as any).set('Authorization', `Bearer ${token}`);
        } else {
            config.headers = config.headers ?? {};
            (config.headers as Record<string, string>)['Authorization'] = `Bearer ${token}`;
        }
    }
    return config;
});

http.interceptors.response.use(
    (resp) => resp,
    (error) => {
        const status = error?.response?.status;
        if (status === 401) {
            localStorage.removeItem('token');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    },
);

export default http;