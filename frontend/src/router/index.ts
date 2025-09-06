import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router'

import HomePage from '@/components/page/HomePage/HomePage.vue'
import ItemInfoPage from '@/components/page/ItemInfoPage/ItemInfoPage.vue'
import ShopPage from '@/components/page/ShopPage/ShopPage.vue'
import CheckoutPage from '@/components/page/CheckoutPage/CheckoutPage.vue'
import OrdersPage from '@/components/page/OrdersPage/OrdersPage.vue'
import LoginPage from "@/components/page/LoginPage/LoginPage.vue";
import RegisterPage from "@/components/page/RegisterPage/RegisterPage.vue";
import FavoritesPage from "@/components/page/FavoritesPage/FavoritesPage.vue";
import ShopManagePage from "@/components/page/ShopManagePage/ShopManagePage.vue";
import ItemManagePage from "@/components/page/ItemManagePage/ItemManagePage.vue";
import ItemEditPage from "@/components/page/ItemEditPage/ItemEditPage.vue";

const routes: RouteRecordRaw[] = [
    {path: '/', name: 'home', component: HomePage},

    {path: '/item/:id', name: 'item', component: ItemInfoPage},
    {path: '/item/:id/edit', name: 'item-edit', component: ItemEditPage},

    {path: '/user/login', name: 'login', component: LoginPage},
    {path: '/user/register', name: 'register', component: RegisterPage},
    {path: '/user/favorites', name: 'favorites', component: FavoritesPage},

    {path: '/shop/:id', name: 'shop', component: ShopPage},

    {path: '/shop/manage', name: 'shop-manage', component: ShopManagePage},
    {path: '/shop/manage/item', name: 'shop-manage-item', component: ItemManagePage},

    {path: '/checkout/:id', name: 'checkout', component: CheckoutPage},
    {path: '/orders/:id', name: 'orders', component: OrdersPage},
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
