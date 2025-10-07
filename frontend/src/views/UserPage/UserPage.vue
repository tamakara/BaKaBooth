<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="user-page">
        <div class="container">
          <!-- 用户信息头部 -->
          <div class="user-header">
            <div v-if="loading" class="loading-header">
              <el-skeleton animated>
                <template #template>
                  <div class="header-skeleton">
                    <el-skeleton-item variant="circle" style="width: 120px; height: 120px"/>
                    <div class="header-skeleton-text">
                      <el-skeleton-item variant="h1" style="width: 200px; height: 32px; margin-bottom: 16px;"/>
                      <el-skeleton-item variant="text" style="width: 300px; height: 20px; margin-bottom: 8px;"/>
                      <el-skeleton-item variant="text" style="width: 250px; height: 20px;"/>
                    </div>
                  </div>
                </template>
              </el-skeleton>
            </div>
            <div v-else class="user-info">
              <div class="user-avatar">
                <el-avatar :size="120" :src="userInfo.avatarUrl">
                  {{ userInfo.username?.[0] }}
                </el-avatar>
              </div>
              <div class="user-details">
                <h1 class="user-name">{{ userInfo.username }}</h1>
                <div class="user-meta">
                  <div class="meta-item">
                    <UserIcon class="meta-icon"/>
                    <span>关注者：{{ userInfo.followers }}</span>
                  </div>
                  <div class="meta-item">
                    <CalendarIcon class="meta-icon"/>
                    <span>加入时间：{{ formatTime(userInfo.createdAt) }}</span>
                  </div>
                  <div class="meta-item" v-if="userInfo.shopStateCode">
                    <BuildingStorefrontIcon class="meta-icon"/>
                    <span>店铺状态：{{ getShopStateText(userInfo.shopStateCode) }}</span>
                  </div>
                </div>
                <div class="user-description">
                  <p>{{ userInfo.introduction || '这个用户很懒，什么都没有留下...' }}</p>
                  <div v-if="userInfo.announcement" class="user-announcement">
                    <BellIcon class="announcement-icon"/>
                    <span>{{ userInfo.announcement }}</span>
                  </div>
                </div>
                <div class="user-actions">
                  <el-button
                      v-if="!isOwnProfile"
                      :type="isFollowed ? 'primary' : 'default'"
                      @click="handleFollow"
                  >
                    <StarIcon class="button-icon"/>
                    {{ isFollowed ? '已关注' : '关注用户' }}
                  </el-button>
                  <el-button
                      v-if="!isOwnProfile"
                      type="primary"
                      plain
                      @click="handleContact"
                  >
                    <ChatBubbleLeftEllipsisIcon class="button-icon"/>
                    联系用户
                  </el-button>
                  <el-button
                      v-if="isOwnProfile"
                      type="primary"
                      @click="goToUserEdit"
                  >
                    <PencilIcon class="button-icon"/>
                    编辑资料
                  </el-button>
                </div>
              </div>
            </div>
          </div>

          <!-- 标签页切换 -->
          <div class="content-tabs">
            <el-tabs v-model="activeTab" @tab-click="handleTabChange">
              <el-tab-pane label="在售商品" name="selling">
                <div class="tab-content">
                  <div class="tab-header">
                    <div class="tab-title">
                      <h2>在售商品 ({{ sellingItems.length }})</h2>
                    </div>
                    <div class="tab-filters">
                      <el-select v-model="sortBy" @change="loadSellingItems" placeholder="排序方式">
                        <el-option label="最新发布" value="newest"/>
                        <el-option label="价格从低到高" value="price_asc"/>
                        <el-option label="价格从高到低" value="price_desc"/>
                        <el-option label="收藏最多" value="favorites"/>
                      </el-select>
                    </div>
                  </div>
                  <ItemGrid :items="sellingItems" :loading="itemsLoading" @item-click="goToItem"/>
                </div>
              </el-tab-pane>

              <el-tab-pane label="已售商品" name="sold" v-if="isOwnProfile">
                <div class="tab-content">
                  <div class="tab-header">
                    <div class="tab-title">
                      <h2>已售商品 ({{ soldItems.length }})</h2>
                    </div>
                  </div>
                  <ItemGrid :items="soldItems" :loading="itemsLoading" @item-click="goToItem"/>
                </div>
              </el-tab-pane>

              <el-tab-pane label="草稿商品" name="draft" v-if="isOwnProfile">
                <div class="tab-content">
                  <div class="tab-header">
                    <div class="tab-title">
                      <h2>草稿商品 ({{ draftItems.length }})</h2>
                    </div>
                    <div class="tab-actions">
                      <el-button type="primary" @click="goToItemCreate">
                        <PlusIcon class="button-icon"/>
                        发布新商品
                      </el-button>
                    </div>
                  </div>
                  <ItemGrid :items="draftItems" :loading="itemsLoading" @item-click="goToItem"/>
                </div>
              </el-tab-pane>
            </el-tabs>
          </div>
        </div>
      </div>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/layout/BaseLayout.vue";
import Banner from "@/components/business/Banner.vue";
import ItemGrid from "./components/ItemGrid.vue";
import {ref, onMounted, computed, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {
  UserIcon,
  CalendarIcon,
  BuildingStorefrontIcon,
  BellIcon,
  StarIcon,
  ChatBubbleLeftEllipsisIcon,
  PencilIcon,
  PlusIcon
} from "@heroicons/vue/24/outline";
import {getUserVO} from "@/services/user.ts";
import {getItemPageVO} from "@/services/item.ts";
import type {UserVO, ItemVO} from "@/types/user";
import {useUserStore} from "@/stores/user";

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

// 响应式数据
const userInfo = ref<UserVO>({} as UserVO);
const sellingItems = ref<ItemVO[]>([]);
const soldItems = ref<ItemVO[]>([]);
const draftItems = ref<ItemVO[]>([]);
const loading = ref(true);
const itemsLoading = ref(false);
const isFollowed = ref(false);
const activeTab = ref('selling');
const sortBy = ref('newest');

// 计算属性
const userId = computed(() => route.params.id as string);
const isOwnProfile = computed(() =>
    userStore.currentUser && userStore.currentUser.id.toString() === userId.value
);

// 监听路由变化
watch(() => route.params.id, (newId) => {
  if (newId) {
    loadData();
  }
});

// 加载用户信息
const loadUserInfo = async () => {
  try {
    userInfo.value = await getUserVO(userId.value);
  } catch (error) {
    console.error('加载用户信息失败:', error);
    ElMessage.error('加载用户信息失败');
  }
};

// 加载在售商品
const loadSellingItems = async () => {
  try {
    itemsLoading.value = true;
    const itemList = await getItemVOList(Number(userId.value), 2, 1, 20);
    sellingItems.value = itemList;
  } catch (error) {
    console.error('加载在售商品失败:', error);
    ElMessage.error('加载在售商品失败');
  } finally {
    itemsLoading.value = false;
  }
};

// 加载已售商品
const loadSoldItems = async () => {
  try {
    itemsLoading.value = true;
    // TODO: 调用已售商品接口（状态为已售）
    soldItems.value = [];
  } catch (error) {
    console.error('加载已售商品失败:', error);
  } finally {
    itemsLoading.value = false;
  }
};

// 加载草稿商品
const loadDraftItems = async () => {
  try {
    itemsLoading.value = true;
    const itemList = await getItemVOList(Number(userId.value), 1, 1, 20);
    draftItems.value = itemList;
  } catch (error) {
    console.error('加载草稿商品失败:', error);
  } finally {
    itemsLoading.value = false;
  }
};

// 加载所有数据
const loadData = async () => {
  try {
    loading.value = true;
    await loadUserInfo();
    await loadSellingItems();

    // 如果是自己的页面，加载其他标签页数据
    if (isOwnProfile.value) {
      await Promise.all([loadSoldItems(), loadDraftItems()]);
    }
  } finally {
    loading.value = false;
  }
};

// 标签页切换处理
const handleTabChange = (tab: any) => {
  activeTab.value = tab.paneName;

  // 根据标签页加载对应数据
  switch (tab.paneName) {
    case 'selling':
      if (sellingItems.value.length === 0) loadSellingItems();
      break;
    case 'sold':
      if (soldItems.value.length === 0) loadSoldItems();
      break;
    case 'draft':
      if (draftItems.value.length === 0) loadDraftItems();
      break;
  }
};

// 关注/取消关注
const handleFollow = () => {
  isFollowed.value = !isFollowed.value;
  ElMessage.success(isFollowed.value ? '关注成功' : '已取消关注');
};

// 联系用户
const handleContact = () => {
  ElMessage.info('联系用户功能开发中');
};

// 跳转到用户编辑页面
const goToUserEdit = () => {
  router.push('/user/edit');
};

// 跳转到商品详情
const goToItem = (itemId: number) => {
  router.push(`/item/${itemId}`);
};

// 跳转到创建商品
const goToItemCreate = () => {
  router.push('/item/new/edit');
};

// 获取店铺��态文本
const getShopStateText = (stateCode: number) => {
  switch (stateCode) {
    case 1:
      return '正常营业';
    case 2:
      return '暂停营业';
    default:
      return '未开通';
  }
};

// ��式化时间
const formatTime = (time: string) => {
  return new Date(time).toLocaleDateString();
};

// 页面加载时获取数据
onMounted(() => {
  loadData();
});
</script>

<style scoped>
.user-page {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.user-header {
  background: white;
  border-radius: 12px;
  padding: 32px;
  margin-bottom: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.loading-header .header-skeleton {
  display: flex;
  gap: 24px;
  align-items: center;
}

.header-skeleton-text {
  flex: 1;
}

.user-info {
  display: flex;
  gap: 24px;
  align-items: flex-start;
}

.user-details {
  flex: 1;
}

.user-name {
  font-size: 28px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 16px 0;
}

.user-meta {
  display: flex;
  gap: 24px;
  margin-bottom: 16px;
  flex-wrap: wrap;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #7f8c8d;
  font-size: 14px;
}

.user-description {
  margin-bottom: 20px;
  color: #606266;
  line-height: 1.6;
}

.user-announcement {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-top: 12px;
  padding: 12px;
  background: #f0f9ff;
  border: 1px solid #bae6fd;
  border-radius: 8px;
  color: #0369a1;
  font-size: 14px;
}

.user-actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}

.content-tabs {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.tab-content {
  padding: 24px;
}

.tab-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e4e7ed;
}

.tab-title h2 {
  font-size: 20px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0;
}

.tab-filters,
.tab-actions {
  display: flex;
  gap: 12px;
  align-items: center;
}

/* 深度选择器修改标签页样式 */
:deep(.el-tabs__header) {
  margin: 0;
  background: #f8f9fa;
  padding: 0 24px;
}

:deep(.el-tabs__nav-wrap::after) {
  display: none;
}

:deep(.el-tabs__item) {
  height: 48px;
  line-height: 48px;
  font-weight: 500;
}

:deep(.el-tabs__item.is-active) {
  color: #409eff;
}

.meta-icon {
  width: 16px;
  height: 16px;
}

.announcement-icon {
  width: 16px;
  height: 16px;
}

.button-icon {
  width: 16px;
  height: 16px;
  margin-right: 4px;
}
</style>
