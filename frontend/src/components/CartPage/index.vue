<template>
  <CartPageLayout>
    <template #content>
      <div class="cart-items">
        <h2>购物车</h2>
        <div class="cart-item" v-for="item in cartItems" :key="item.id">
          <img :src="item.image" alt="Item image" />
          <p>{{ item.name }}</p>
          <p>{{ item.price }} 元</p>
          <button @click="removeFromCart(item.id)">移除</button>
        </div>
        <div v-if="cartItems.length > 0">
          <p>总价：{{ totalPrice }} 元</p>
          <button @click="goToCheckout">结算</button>
        </div>
        <div v-else>
          <p>购物车为空</p>
        </div>
      </div>
    </template>
  </CartPageLayout>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import CartPageLayout from './CartPageLayout.vue';
import { useRouter } from 'vue-router';

const cartItems = ref([
  { id: 1, name: '数字插画', price: 50, image: '/assets/product1.jpg' },
  { id: 2, name: '3D 模型', price: 120, image: '/assets/product2.jpg' },
]);

const totalPrice = ref(cartItems.value.reduce((total, item) => total + item.price, 0));

const router = useRouter();

const removeFromCart = (id: number) => {
  cartItems.value = cartItems.value.filter(item => item.id !== id);
  totalPrice.value = cartItems.value.reduce((total, item) => total + item.price, 0);
};

const goToCheckout = () => {
  router.push('/checkout');
};
</script>

<style scoped>
.cart-items {
  display: flex;
  flex-direction: column;
}
.cart-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}
</style>
