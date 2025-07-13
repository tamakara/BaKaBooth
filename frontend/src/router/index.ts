import {createRouter, createWebHistory, type RouteRecordRaw} from 'vue-router'

import HomePage from '@/components/HomePage/index.vue'
import ProductPage from '@/components/ProductPage/index.vue'
import ShopPage from '@/components/ShopPage/index.vue'
import CartPage from '@/components/CartPage/index.vue'
import CheckoutPage from '@/components/CheckoutPage/index.vue'
import OrdersPage from '@/components/OrdersPage/index.vue'

const routes: RouteRecordRaw[] = [
    {path: '/', name: 'Home', component: HomePage},
    {path: '/product/:uuid', name: 'Product', component: ProductPage},
    {path: '/shop/:username', name: 'ShopPage', component: ShopPage},
    {path: '/cart', name: 'Cart', component: CartPage},
    {path: '/checkout', name: 'Checkout', component: CheckoutPage},
    {path: '/orders', name: 'Orders', component: OrdersPage},
]

const router = createRouter({
    history: createWebHistory(),
    routes
})

export default router
