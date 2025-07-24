import axios from 'axios';

const baseUrl = 'http://localhost:8080';

const axiosInstance = axios.create({
    baseURL: baseUrl,
    timeout: 5000,
});

axiosInstance.interceptors.request.use(config => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default axiosInstance;