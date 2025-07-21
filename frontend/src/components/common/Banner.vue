<template>
  <div class="banner">
    <div class="logo">
      <el-button class="logo-btn" color="#fc4d50" @click="goToHome">
        <span class="logo-text">BaKaBooth</span>
      </el-button>
    </div>

    <div class="search">
      <el-input
          type="text"
          placeholder="搜索商品"
          style="
           height: 70%;
           margin: auto 0;
          "
      />
    </div>

    <div class="nav">
      <div
          class="nav-btn"
          v-for="(btn,index) in nav"
          :key="index"
      >
        <el-button
            :icon="btn.icon"
            @click="btn.goto"
            style="
             border: none;
             font-size: 28px;
             height: 100%;
             width: 100%;
             background: transparent;
            "
        />
      </div>

      <div class="user">
        <div class="user-avatar">
          <el-avatar> user</el-avatar>
        </div>
        xxx
      </div>
    </div>


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
.banner {
  height: 52px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ecf0f1;
}

.logo {
  height: 100%;
  width: 200px;
}

.logo-btn {
  border-radius: 0;
  border: 0;
  height: 100%;
  width: 100%;
}

.logo-text {
  font-size: 30px;
  color: white;
  font-weight: bold;
  font-style: italic;
}

.search {
  height: 100%;
  display: flex;
  flex: 1;
  padding: 0 20px;
}

.nav {
  display: flex;
  justify-content: space-between;
  margin-right: 20px;
  gap: 10px
}

.nav-btn {
  height: 52px;
  width: 52px;
  box-sizing: border-box;
  padding: 1px 6px;
}

.user {
  display: flex;

}

.user-avatar {

}
</style>
