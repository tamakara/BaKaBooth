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
      <div class="user">
        <el-dropdown
            v-if="true"
            @command="handleCommand"
            :hide-on-click="false"
        >
          <el-avatar @click="handleAvatarClick">FUCK</el-avatar>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="a">关注的店铺</el-dropdown-item>
              <el-dropdown-item command="b">已购买的商品</el-dropdown-item>
              <el-dropdown-item command="c">历史订单</el-dropdown-item>
              <el-dropdown-item
                  command="logout"
                  divided
                  :icon="SwitchButton"
              >
                退出登录
              </el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
        <el-avatar @click="handleAvatarClick" v-else>登录</el-avatar>
      </div>
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
             font-size: 30px;
             height: 100%;
             width: 100%;
             background: transparent;
            "
        />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from 'vue-router';
import {ShoppingCart, Star, SwitchButton} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

const router = useRouter();

const nav = [
  {icon: Star, goto: () => router.push({name: 'favorites'})},
  {icon: ShoppingCart, goto: () => router.push({name: 'cart'})}
]

const goToHome = () => router.push({name: 'home'});

function handleAvatarClick() {

}

function handleCommand(command: string | number | object) {
  ElMessage(`click on item ${command}`)
}

</script>

<style scoped>
.banner {
  height: 52px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
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
  margin-right: 10px;
  gap: 10px
}

.nav-btn {
  height: 52px;
  width: 52px;
  box-sizing: border-box;
  padding: 1px 6px;
}

.user {
  height: 52px;
  width: 52px;
  box-sizing: border-box;
  padding: 1px 6px;
  display: flex;
  align-items: center;
}
</style>
