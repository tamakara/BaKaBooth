<template>
  <HomePageLayout>
    <div class="home-page">
      <div class="section">
        <h2>关注店铺的新商品</h2>
        <div class="item-list">
          <ItemCard
              v-for="item in newItems"
              :key="item.itemId"
              :item="item"
          />
        </div>
      </div>

      <div class="section">
        <h2>热销商品</h2>
        <div class="item-list">
          <ItemCard
              v-for="item in hotItems"
              :key="item.itemId"
              :item="item"
          />
        </div>
      </div>

      <div class="section">
        <h2>最新商品</h2>
        <div class="item-list">
          <ItemCard
              v-for="item in latestItems"
              :key="item.itemId"
              :item="item"
          />
        </div>
      </div>
    </div>
  </HomePageLayout>
</template>

<script setup lang="ts">
import {ref} from 'vue'
import ItemCard from '@/components/common/IteamCard.vue'
import type {Item} from "@/types/ItemTypes.ts"
import HomePageLayout from "@/components/HomePage/HomePageLayout.vue";

const newItems = ref<Item[]>([
  {
    itemId: '1',
    itemName: 'aaaaaaaavavavavaaaabbbbbbbbbbbbbbcccccccccddddddd',
    coverUrl: './src/assets/test.jpg',
    shopId: 'shop1',
    shopName: '店铺A',
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
  }, {
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

const hotItems = ref<Item[]>([
  {
    itemId: '3',
    itemName: '热销商品1',
    coverUrl: 'https://example.com/hot1.jpg',
    shopId: 'shop1',
    shopName: '店铺A',
    favorite: 500,
    price: 199.9,
    sales: 300
  },
  {
    itemId: '4',
    itemName: '热销商品2',
    coverUrl: 'https://example.com/hot2.jpg',
    shopId: 'shop2',
    shopName: '店铺B',
    favorite: 700,
    price: 299.9,
    sales: 450
  }
])

const latestItems = ref<Item[]>([
  {
    itemId: '5',
    itemName: '最新商品1',
    coverUrl: 'https://example.com/latest1.jpg',
    shopId: 'shop1',
    shopName: '店铺A',
    favorite: 10,
    price: 49.9,
    sales: 20
  },
  {
    itemId: '6',
    itemName: '最新商品2',
    coverUrl: 'https://example.com/latest2.jpg',
    shopId: 'shop2',
    shopName: '店铺B',
    favorite: 50,
    price: 79.9,
    sales: 80
  }
])
</script>

<style scoped>
.home-page {
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 200px;
}

.section {

}

h2 {
  font-size: 20px;
  font-weight: bold;
}

.item-list {
  display: flex;
  gap: 20px;
  flex-wrap: wrap;
}
</style>
