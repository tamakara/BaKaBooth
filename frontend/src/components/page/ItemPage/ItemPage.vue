<template>
  <ItemPageLayout>
    <template #content>
      <div class="product-details">
        <img :src="product.image" alt="Product image" />
        <div class="info">
          <h1>{{ product.name }}</h1>
          <p>{{ product.description }}</p>
          <p>{{ product.price }} 元</p>
          <button @click="addToCart(product)">加入购物车</button>
          <button @click="buyNow(product)">立即购买</button>
        </div>
      </div>
    </template>
  </ItemPageLayout>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue';
import ItemPageLayout from './ItemPageLayout.vue';
import { useRoute } from 'vue-router';

const product = ref({
  id: 0,
  name: '',
  description: '',
  price: 0,
  image: '',
});

const route = useRoute();

const fetchProduct = async (id: number) => {
  // 模拟 API 请求获取商品详情
  product.value = {
    id,
    name: '数字插画',
    description: '这是一幅数字插画。',
    price: 100,
    image: '/assets/product1.jpg',
  };
};

const addToCart = (product: any) => {
  console.log('添加到购物车', product);
};

const buyNow = (product: any) => {
  console.log('立即购买', product);
};

onMounted(() => {
  fetchProduct(parseInt(route.params.id as string));
});
</script>

<style scoped>
.product-details {
  display: flex;
}
.info {
  margin-left: 20px;
}
</style>
