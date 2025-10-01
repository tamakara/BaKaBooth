import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router'

import HomePage from '@/views/HomePage/HomePage.vue'
import ItemInfoPage from '@/views/ItemInfoPage/ItemInfoPage.vue'
import ShopPage from '@/views/ShopPage/ShopPage.vue'
import CheckoutPage from '@/views/CheckoutPage/CheckoutPage.vue'
import LoginPage from "@/views/LoginPage/LoginPage.vue";
import RegisterPage from "@/views/RegisterPage/RegisterPage.vue";
import FavoritesPage from "@/views/FavoritesPage/FavoritesPage.vue";
import ShopManagePage from "@/views/ShopManagePage/ShopManagePage.vue";
import ItemManagePage from "@/views/ItemManagePage/ItemManagePage.vue";
import ItemEditPage from "@/views/ItemEditPage/ItemEditPage.vue";
import OrderEditPage from "@/views/OrderEditPage/OrderEditPage.vue";

const routes: RouteRecordRaw[] = [
    {path: '/', name: 'home', component: HomePage},

    {path: '/item/:id', name: 'item', component: ItemInfoPage},
    {path: '/item/:id/edit', name: 'item-edit', component: ItemEditPage},

    {path: '/user/login', name: 'login', component: LoginPage},
    {path: '/user/register', name: 'register', component: RegisterPage},
    {path: '/user/favorites', name: 'favorites', component: FavoritesPage},

    {path: '/shop/:id', name: 'shop', component: ShopPage},

    {path: '/shop/manage', name: 'shop-manage', component: ShopManagePage},
    {path: '/item/manage', name: 'item-manage', component: ItemManagePage},

    {path: '/checkout/:id', name: 'checkout', component: CheckoutPage},
    {path: '/order/:id', name: 'order-edit', component: OrderEditPage},
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
