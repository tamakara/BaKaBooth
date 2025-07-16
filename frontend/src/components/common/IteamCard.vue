<template>
  <div class="item-card">
    <el-image
        class="item-cover"
        :src="item.coverUrl"
        fit="cover"
        lazy
    />
    <div class="item-info">
      <el-text
          class="title"
          truncated
      >
        {{ item.itemName }}
      </el-text>
      <el-text
          class="price"
      >
        ￥{{ item.price }}
      </el-text>
      <div class="item-stats">
        <span>收藏: {{ item.favorite }}</span>
        <span>销量: {{ item.sales }}</span>
      </div>

    </div>
  </div>
</template>

<script setup lang="ts">
import type {Item} from "@/types/ItemTypes.ts";

const props = defineProps<{
  item: Item;
}>();
</script>

<style scoped>
.item-card {
  width: 200px;
  height: 400px;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.item-cover {
  flex: 4;

}


.item-info {
  flex: 3;

}

.title {
  font-size: 20px;
  font-weight: 500;
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
