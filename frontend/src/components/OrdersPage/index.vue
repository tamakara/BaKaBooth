<template>
  <OrdersPageLayout>
    <template #content>
      <div class="order-list">
        <h2>历史订单</h2>
        <div class="order-item" v-for="order in orders" :key="order.id">
          <p>订单号：{{ order.id }}</p>
          <p>商品：{{ order.product }}</p>
          <p>状态：{{ order.status }}</p>
        </div>
      </div>
    </template>
  </OrdersPageLayout>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import OrdersPageLayout from './OrdersPageLayout.vue';

const orders = ref([
  { id: 1, product: '数字插画', status: '已完成' },
  { id: 2, product: '3D 模型', status: '待支付' },
]);
</script>

<style scoped>
.order-list {
  display: flex;
  flex-direction: column;
}
.order-item {
  margin-bottom: 20px;
}
</style>
