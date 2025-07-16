import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router'

import HomePage from '@/components/HomePage/HomePage.vue'
import ItemPage from '@/components/ItemPage/index.vue'
import ShopPage from '@/components/ShopPage/index.vue'
import CartPage from '@/components/CartPage/index.vue'
import CheckoutPage from '@/components/CheckoutPage/index.vue'
import OrdersPage from '@/components/OrdersPage/index.vue'

const routes: RouteRecordRaw[] = [
    {path: '/', name: 'home', component: HomePage},
    {path: '/item/:id', name: 'item', component: ItemPage},
    {path: '/shop/:id', name: 'shop', component: ShopPage},
    {path: '/cart', name: 'cart', component: CartPage},
    {path: '/checkout', name: 'checkout', component: CheckoutPage},
    {path: '/orders', name: 'orders', component: OrdersPage},
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
