<template>
  <div class="item-card">
    <el-image
        class="item-cover"
        :src="item.coverUrl"
        fit="cover"
        lazy
        @click="goToItemPage"
    />
    <div class="item-info">
      <el-text
          class="title"
          tag="a"
          @click="goToItemPage"
      >
        {{ item.itemName }}
      </el-text>

      <el-text
          class="shop"
          tag="a"
          @click="goToShopPage"
          truncated
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
            :style="{ color: isFavorited ? '#FF5733' : '#B0B0B0' }"
            @click="toggleFavorite"
        >
          <template #icon>
            <el-icon>
              <StarFilled v-if="isFavorited" style="color: #FF5733;"/>
              <Star v-else style="color: #B0B0B0;"/>
            </el-icon>
          </template>
          {{ favoriteCount }}
        </el-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type {Item} from '@/types/ItemTypes.ts';
import {Star, StarFilled} from '@element-plus/icons-vue'
import {ref} from "vue";
import {useRouter} from "vue-router";

const props = defineProps<{
  item: Item;
}>();

const router = useRouter();

const favoriteCount = ref(props.item.favorite)
const isFavorited = ref(false);

const toggleFavorite = () => {
  isFavorited.value = !isFavorited.value;
  if (isFavorited.value) {
    favoriteCount.value += 1;
  } else {
    favoriteCount.value -= 1;
  }
};

const goToItemPage = () => {
  router.push({name: 'item', params: {id: props.item.itemId}});
};
const goToShopPage = () => {
  router.push({name: 'shop', params: {id: props.item.shopId}});
};
</script>

<style scoped>
.item-card {
  width: 170px;
  height: 270px;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
  overflow: hidden;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.item-cover {
  height: 170px;
  cursor: pointer;
  border-bottom: 1px solid #f0f0f0;
}

.item-info {
  flex: 2;
  display: flex;
  flex-direction: column;
  align-items: start;
  padding: 0 5px;
}

.title {
  width: 100%;
  font-size: 14px;
  color: #333;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  flex: 2;
  cursor: pointer;
}

.shop {
  width: 100%;
  font-size: 12px;
  color: #666;
  flex: 1;
  cursor: pointer;
  margin: 2px 0;
}

.price-and-favorite {
  flex: 1;
  display: flex;
  justify-content: space-between;
  width: 100%;
  align-items: center;
}

.price {
  color: #FF5733;
  font-size: 20px;
  font-weight: bold;
  height: 100%;
}

.favorite-button {
  font-size: 16px;
  padding: 0;
  border: none;
  background: none;
  color: #B0B0B0;
  transition: color 0.3s ease;
}
</style>
