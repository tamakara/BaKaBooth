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
                  :size="28"
              />
            </div>

            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="my-shop">
                  <el-icon>
                    <HomeIcon/>
                  </el-icon>
                  个人主页
                </el-dropdown-item>
                <el-dropdown-item command="shop-manage">
                  <el-icon>
                    <BuildingStorefrontIcon/>
                  </el-icon>
                  店铺管理
                </el-dropdown-item>
                <el-dropdown-item command="item-manage">
                  <el-icon>
                    <CubeIcon/>
                  </el-icon>
                  商品管理
                </el-dropdown-item>
                <el-dropdown-item command="settings" divided>
                  <el-icon>
                    <CogIcon/>
                  </el-icon>
                  账号设定
                </el-dropdown-item>
                <el-dropdown-item command="logout">
                  <el-icon>
                    <ArrowRightOnRectangleIcon/>
                  </el-icon>
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

        <!-- 导航按钮组 -->
        <div class="nav-buttons">
          <div class="nav-item">
            <el-button
                class="nav-btn"
                :icon="BellIcon"
                @click="router.push({name: 'messages'})"
                text
                circle
            />
            <span class="nav-label">消息</span>
          </div>
          <div class="nav-item">
            <el-button
                class="nav-btn"
                :icon="UserGroupIcon"
                @click="router.push({name: 'followers'})"
                text
                circle
            />
            <span class="nav-label">关注</span>
          </div>
          <div class="nav-item">
            <el-button
                class="nav-btn"
                :icon="HeartIcon"
                @click="router.push({name: 'favorites'})"
                text
                circle
            />
            <span class="nav-label">收藏</span>
          </div>
          <div class="nav-item">
            <el-button
                class="nav-btn"
                :icon="ShoppingBagIcon"
                @click="router.push({name: 'orders'})"
                text
                circle
            />
            <span class="nav-label">订单</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useRouter} from 'vue-router'
import {ref, onMounted} from 'vue'
import {
  UserGroupIcon,
  ShoppingBagIcon,
  BellIcon,
  MagnifyingGlassIcon as SearchIcon,
  HeartIcon,
  HomeIcon,
  BuildingStorefrontIcon,
  CubeIcon,
  CogIcon,
  ArrowRightOnRectangleIcon
} from '@heroicons/vue/24/outline'

import {useUserStore} from "@/stores/user.ts"
import {getUserSimpleInfo} from "@/api/user.ts"

// 响应式数据
const router = useRouter()
const userStore = useUserStore()
const searchQuery = ref('')
const nickname = ref('')
const avatarUrl = ref('')

const handleSearch = () => {
  if (searchQuery.value.trim()) {
    // 实现搜索逻辑
    router.push({
      name: 'search',
      query: {q: searchQuery.value.trim()}
    })
  }
}

const handleAvatarClick = () => {
  if (!userStore.isLogged) {
    router.push({name: 'login'})
  }
}

const handleCommand = (command: string) => {
  switch (command) {
    case 'my-shop':
      router.push({name: 'shop', params: {id: 123}});
      break
    case 'shop-manage':
      router.push({name: 'shop-manage'})
      break
    case 'item-manage':
      router.push({name: 'item-manage'})
      break
    case 'followers':
      router.push({name: 'followers'})
      break
    case 'favorites':
      router.push({name: 'favorites'})
      break
    case 'orders':
      router.push({name: 'orders'})
      break
    case 'settings':
      router.push({name: 'settings'})
      break
    case 'logout':
      userStore.logout()
      router.push({name: 'home'})
      break
  }
}

const goToRoute = (routeName: string) => {
  router.push({name: routeName})
}

// ���命周期
onMounted(async () => {
  if (userStore.isLogged) {
    try {
      const userInfo = await getUserSimpleInfo()
      nickname.value = userInfo.nickname
      avatarUrl.value = userInfo.avatarUrl
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
  margin: 0 !important;
  padding: 0 !important;
  height: 60px !important;
  min-height: 60px !important;
  max-height: 60px !important;
  line-height: normal !important;
  box-sizing: border-box !important;
  overflow: hidden !important;
}

.banner-container {
  max-width: none;
  width: 100%;
  margin: 0 !important;
  padding: 0 !important;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 0;
  height: 60px !important;
  min-height: 60px !important;
  max-height: 60px !important;
  box-sizing: border-box !important;
  overflow: hidden !important;
}

/* Logo区域 */
.logo-section {
  flex-shrink: 0;
  display: flex;
  align-items: stretch;
  height: 60px !important;
}

.logo-btn {
  padding: 0 16px !important;
  background: #fc4d50 !important;
  border: none !important;
  box-shadow: none !important;
  transition: all 0.3s ease;
  height: 60px !important;
  min-height: 60px !important;
  max-height: 60px !important;
  display: flex;
  align-items: center;
  border-radius: 0 !important;
  margin: 0 !important;
  line-height: normal !important;
  box-sizing: border-box !important;
}

.logo-btn:hover {
  background: #e63946 !important;
  transform: none;
}

.logo-btn:hover .logo-text {
  color: #ffffff;
}

.logo-text {
  font-size: 32px;
  font-weight: 500;
  color: #ffffff;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Helvetica Neue', Arial, sans-serif;
  letter-spacing: 0.3px;
  transition: all 0.3s ease;
  line-height: 1;
  font-style: italic;
}

/* 搜索区域 */
.search-section {
  flex: 1;
  max-width: 600px;
  margin: 0 16px;
  display: flex;
  align-items: center;
  padding: 10px 0;
}

.search-input {
  height: 40px;
}

.search-input :deep(.el-input__wrapper) {
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
  border: 1px solid #e0e0e0;
  padding: 0 16px;
}

.search-input :deep(.el-input__wrapper:hover) {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  border-color: #fc4d50;
}

.search-input :deep(.el-input__wrapper.is-focus) {
  box-shadow: 0 4px 12px rgba(252, 77, 80, 0.2);
  border-color: #fc4d50;
}

.search-input :deep(.el-input__inner) {
  font-size: 14px;
}

.search-btn {
  color: #fc4d50;
  font-size: 16px;
  padding: 6px;
  border-radius: 50%;
  transition: all 0.3s ease;
}

.search-btn:hover {
  background-color: rgba(252, 77, 80, 0.1);
  transform: scale(1.1);
}

/* 导航区域 */
.nav-section {
  display: flex;
  align-items: center;
  gap: 20px;
  flex-shrink: 0;
  padding: 10px 20px 10px 0;
  height: 60px;
}

.nav-buttons {
  display: flex;
  gap: 16px;
  align-items: center;
  height: 100%;
}

.nav-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  min-width: 50px;
  cursor: pointer;
  transition: all 0.3s ease;
  height: 40px;
  justify-content: center;
}

.nav-item:hover {
  transform: translateY(-1px);
}

.nav-btn {
  width: 32px;
  height: 32px;
  color: #666;
  font-size: 20px;
  transition: all 0.3s ease;
  border-radius: 50%;
  margin-bottom: 2px;
}

.nav-btn:hover {
  color: #fc4d50;
  background-color: rgba(252, 77, 80, 0.1);
  box-shadow: 0 2px 8px rgba(252, 77, 80, 0.15);
}

.nav-label {
  font-size: 11px;
  color: #666;
  font-weight: 500;
  white-space: nowrap;
  transition: all 0.3s ease;
  line-height: 1;
}

.nav-item:hover .nav-label {
  color: #fc4d50;
}

/* 用户区域 */
.user-section {
  display: flex;
  align-items: center;
  flex-shrink: 0;
  height: 60px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 14px;
  border-radius: 20px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid transparent;
  min-height: 36px;
}

.user-info:hover {
  background-color: rgba(252, 77, 80, 0.06);
  border-color: rgba(252, 77, 80, 0.15);
  box-shadow: 0 2px 6px rgba(252, 77, 80, 0.1);
}

.user-nickname {
  font-weight: 600;
  color: #333;
  font-size: 14px;
}

.user-avatar {
  border: 2px solid #fc4d50;
  transition: all 0.3s ease;
}

.user-avatar:hover {
  transform: scale(1.05);
  box-shadow: 0 2px 6px rgba(252, 77, 80, 0.2);
}

.login-btn {
  height: 36px;
  padding: 0 20px;
  border-radius: 18px;
  font-weight: 600;
  font-size: 14px;
  background-color: #fc4d50;
  border-color: #fc4d50;
  transition: all 0.3s ease;
  box-shadow: 0 2px 6px rgba(252, 77, 80, 0.25);
}

.login-btn:hover {
  background-color: #e63946;
  border-color: #e63946;
  transform: translateY(-1px);
  box-shadow: 0 4px 10px rgba(252, 77, 80, 0.35);
}

/* 下拉菜单样式 */
:deep(.el-dropdown-menu) {
  border-radius: 12px;
  border: 1px solid #e9ecef;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
  padding: 8px 0;
  min-width: 160px;
}

:deep(.el-dropdown-menu__item) {
  padding: 10px 18px;
  font-size: 13px;
  color: #333;
  display: flex;
  align-items: center;
  gap: 10px;
  transition: all 0.3s ease;
  font-weight: 500;
}

:deep(.el-dropdown-menu__item:hover) {
  background-color: rgba(252, 77, 80, 0.06);
  color: #fc4d50;
}

:deep(.el-dropdown-menu__item.is-divided) {
  border-top: 1px solid #e9ecef;
  margin-top: 6px;
  padding-top: 14px;
}

:deep(.el-dropdown-menu__item .el-icon) {
  font-size: 14px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .banner-container {
    min-height: 56px;
  }

  .logo-btn {
    padding: 0 16px;
    min-height: 56px;
  }

  .logo-text {
    font-size: 24px;
  }

  .search-section {
    margin: 0 16px;
    max-width: 280px;
    padding: 8px 0;
  }

  .search-input {
    height: 36px;
  }

  .user-nickname {
    display: none;
  }

  .nav-section {
    padding: 8px 16px 8px 0;
  }

  .nav-buttons {
    gap: 8px;
  }

  .nav-item {
    min-width: 40px;
  }

  .nav-btn {
    width: 36px;
    height: 36px;
    font-size: 18px;
    margin-bottom: 3px;
  }

  .nav-label {
    font-size: 9px;
  }

  .login-btn {
    height: 32px;
    padding: 0 16px;
    font-size: 12px;
  }
}

@media (max-width: 480px) {
  .banner-container {
    min-height: 50px;
  }

  .logo-btn {
    padding: 0 12px;
    min-height: 50px;
  }

  .logo-text {
    font-size: 20px;
    letter-spacing: 0.3px;
  }

  .search-section {
    margin: 0 8px;
    padding: 6px 0;
  }

  .nav-section {
    gap: 16px;
    padding: 6px 12px 6px 0;
  }

  .nav-buttons {
    gap: 6px;
  }

  .nav-item {
    min-width: 32px;
  }

  .nav-btn {
    width: 32px;
    height: 32px;
    font-size: 16px;
    margin-bottom: 2px;
  }

  .nav-label {
    font-size: 8px;
  }
}
</style>