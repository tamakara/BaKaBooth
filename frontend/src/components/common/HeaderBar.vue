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

    <el-space class="nav" :size="40">
      <el-button
          class="nav-button"
          v-for="(btn,index) in nav"
          :key="index"
          :icon="btn.icon"
          @click="btn.goto"
      />
    </el-space>

    <el-avatar class="avatar"> user</el-avatar>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from 'vue-router';
import {ShoppingCart, Star, List} from "@element-plus/icons-vue";

const router = useRouter();

const nav = [
  {icon: ShoppingCart, goto: () => router.push({name: 'cart'})},
  {icon: Star, goto: () => router.push({name: 'favorites'})},
  {icon: List, goto: () => router.push({name: 'orders'})},
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
  margin: 0 20px;
}

.nav-button {
  flex-direction: column;
  align-items: center;
  background-color: transparent;
  border: none;
  color: #2c3e50;
  font-size: 30px;
}


.avatar {
  margin: 0 20px;
}
</style>
