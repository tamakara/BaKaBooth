import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router'

import HomePage from '@/components/page/HomePage/HomePage.vue'
import ItemPage from '@/components/page/ItemPage/ItemPage.vue'
import ShopPage from '@/components/page/ShopPage/ShopPage.vue'
import CartPage from '@/components/page/CartPage/CartPage.vue'
import CheckoutPage from '@/components/page/CheckoutPage/CheckoutPage.vue'
import OrdersPage from '@/components/page/OrdersPage/OrdersPage.vue'
import LoginPage from "@/components/page/LoginPage/LoginPage.vue";
import RegisterPage from "@/components/page/RegisterPage/RegisterPage.vue";
import FavoritesPage from "@/components/page/FavoritesPage/FavoritesPage.vue";
import ShopManagePage from "@/components/page/ShopManagePage/ShopManagePage.vue";
import ShopManageItemPage from "@/components/page/ShopManageItemPage/ShopManageItemPage.vue";
import ItemEditPage from "@/components/page/ItemEditPage/ItemEditPage.vue";

const routes: RouteRecordRaw[] = [
    {path: '/', name: 'home', component: HomePage},

    {path: '/item/:id', name: 'item', component: ItemPage},
    {path: '/item/:id/edit', name: 'item-edit', component: ItemEditPage},

    {path: '/user/login', name: 'login', component: LoginPage},
    {path: '/user/register', name: 'register', component: RegisterPage},
    {path: '/user/favorites', name: 'favorites', component: FavoritesPage},

    {path: '/shop/:id', name: 'shop', component: ShopPage},

    {path: '/shop/manage', name: 'shop-manage', component: ShopManagePage},
    {path: '/shop/manage/item', name: 'shop-manage-item', component: ShopManageItemPage},

    {path: '/cart', name: 'cart', component: CartPage},
    {path: '/checkout', name: 'checkout', component: CheckoutPage},
    {path: '/orders', name: 'orders', component: OrdersPage},
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
