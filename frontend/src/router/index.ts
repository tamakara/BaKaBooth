import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router'

// 导入页面组件
import HomePage from '@/views/HomePage/HomePage.vue'
import ItemPage from '@/views/ItemPage/ItemPage.vue'
import ItemEditPage from '@/views/ItemEditPage/ItemEditPage.vue'
import LoginPage from '@/views/LoginPage/LoginPage.vue'
import RegisterPage from '@/views/RegisterPage/RegisterPage.vue'
import FavoritesPage from '@/views/FavoritesPage/FavoritesPage.vue'
import UserPage from '@/views/UserPage/UserPage.vue'
import UserEditPage from '@/views/UserEditPage/UserEditPage.vue'
import ItemManagePage from '@/views/ItemManagePage/ItemManagePage.vue'
import OrderPage from '@/views/OrderPage/OrderPage.vue'

const routes: RouteRecordRaw[] = [
    {path: '/', name: 'home', component: HomePage},

    {path: '/item/:id', name: 'item', component: ItemPage},
    {path: '/item/:id/edit', name: 'item-edit', component: ItemEditPage},

    {path: '/user/login', name: 'login', component: LoginPage},
    {path: '/user/register', name: 'register', component: RegisterPage},
    {path: '/user/favorites', name: 'favorites', component: FavoritesPage},

    {path: '/user/:id', name: 'user', component: UserPage},

    {path: '/user/edit', name: 'user-edit', component: UserEditPage},
    {path: '/item/manage', name: 'item-manage', component: ItemManagePage},

    {path: '/order/:id', name: 'order', component: OrderPage},
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
