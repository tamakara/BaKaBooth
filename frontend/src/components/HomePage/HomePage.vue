<template>
  <HomePageLayout>
    <template #1>
      <el-text class="section-title">
        关注店铺的最新商品
      </el-text>
      <ItemGrid
          :max-rows="2"
          :max-columns="5"
          :gap="16"
          :items="newItems"
      />
    </template>

    <template #2>
      <el-text class="section-title">
        热销商品
      </el-text>
      <ItemGrid
          :max-rows="4"
          :max-columns="5"
          :gap="16"
          :items="newItems"
      />
    </template>
  </HomePageLayout>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import type {Item} from "@/types/ItemTypes.ts"
import HomePageLayout from "@/components/HomePage/HomePageLayout.vue";
import ItemGrid from "@/components/common/ItemGrid.vue";

const newItems = ref<Item[]>([
  {
    itemId: '1',
    itemName: '你好你好你好你你好你好你好你好好你好你好你好你好你好你好你好',
    coverUrl: './src/assets/test.jpg',
    shopId: 'shop1',
    shopName: '店铺xxxxxxxxxxxVB店铺SVASVAVSAVASVxxA',
    favorite: 100,
    price: 99.9,
    sales: 50
  },
  {
    itemId: '2',
    itemName: '新商品2',
    coverUrl: './src/assets/test.jpg',
    shopId: 'shop2',
    shopName: '店铺B',
    favorite: 200,
    price: 149.9,
    sales: 150
  },
  {
    itemId: '2',
    itemName: '新商品2',
    coverUrl: './src/assets/test.jpg',
    shopId: 'shop2',
    shopName: '店铺B',
    favorite: 200,
    price: 149.9,
    sales: 150
  },
  {
    itemId: '2',
    itemName: '新商品2',
    coverUrl: './src/assets/test.jpg',
    shopId: 'shop2',
    shopName: '店铺B',
    favorite: 200,
    price: 149.9,
    sales: 150
  }
  , {
    itemId: '2',
    itemName: '新商品2',
    coverUrl: './src/assets/test.jpg',
    shopId: 'shop2',
    shopName: '店铺B',
    favorite: 200,
    price: 149.9,
    sales: 150
  },
  {
    itemId: '2',
    itemName: '新商品2',
    coverUrl: './src/assets/test.jpg',
    shopId: 'shop2',
    shopName: '店铺B',
    favorite: 200,
    price: 149.9,
    sales: 150
  },
  {
    itemId: '2',
    itemName: '新商品2',
    coverUrl: './src/assets/test.jpg',
    shopId: 'shop2',
    shopName: '店铺B',
    favorite: 200,
    price: 149.9,
    sales: 150
  },
  {
    itemId: '2',
    itemName: '新商品2',
    coverUrl: './src/assets/test.jpg',
    shopId: 'shop2',
    shopName: '店铺B',
    favorite: 200,
    price: 149.9,
    sales: 150
  }
])
</script>

<style scoped>

.section-title {
  font-size: 26px;
  font-weight: bold;

}
</style>
