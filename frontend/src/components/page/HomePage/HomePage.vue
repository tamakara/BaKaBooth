<template>
  <HomePageLayout>
    <template #section1-title>
      <div class="title-with-icon">
        <el-icon class="title-icon"><StarIcon /></el-icon>
        关注店铺
        <span class="title-subtitle">来自您关注店铺的最新商品</span>
      </div>
    </template>
    <template #section1>
      <div class="section-container">
        <ItemGrid
          :max-rows="2"
          :max-columns="5"
          :gap="20"
          :items="newItems"
          class="item-grid"
        />
      </div>
    </template>

    <template #section2-title>
      <div class="title-with-icon">
        <el-icon class="title-icon"><FireIcon /></el-icon>
        热销商品
        <span class="title-subtitle">最受欢迎的精选商品</span>
      </div>
    </template>
    <template #section2>
      <div class="section-container">
        <ItemGrid
          :max-rows="4"
          :max-columns="5"
          :gap="20"
          :items="newItems"
          class="item-grid"
        />
      </div>
    </template>
  </HomePageLayout>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import type {ItemDisplayVO} from "@/types/ItemTypes.d.ts"
import HomePageLayout from "@/components/page/HomePage/HomePageLayout.vue";
import ItemGrid from "@/components/common/ItemGrid.vue";
import {StarIcon, FireIcon} from "@heroicons/vue/24/outline";

const newItems = ref<ItemDisplayVO[]>([
  {
    itemId: '1',
    itemName: '你好你好��好你你好你好你好你好好你好你好你好你好你好你好你好',
    coverUrl: './src/assets/test1.jpg',
    shopId: 222,
    shopName: '店铺xxxxxxxxxxxVB店铺SVASVAVSAVASVxxA',
    favorite: 100,
    minPrice: 99.9,
    maxPrice: 99.9,
  },
  {
    itemId: '2',
    itemName: '新商品2',
    coverUrl: './src/assets/test.jpg',
    shopId: 222,
    shopName: '店铺B',
    favorite: 200,
    minPrice: 149.9,
    maxPrice: 99.9,
  },
  {
    itemId: '3',
    itemName: '新商品3',
    coverUrl: './src/assets/test.jpg',
    shopId: 222,
    shopName: '店铺C',
    favorite: 200,
    minPrice: 149.9,
    maxPrice: 99.9,
  },
  {
    itemId: '4',
    itemName: '新商品4',
    coverUrl: './src/assets/test.jpg',
    shopId: 222,
    shopName: '店铺D',
    favorite: 200,
    minPrice: 149.9,
    maxPrice: 99.9,
  },
  {
    itemId: '5',
    itemName: '我f我aewfwafg我awgawfawfawfa我wfafawfafwafdaefegfwsefwsefwfe',
    coverUrl: './src/assets/test.jpg',
    shopId: 222,
    shopName: '店铺E',
    favorite: 200,
    minPrice: 149.9,
    maxPrice: 99.9,
  },
  {
    itemId: '6',
    itemName: '新商品6',
    coverUrl: './src/assets/test.jpg',
    shopId: 222,
    shopName: '店铺F',
    favorite: 200,
    minPrice: 149.9,
    maxPrice: 99.9,
  },
  {
    itemId: '7',
    itemName: '新商品7',
    coverUrl: './src/assets/test.jpg',
    shopId: 222,
    shopName: '店铺G',
    favorite: 200,
    minPrice: 149.9,
    maxPrice: 99.9,
  },
  {
    itemId: '8',
    itemName: '新商品8',
    coverUrl: './src/assets/test.jpg',
    shopId: 222,
    shopName: '店铺H',
    favorite: 200,
    minPrice: 149.9,
    maxPrice: 99.9,
  }
])
</script>

<style scoped>
.title-with-icon {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
}

.title-icon {
  color: #409eff;
  font-size: 32px;
}

.title-subtitle {
  font-size: 14px;
  font-weight: 400;
  color: #909399;
  margin-top: 4px;
}

.section-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.item-grid {
  width: 100%;
  max-width: 1000px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .title-icon {
    font-size: 28px;
  }

  .title-subtitle {
    font-size: 12px;
  }
}
</style>
