import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
import VueDevtools from 'vite-plugin-vue-devtools'

export default defineConfig({
    plugins: [vue(), VueDevtools()],
    resolve: {
        alias: {
            '@': path.resolve(__dirname, './src')
        }
    }
})