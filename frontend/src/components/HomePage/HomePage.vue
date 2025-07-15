<template>
  <HomePageLayout>
    <div class="featured-products">
      <h2>热门商品</h2>
      <div class="product-list">
        <div v-for="product in featuredProducts" :key="product.id" class="product-card">
          <img :src="product.image" alt="Product image"/>
          <p>{{ product.name }}</p>
          <p>{{ product.price }} 元</p>
          <button @click="goToProduct(product.id)">查看详情</button>
        </div>
      </div>
    </div>
  </HomePageLayout>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import HomePageLayout from './HomePageLayout.vue';
import {useRouter} from 'vue-router';

const featuredProducts = ref([
  {id: 1, name: '数字插画', price: 50, image: '/assets/product1.jpg'},
  {id: 2, name: '3D 模型', price: 120, image: '/assets/product2.jpg'},
]);

const router = useRouter();

const goToProduct = (productId: number) => {
  router.push(`/product/${productId}`);
};
</script>

<style scoped>
.featured-products {
  display: flex;
  flex-direction: column;
}

.product-list {
  display: flex;
  flex-wrap: wrap;
}

.product-card {
  width: 200px;
  margin: 10px;
}
</style>
