<template>
  <el-card :body-style="{ padding: '20px' }">
    <el-image
        :src="item.coverUrl"
        lazy
        fit="cover"
        style="width: 100%; height: 200px; border-radius: 8px;"
    />
    <div class="item-info">
      <h3>{{ item.itemName }}</h3>
      <p class="price">￥{{ item.price }}</p>
      <div class="item-stats">
        <span>收藏: {{ item.favorite }}</span>
        <span>销量: {{ item.sales }}</span>
      </div>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import type { Item } from "@/types/ItemTypes.ts";

const props = defineProps<{
  item: Item;
}>();
</script>

<style scoped>
.item-info {
  padding-top: 10px;
}

h3 {
  font-size: 16px;
  font-weight: 500;
  margin-bottom: 10px;
}

.price {
  color: #f56c6c;
  font-size: 18px;
  font-weight: 600;
}

.item-stats {
  font-size: 12px;
  color: #999;
  display: flex;
  justify-content: space-between;
  margin-top: 5px;
}
</style>
