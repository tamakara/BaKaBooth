<template>
  <div class="banner">
    <div class="banner-container">
      <!-- Logo区域 -->
      <div class="logo-section">
        <el-button
          class="logo-btn"
          type="text"
          @click="goToRoute('home')"
        >
          <span class="logo-text">BaKaBooth</span>
        </el-button>
      </div>

      <!-- 搜索区域 -->
      <div class="search-section">
        <el-input
          v-model="searchQuery"
          class="search-input"
          type="text"
          placeholder="搜索商品"
          clearable
          @keyup.enter="handleSearch"
        >
          <template #suffix>
            <el-button
              class="search-btn"
              :icon="SearchIcon"
              @click="handleSearch"
              text
            />
          </template>
        </el-input>
      </div>

      <!-- 导航区域 -->
      <div class="nav-section">
        <!-- 导航按钮组 -->
        <div class="nav-buttons">
          <el-button
            class="nav-btn"
            :icon="BellIcon"
            @click="goToRoute('messages')"
            text
            circle
            size="large"
          />
          <el-button
            class="nav-btn"
            :icon="StarIcon"
            @click="goToRoute('favorites')"
            text
            circle
            size="large"
          />
          <el-button
            class="nav-btn"
            :icon="ShoppingCartIcon"
            @click="goToRoute('cart')"
            text
            circle
            size="large"
          />
        </div>

        <!-- 用户区域 -->
        <div class="user-section">
          <el-dropdown
            v-if="userStore.isLogged"
            @command="handleCommand"
            trigger="click"
            placement="bottom-end"
          >
            <div class="user-info">
              <el-text class="user-nickname">{{ nickname }}</el-text>
              <el-avatar
                class="user-avatar"
                :src="avatarUrl"
                :size="36"
              />
            </div>

            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="followed-shops">
                  <el-icon><StarIcon /></el-icon>
                  关注的店铺
                </el-dropdown-item>
                <el-dropdown-item command="favorites">
                  <el-icon><HeartIcon /></el-icon>
                  收藏的商品
                </el-dropdown-item>
                <el-dropdown-item command="purchased">
                  <el-icon><ShoppingBagIcon /></el-icon>
                  已购买的商品
                </el-dropdown-item>
                <el-dropdown-item command="orders">
                  <el-icon><DocumentIcon /></el-icon>
                  历史订单
                </el-dropdown-item>
                <el-dropdown-item command="shop-manage" divided>
                  <el-icon><BuildingStorefrontIcon /></el-icon>
                  店铺管理
                </el-dropdown-item>
                <el-dropdown-item command="item-manage">
                  <el-icon><CubeIcon /></el-icon>
                  商品管理
                </el-dropdown-item>
                <el-dropdown-item command="settings" divided>
                  <el-icon><CogIcon /></el-icon>
                  账号设定
                </el-dropdown-item>
                <el-dropdown-item command="logout">
                  <el-icon><ArrowRightOnRectangleIcon /></el-icon>
                  登出
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>

          <el-button
            v-else
            class="login-btn"
            type="primary"
            @click="handleAvatarClick"
          >
            登录
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ref, onMounted, computed } from 'vue'
import {
  StarIcon,
  ShoppingCartIcon,
  BellIcon,
  MagnifyingGlassIcon as SearchIcon,
  HeartIcon,
  ShoppingBagIcon,
  DocumentIcon,
  BuildingStorefrontIcon,
  CubeIcon,
  CogIcon,
  ArrowRightOnRectangleIcon
} from '@heroicons/vue/24/outline'

import { useUserStore } from "@/stores/user.ts"
import { getUserSimpleInfo } from "@/api/user.ts"

// 响应式数据
const router = useRouter()
const userStore = useUserStore()
const searchQuery = ref('')
const nickname = ref('')
const avatarUrl = ref('')

// 方法
const goToRoute = (routeName: string) => {
  router.push({ name: routeName })
}

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    // 实现搜索逻辑
    router.push({
      name: 'search',
      query: { q: searchQuery.value.trim() }
    })
  }
}

const handleAvatarClick = () => {
  if (!userStore.isLogged) {
    router.push({ name: 'login' })
  }
}

const handleCommand = (command: string) => {
  switch (command) {
    case 'followed-shops':
      // 跳转到关注的店铺页面
      break
    case 'favorites':
      goToRoute('favorites')
      break
    case 'purchased':
      // 跳转到已购买的商品页面
      break
    case 'orders':
      goToRoute('orders')
      break
    case 'shop-manage':
      goToRoute('shop-manage')
      break
    case 'item-manage':
      // 跳转到商品管理页面
      break
    case 'settings':
      // 跳转到账号设定页面
      break
    case 'logout':
      userStore.logout()
      router.push({ name: 'home' })
      break
  }
}

// 生命周期
onMounted(async () => {
  if (userStore.isLogged) {
    try {
      const userInfo = await getUserSimpleInfo()
      nickname.value = userInfo.nickname || '用户'
      avatarUrl.value = userInfo.avatarUrl || ''
    } catch (error) {
      console.error('获取用户信息失败:', error)
    }
  }
})
</script>

<style scoped>
.banner {
  position: sticky;
  top: 0;
  z-index: 1000;
  background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
  border-bottom: 1px solid #e9ecef;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
}

.banner-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 12px 0;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 24px;
}

/* Logo区域 */
.logo-section {
  flex-shrink: 0;
}

.logo-btn {
  padding: 4px 8px;
  background: transparent !important;
  border: none !important;
  box-shadow: none !important;
  transition: all 0.3s ease;
  height: auto;
  min-height: 48px;
  display: flex;
  align-items: center;
}

.logo-btn:hover {
  background: transparent !important;
  transform: scale(1.05);
}

.logo-btn:hover .logo-text {
  color: #e63946;
  text-shadow: 0 2px 8px rgba(252, 77, 80, 0.3);
}

.logo-text {
  font-size: 32px;
  font-weight: 600;
  color: #fc4d50;
  font-family: 'Arial', sans-serif;
  letter-spacing: 0.5px;
  transition: all 0.3s ease;
  line-height: 1;
}

/* 搜索区域 */
.search-section {
  flex: 1;
  max-width: 500px;
  margin: 0 32px;
}

.search-input {
  height: 44px;
}

.search-input :deep(.el-input__wrapper) {
  border-radius: 22px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
  border: 1px solid #e0e0e0;
}

.search-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

.search-input :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 12px rgba(252, 77, 80, 0.2);
  border-color: #fc4d50;
}

.search-btn {
  color: #fc4d50;
  font-size: 18px;
  padding: 8px;
}

/* 导航区域 */
.nav-section {
  display: flex;
  align-items: center;
  gap: 16px;
  flex-shrink: 0;
}

.nav-buttons {
  display: flex;
  gap: 8px;
}

.nav-btn {
  width: 44px;
  height: 44px;
  color: #666;
  font-size: 20px;
  transition: all 0.3s ease;
}

.nav-btn:hover {
  color: #fc4d50;
  background-color: rgba(252, 77, 80, 0.1);
  transform: translateY(-2px);
}

/* 用户区域 */
.user-section {
  display: flex;
  align-items: center;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 8px 12px;
  border-radius: 22px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.user-info:hover {
  background-color: rgba(252, 77, 80, 0.1);
}

.user-nickname {
  font-weight: 500;
  color: #333;
  font-size: 14px;
}

.user-avatar {
  border: 2px solid #fc4d50;
  transition: all 0.3s ease;
}

.user-avatar:hover {
  transform: scale(1.1);
}

.login-btn {
  height: 36px;
  padding: 0 20px;
  border-radius: 18px;
  font-weight: 500;
  background-color: #fc4d50;
  border-color: #fc4d50;
  transition: all 0.3s ease;
}

.login-btn:hover {
  background-color: #e63946;
  border-color: #e63946;
  transform: translateY(-2px);
}

/* 下拉菜单样式 */
:deep(.el-dropdown-menu) {
  border-radius: 12px;
  border: 1px solid #e9ecef;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
  padding: 8px 0;
}

:deep(.el-dropdown-menu__item) {
  padding: 12px 20px;
  font-size: 14px;
  color: #333;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: all 0.3s ease;
}

:deep(.el-dropdown-menu__item:hover) {
  background-color: rgba(252, 77, 80, 0.1);
  color: #fc4d50;
}

:deep(.el-dropdown-menu__item.is-divided) {
  border-top: 1px solid #e9ecef;
  margin-top: 4px;
  padding-top: 16px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .banner-container {
    padding: 8px 16px;
    gap: 16px;
  }

  .search-section {
    margin: 0 16px;
  }

  .user-nickname {
    display: none;
  }

  .nav-buttons {
    gap: 4px;
  }

  .nav-btn {
    width: 36px;
    height: 36px;
    font-size: 18px;
  }

  .logo-text {
    font-size: 18px;
  }
}

@media (max-width: 480px) {
  .banner-container {
    gap: 12px;
  }

  .search-section {
    margin: 0 8px;
  }

  .logo-text {
    font-size: 16px;
    letter-spacing: 0.5px;
  }
}
</style>