import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import {createPinia} from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/theme-chalk/index.css'
import {useUserStore} from '@/stores/user'

const app = createApp(App)
const pinia = createPinia()

app.use(pinia)
app.use(router)
app.use(ElementPlus)

// 应用启动时检查自动登录
const userStore = useUserStore()
userStore.check()
app.mount('#app')