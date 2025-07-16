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
          tag="a"
          href="http://localhost:5173/"
      >
        {{ item.itemName }}
      </el-text>

      <el-text
          class="shop"
      >
        {{ item.shopName }}
      </el-text>

      <div class="price-and-favorite">
        <el-text class="price">
          ￥{{ item.price }}
        </el-text>
        <el-button
            type="text"
            class="favorite-button"
            :style="{ color: isFavorited ? 'red' : '#ccc' }"
            @click="toggleFavorite"
        >
          <template #icon>
            <el-icon>
              <StarFilled v-if="isFavorited"/>
              <Star v-else/>
            </el-icon>
          </template>
          {{ item.favorite }}
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type {Item} from '@/types/ItemTypes.ts';
import {Star, StarFilled} from '@element-plus/icons-vue'
import {ref} from "vue";

const props = defineProps<{
  item: Item;
}>();

const favoriteCount = ref(0); // 收藏数
const isFavorited = ref(false); // 是否已收藏

// 切换收藏状态
const toggleFavorite = () => {
  isFavorited.value = !isFavorited.value;  // 切换收藏状态
  if (isFavorited.value) {
    favoriteCount.value += 1;  // 收藏数加1
  } else {
    favoriteCount.value -= 1;  // 收藏数减1
  }
};
</script>

<style scoped>
.item-card {
  width: 180px;
  height: 300px;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.item-cover {
  flex: 4;
}

.item-info {
  flex: 2;
  display: flex;
  flex-direction: column;
  align-items: start;
}

.title {
  width: 100%;
  font-weight: bold;
  font-size: 18px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  flex: 2;
}

.shop {
  width: 100%;
  font-size: 16px;
  flex: 1;
}

.price-and-favorite {
  flex: 1;
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.price {
  color: #f56c6c;
  font-size: 20px;
  font-weight: bold;
  height: 100%;
}

.favorite-button {
  font-size: 16px;
}
</style>
