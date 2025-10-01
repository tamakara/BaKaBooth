<template>
  <CheckoutPage>
    <template #content>
      <div class="checkout-items">
        <h2>结算</h2>
        <div v-for="item in cartItems" :key="item.id" class="checkout-item">
          <img :src="item.image" alt="Item image" />
          <p>{{ item.name }}</p>
          <p>{{ item.price }} 元</p>
        </div>
        <div>
          <p>总价：{{ totalPrice }} 元</p>
          <button @click="placeOrder">提交订单</button>
        </div>
      </div>
    </template>
  </CheckoutPage>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import CheckoutPage from './CheckoutPage.vue';
import { useRouter } from 'vue-router';

const cartItems = ref([
  { id: 1, name: '数字插画', price: 50, image: '/assets/product1.jpg' },
  { id: 2, name: '3D 模型', price: 120, image: '/assets/product2.jpg' },
]);

const totalPrice = ref(cartItems.value.reduce((total, item) => total + item.price, 0));

const router = useRouter();

const placeOrder = () => {
  console.log('提交订单');
  router.push('/orders');
};
</script>

<style scoped>
.checkout-items {
  display: flex;
  flex-direction: column;
}
.checkout-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}
</style>
