<template>
  <ShopPageLayout>
    <template #left-sidebar>
      <div class="shop-info">
        <h3>{{ shop.name }}</h3>
        <p>{{ shop.description }}</p>
        <p>店铺公告：{{ shop.announcement }}</p>
      </div>
    </template>

    <template #content>
      <div class="product-list">
        <h2>{{ shop.name }} 的商品</h2>
        <div class="product-item" v-for="product in shop.products" :key="product.id">
          <img :src="product.image" alt="Product image" />
          <p>{{ product.name }}</p>
          <p>{{ product.price }} 元</p>
          <button @click="goToProduct(product.id)">查看详情</button>
        </div>
      </div>
    </template>
  </ShopPageLayout>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import ShopPageLayout from './ShopPageLayout.vue';
import { useRouter } from 'vue-router';

const shop = ref({
  name: '数字商品店',
  description: '专注于高质量数字商品的交易。',
  announcement: '所有商品七天无理由退换。',
  products: [
    { id: 1, name: '数字插画', price: 50, image: '/assets/product1.jpg' },
    { id: 2, name: '3D 模型', price: 120, image: '/assets/product2.jpg' },
  ]
});

const router = useRouter();
const goToProduct = (productId: number) => {
  router.push(`/product/${productId}`);
};
</script>

<style scoped>
.product-list {
  display: flex;
  flex-wrap: wrap;
}
.product-item {
  width: 200px;
  margin: 10px;
}
</style>
