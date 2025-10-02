<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="favorites-page">
        <div class="container">
          <div class="page-header">
            <h1>我的收藏</h1>
            <p>您收藏的商品将在这里显示</p>
          </div>

          <div v-if="loading" class="loading-grid">
            <el-skeleton
                v-for="i in 8"
                :key="i"
                animated
                class="skeleton-item"
            >
              <template #template>
                <el-skeleton-item variant="image" style="width: 100%; height: 200px"/>
                <el-skeleton-item variant="text" style="width: 80%; margin-top: 10px"/>
                <el-skeleton-item variant="text" style="width: 60%; margin-top: 5px"/>
              </template>
            </el-skeleton>
          </div>

          <div v-else-if="favoriteItems.length === 0" class="empty-state">
            <el-empty description="您还没有收藏任何商品">
              <el-button type="primary" @click="goToHome">去逛逛</el-button>
            </el-empty>
          </div>

          <div v-else class="favorites-grid">
            <div
                v-for="item in favoriteItems"
                :key="item.id"
                class="item-card"
                @click="goToItem(item.id)"
            >
              <div class="item-image">
                <el-image
                    :src="item.images[0]"
                    fit="cover"
                    style="width: 100%; height: 200px"
                >
                  <template #placeholder>
                    <div class="image-placeholder">
                      <PhotoIcon class="placeholder-icon"/>
                    </div>
                  </template>
                  <template #error>
                    <div class="image-placeholder">
                      <PhotoIcon class="placeholder-icon"/>
                    </div>
                  </template>
                </el-image>
                <div class="item-actions">
                  <el-button
                      @click.stop="handleUnfavorite(item.id)"
                      type="danger"
                      size="small"
                      circle
                  >
                    <TrashIcon class="action-icon"/>
                  </el-button>
                </div>
              </div>
              <div class="item-info">
                <h3 class="item-name">{{ item.name }}</h3>
                <div class="item-price">¥{{ formatPrice(item.price) }}</div>
                <div class="item-meta">
                  <span class="favorites">
                    <HeartIcon class="meta-icon"/>
                    {{ item.favorites }}
                  </span>
                  <span class="time">{{ formatTime(item.createdAt) }}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- 分页 -->
          <div v-if="favoriteItems.length > 0" class="pagination">
            <el-pagination
                v-model:current-page="currentPage"
                :page-size="pageSize"
                :total="total"
                layout="prev, pager, next"
                @current-change="handlePageChange"
            />
          </div>
        </div>
      </div>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/layout/BaseLayout.vue";
import Banner from "@/components/business/Banner.vue";
import {ref, onMounted} from "vue";
import {useRouter} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import {PhotoIcon, HeartIcon, TrashIcon} from "@heroicons/vue/24/outline";
import {getItemVOList, unfavorite} from "@/services/item.ts";
import type {ItemVO} from "@/types/item.d.ts";

const router = useRouter();

// 响应式数据
const favoriteItems = ref<ItemVO[]>([]);
const loading = ref(false);
const currentPage = ref(1);
const pageSize = ref(20);
const total = ref(0);

// 加载收藏的商品列表
const loadFavoriteItems = async () => {
  try {
    loading.value = true;
    // TODO: 这里应该调用获取收藏商品的接口，暂时用普通商品列表代替
    const itemList = await getItemVOList(undefined, 2, currentPage.value, pageSize.value);
    favoriteItems.value = itemList;
    total.value = itemList.length;
  } catch (error) {
    console.error('加载收藏商品失败:', error);
    ElMessage.error('加载收藏商品失败');
  } finally {
    loading.value = false;
  }
};

// 取消收藏
const handleUnfavorite = async (itemId: number) => {
  try {
    await ElMessageBox.confirm(
        '确定要取消收藏这个商品吗？',
        '取消收藏',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
    );

    await unfavorite(itemId);
    ElMessage.success('已取消收藏');

    // 从列表中移除该商品
    favoriteItems.value = favoriteItems.value.filter(item => item.id !== itemId);
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('取消收藏失败');
    }
  }
};

// 分页处理
const handlePageChange = (page: number) => {
  currentPage.value = page;
  loadFavoriteItems();
};

// 跳转到商品详情
const goToItem = (itemId: number) => {
  router.push(`/item/${itemId}`);
};

// 跳转到首页
const goToHome = () => {
  router.push('/');
};

// 格式化价格
const formatPrice = (price: number) => {
  return price.toFixed(2);
};

// 格式化时间
const formatTime = (time: string) => {
  return new Date(time).toLocaleDateString();
};

// 页面加载时获取数据
onMounted(() => {
  loadFavoriteItems();
});
</script>

<style scoped>
.favorites-page {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.page-header {
  text-align: center;
  margin-bottom: 40px;
  padding: 40px 0;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.page-header h1 {
  font-size: 32px;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 12px;
}

.page-header p {
  color: #7f8c8d;
  margin: 0;
}

.loading-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.skeleton-item {
  background: white;
  border-radius: 8px;
  padding: 16px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.empty-state {
  background: white;
  border-radius: 12px;
  padding: 60px 20px;
  text-align: center;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.favorites-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
  margin-bottom: 32px;
}

.item-card {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  transition: transform 0.2s, box-shadow 0.2s;
  cursor: pointer;
}

.item-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
}

.item-image {
  position: relative;
  overflow: hidden;
}

.image-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 200px;
  background-color: #f5f5f5;
  color: #c0c4cc;
  font-size: 24px;
}

.item-actions {
  position: absolute;
  top: 8px;
  right: 8px;
  opacity: 0;
  transition: opacity 0.3s;
}

.item-card:hover .item-actions {
  opacity: 1;
}

.item-info {
  padding: 16px;
}

.item-name {
  font-size: 16px;
  font-weight: 500;
  color: #2c3e50;
  margin: 0 0 8px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.item-price {
  font-size: 20px;
  font-weight: 600;
  color: #e74c3c;
  margin-bottom: 8px;
}

.item-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  color: #7f8c8d;
}

.favorites {
  display: flex;
  align-items: center;
  gap: 4px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 32px;
}

.placeholder-icon {
  width: 24px;
  height: 24px;
}

.action-icon {
  width: 16px;
  height: 16px;
}

.meta-icon {
  width: 16px;
  height: 16px;
}
</style>
