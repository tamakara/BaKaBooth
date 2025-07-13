<template>
  <header class="header-bar">
    <div class="logo">
      BaKaBooth
    </div>
    <nav class="nav">
      <router-link to="/">首页</router-link>
      <router-link to="/shop">店铺</router-link>
      <router-link to="/cart">购物车</router-link>
      <router-link to="/orders">订单</router-link>
    </nav>
    <div class="user-actions">
      <button>登录</button>
      <button>注册</button>
    </div>
  </header>
</template>

<script setup lang="ts">
</script>

<style scoped>
.header-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #333;
  color: white;
}
.nav {
  display: flex;
  gap: 20px;
}
.nav a {
  text-decoration: none;
  color: white;
}
</style>
