<template>
  <div class="header-bar test">
    <el-button class="logo" @click="goToHome">
      <span class="logo-text test">BaKaBooth</span>
    </el-button>

    <el-input class="search-container test" type="text" placeholder="搜索商品"/>


    <div class="user-actions test">
      <el-button
          :icon="ShoppingCart"
          class="icon-button"
          @click="goToCart"
      ></el-button>
      <el-button
          :icon="Star"
          class="icon-button"
          @click="goToFavorites"
      ></el-button>
      <el-button
          :icon="TakeawayBox"
          class="icon-button"
          @click="goToOrders"
      ></el-button>
      <el-button
          :icon="User"
          class="icon-button"
          @click="goToAccount"
      ></el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from 'vue-router';
import {ShoppingCart, Star, TakeawayBox, User} from "@element-plus/icons-vue";

const router = useRouter();

const goToHome = () => router.push({name: 'home'});
const goToCart = () => router.push({name: 'cart'});
const goToFavorites = () => router.push({name: 'favorites'});
const goToOrders = () => router.push({name: 'orders'});
const goToAccount = () => router.push({name: 'account'});
</script>

<style scoped>
.test {
  border: 0 solid red;
}

.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ecf0f1;
  padding: 10px;
  gap: 20px;
}

.logo {
  background: none;
  padding: 0;
  border: 0;
}

.logo-text {
  font-size: 36px;
  color: #2c3e50;
  font-weight: bold;
  font-style: italic;
}

.search-container {
  flex: 1;
  display: flex;
  justify-content: center;
}

.search-input {
  width: 100%;
  max-width: 600px;
  padding: 8px 15px;
  border: 1px solid #bdc3c7;
  border-radius: 4px;
  font-size: 16px;
  background-color: #ffffff;
  color: #2c3e50;
}

.search-input:focus {
  outline: none;
  border-color: #3498db;
}

.user-actions {
  display: flex;
  gap: 5px;
}

.icon-button {
  background-color: transparent;
  border: none;
  color: #2c3e50;
  font-size: 24px;
}

.icon-button:hover {
  color: #3498db;
}
</style>
