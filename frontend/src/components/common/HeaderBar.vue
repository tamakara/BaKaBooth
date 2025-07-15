<template>
  <div class="header-bar">
    <div class="logo">
      <el-button class="logo-btn" color="#fc4d50" @click="goToHome">
        <span class="logo-text">BaKaBooth</span>
      </el-button>
    </div>


    <el-input
        class="search"
        type="text"
        placeholder="搜索商品"
    />

    <div class="nav">
      <el-button
          class="nav-button"
          v-for="(btn,index) in nav"
          :key="index"
          :icon="btn.icon"
          @click="btn.goto"
      >
        <span class="nav-button-text">{{ btn.title }}</span>
      </el-button>
      <el-button>
        <template #icon>
          用户
        </template>
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from 'vue-router';
import {ShoppingCart, Star, List} from "@element-plus/icons-vue";

const router = useRouter();

const nav = [
  {title: '购物车', icon: ShoppingCart, goto: () => router.push({name: 'cart'})},
  {title: '收藏夹', icon: Star, goto: () => router.push({name: 'favorites'})},
  {title: '订单', icon: List, goto: () => router.push({name: 'orders'})},
]

const goToHome = () => router.push({name: 'home'});

</script>

<style scoped>
.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ecf0f1;
  box-sizing: border-box;
  height: 100%;
  width: 100%;
}


.logo {
  height: 100%;
  width: 240px;
  padding-right: 20px;
}

.logo-btn {
  border-radius: 0;
  border: 0;
  height: 100%;
  width: 100%;
}

.logo-text {
  font-size: 36px;
  color: white;
  font-weight: bold;
  font-style: italic;
}

.search {
  display: flex;
  flex: 1;
  justify-content: center;
  height: 70%;
}

.nav {
  display: flex;
  justify-content: space-around;
  padding: 0 20px;
  width: 300px;

}

.nav-button {
  flex-direction: column;
  align-items: center;
  background-color: transparent;
  border: none;
  color: #2c3e50;
  font-size: 26px;
}

.nav-button-text {
  font-size: 12px;
  margin-top: 4px;
}
</style>
