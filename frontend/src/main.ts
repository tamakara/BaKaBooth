import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import {createPinia} from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import './style.css'
import {useUserStore} from "@/stores/user.ts";

const app = createApp(App)
app.use(createPinia())
app.use(router)
app.use(ElementPlus)

const userStore = useUserStore();
userStore.initializeUserStatus();
app.mount('#app')