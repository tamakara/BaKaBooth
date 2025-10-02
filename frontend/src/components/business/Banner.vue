<template>
  <div class="banner">
    <div class="container">
      <div class="nav-left">
        <router-link to="/" class="logo">
          <h1>BaKaBooth</h1>
        </router-link>
        <nav class="nav-menu">
          <router-link to="/" class="nav-item">首页</router-link>
          <router-link to="/item/manage" class="nav-item" v-if="userStore.isLoggedIn">我的商品</router-link>
          <router-link to="/user/edit" class="nav-item" v-if="userStore.isLoggedIn">个人中心</router-link>
        </nav>
      </div>

      <div class="nav-right">
        <div v-if="userStore.isLoggedIn" class="user-menu">
          <router-link to="/user/favorites" class="nav-item">
            <HeartIcon class="icon" />
            收藏
          </router-link>
          <el-dropdown>
            <span class="user-avatar">
              <el-avatar :size="32" :src="userStore.user?.avatarUrl">
                {{ userStore.user?.username?.[0] }}
              </el-avatar>
              <span class="username">{{ userStore.user?.username }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="goToProfile">我的主页</el-dropdown-item>
                <el-dropdown-item @click="goToUserEdit">编辑资料</el-dropdown-item>
                <el-dropdown-item @click="logout" divided>退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
        <div v-else class="auth-buttons">
          <router-link to="/user/login">
            <el-button type="primary" plain>登录</el-button>
          </router-link>
          <router-link to="/user/register">
            <el-button type="primary">注册</el-button>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { ElMessage } from 'element-plus';
import { useUserStore } from '@/stores/user';
import { HeartIcon } from '@heroicons/vue/24/outline';

const router = useRouter();
const userStore = useUserStore();

const goToProfile = () => {
  if (userStore.currentUser) {
    router.push(`/user/${userStore.currentUser.id}`);
  }
};

const goToUserEdit = () => {
  router.push('/user/edit');
};

const logout = () => {
  userStore.logout();
  ElMessage.success('已退出登录');
  router.push('/');
};
</script>

<style scoped>
.banner {
  background: white;
  border-bottom: 1px solid #e4e7ed;
  padding: 0;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 64px;
}

.nav-left {
  display: flex;
  align-items: center;
  gap: 32px;
}

.logo {
  text-decoration: none;
  color: #2c3e50;
}

.logo h1 {
  margin: 0;
  font-size: 24px;
  font-weight: 700;
  color: #409eff;
}

.nav-menu {
  display: flex;
  gap: 24px;
}

.nav-item {
  text-decoration: none;
  color: #606266;
  font-weight: 500;
  padding: 8px 12px;
  border-radius: 4px;
  transition: all 0.3s;
  display: flex;
  align-items: center;
  gap: 4px;
}

.nav-item:hover {
  color: #409eff;
  background-color: #ecf5ff;
}

.nav-item.router-link-active {
  color: #409eff;
  background-color: #ecf5ff;
}

.nav-right {
  display: flex;
  align-items: center;
}

.user-menu {
  display: flex;
  align-items: center;
  gap: 16px;
}

.user-avatar {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 4px 8px;
  border-radius: 4px;
  transition: background-color 0.3s;
}

.user-avatar:hover {
  background-color: #f5f7fa;
}

.username {
  font-weight: 500;
  color: #606266;
}

.auth-buttons {
  display: flex;
  gap: 12px;
}

.auth-buttons a {
  text-decoration: none;
}

.icon {
  width: 16px;
  height: 16px;
}
</style>
