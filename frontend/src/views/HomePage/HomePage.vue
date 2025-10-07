<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="home-page">
        <div class="container">
          <!-- Hero 区域 -->
          <div class="hero-section">
            <div class="hero-content">
              <h1 class="hero-title">
                <span class="gradient-text">发现好物</span>
                <span class="subtitle">让闲置物品重获新生</span>
              </h1>
              <p class="hero-description">
                BaKaBooth 是您的专属二手交易平台，在这里您可以发现独特的宝贝，
                也可以让自己的闲置物品找到新主人。安全、便捷、值得信赖。
              </p>
              <div class="search-bar">
                <el-input
                    v-model="searchKeyword"
                    placeholder="搜索您想要的商品..."
                    size="large"
                    class="search-input"
                    @keyup.enter="handleSearch"
                >
                  <template #prefix>
                    <MagnifyingGlassIcon class="search-icon"/>
                  </template>
                  <template #suffix>
                    <el-button @click="handleSearch" type="primary" class="search-btn">
                      搜索
                    </el-button>
                  </template>
                </el-input>
              </div>
            </div>
            <div class="hero-image">
              <div class="floating-cards">
                <div class="card card-1">
                  <PhotoIcon class="card-icon"/>
                  <span>精美商品</span>
                </div>
                <div class="card card-2">
                  <ShieldCheckIcon class="card-icon"/>
                  <span>安全交易</span>
                </div>
                <div class="card card-3">
                  <HeartIcon class="card-icon"/>
                  <span>用心服务</span>
                </div>
              </div>
            </div>
          </div>

          <!-- 商品分类快捷入口 -->
          <div class="categories-section">
            <h2 class="section-title">热门分类</h2>
            <div class="categories-grid">
              <div class="category-item" v-for="category in categories" :key="category.name">
                <div class="category-icon">{{ category.icon }}</div>
                <span class="category-name">{{ category.name }}</span>
              </div>
            </div>
          </div>

          <!-- 商品列表 -->
          <div class="items-section">
            <div class="section-header">
              <div class="section-title-area">
                <h2 class="section-title">最新商品</h2>
                <p class="section-subtitle">发现更多精彩好物</p>
              </div>
              <div class="filters">
                <el-select v-model="stateCode" @change="loadItems" placeholder="商品状态" class="filter-select">
                  <el-option label="全部" :value="0"/>
                </el-select>
              </div>
            </div>

            <div v-if="loading" class="loading-grid">
              <div v-for="i in 8" :key="i" class="skeleton-card">
                <el-skeleton animated>
                  <template #template>
                    <el-skeleton-item variant="image" style="width: 100%; height: 220px; border-radius: 12px;"/>
                    <div style="padding: 16px;">
                      <el-skeleton-item variant="text" style="width: 80%; margin-bottom: 8px;"/>
                      <el-skeleton-item variant="text" style="width: 60%; margin-bottom: 8px;"/>
                      <el-skeleton-item variant="text" style="width: 40%;"/>
                    </div>
                  </template>
                </el-skeleton>
              </div>
            </div>

            <div v-else-if="items.length === 0" class="empty-state">
              <PhotoIcon class="empty-icon"/>
              <h3>暂无商品</h3>
              <p>还没有找到相关商品，试试其他搜索条件吧</p>
            </div>

            <div v-else class="items-grid">
              <div
                  v-for="item in items"
                  :key="item.id"
                  class="item-card"
                  @click="goToItem(item.id)"
              >
                <div class="item-image">
                  <el-image
                      :src="item.images[0]"
                      fit="cover"
                      class="image"
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
                  <div class="item-badge" v-if="item.isSeller">
                    <span>我的商品</span>
                  </div>
                </div>
                <div class="item-content">
                  <h3 class="item-name">{{ item.name }}</h3>
                  <div class="item-price">¥{{ formatPrice(item.price) }}</div>
                  <div class="item-meta">
                    <span class="favorites">
                      <HeartIcon class="meta-icon"/>
                      {{ item.favorites }}
                    </span>
                    <span class="item-time">
                      {{ formatTimeAgo(item.createdAt) }}
                    </span>
                  </div>
                </div>
              </div>
            </div>

            <!-- 分页 -->
            <div v-if="items.length > 0" class="pagination">
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
      </div>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/layout/BaseLayout.vue";
import Banner from "@/components/business/Banner.vue";
import {ref, onMounted} from "vue";
import {useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {MagnifyingGlassIcon, PhotoIcon, HeartIcon, ShieldCheckIcon} from "@heroicons/vue/24/outline";
import {getItemPageVO} from "@/services/item.ts";
import type {ItemVO} from "@/types/item.d.ts";

const router = useRouter();

// 响应式数据
const searchKeyword = ref('');
const items = ref<ItemVO[]>([]);
const loading = ref(false);
const stateCode = ref(0);
const currentPage = ref(1);
const pageSize = ref(20);
const total = ref(0);

// 分类数据
const categories = ref([
  {name: '数码产品', icon: '📱'},
  {name: '服装配饰', icon: '👕'},
  {name: '家居用品', icon: '🏠'},
  {name: '图书文具', icon: '📚'},
  {name: '运动户外', icon: '⚽'},
  {name: '美妆护肤', icon: '💄'},
  {name: '母婴用品', icon: '👶'},
  {name: '其他商品', icon: '🎁'},
]);

// 加载商品列表
const loadItems = async () => {
  try {
    loading.value = true;
    const page = await getItemPageVO({
      sellerId: 0,
      pageNo: currentPage.value,
      pageSize: pageSize.value,
      stateCode: stateCode.value,
      keyword: searchKeyword.value.trim(),
    });
    items.value = page.records;
    currentPage.value = page.current;
    total.value = page.total;
  } catch (error) {
    console.error('加载商品失败:', error);
    ElMessage.error('加载商品失败');
  } finally {
    loading.value = false;
  }
};

// 搜索处理
const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    loadItems();
  } else {
    ElMessage.error('搜索失败');
  }
};

// 分页处理
const handlePageChange = (page: number) => {
  currentPage.value = page;
  loadItems();
};

// 跳转到商品详情
const goToItem = (itemId: number) => {
  router.push(`/item/${itemId}`);
};

// 格式化价格
const formatPrice = (price: number) => {
  return price.toFixed(2);
};

// 时间格式化
const formatTimeAgo = (time: string) => {
  const now = new Date();
  const itemTime = new Date(time);
  const diffInMinutes = Math.floor((now.getTime() - itemTime.getTime()) / (1000 * 60));

  if (diffInMinutes < 60) {
    return `${diffInMinutes}分钟前`;
  } else if (diffInMinutes < 1440) {
    return `${Math.floor(diffInMinutes / 60)}小时前`;
  } else {
    return `${Math.floor(diffInMinutes / 1440)}天前`;
  }
};

// 页面加载时获取数据
onMounted(() => {
  loadItems();
});
</script>

<style scoped>
.home-page {
  min-height: 100vh;
  background: linear-gradient(180deg, #f8faff 0%, #f5f5f5 100%);
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

/* Hero Section */
.hero-section {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 60px;
  align-items: center;
  padding: 80px 0;
  min-height: 500px;
}

.hero-content {
  max-width: 500px;
}

.hero-title {
  margin: 0 0 24px 0;
  line-height: 1.2;
}

.gradient-text {
  display: block;
  font-size: 48px;
  font-weight: 700;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 8px;
}

.subtitle {
  display: block;
  font-size: 20px;
  font-weight: 400;
  color: #7f8c8d;
}

.hero-description {
  font-size: 16px;
  line-height: 1.6;
  color: #606266;
  margin-bottom: 32px;
}

.search-bar {
  width: 100%;
}

.search-input :deep(.el-input__wrapper) {
  border-radius: 50px;
  padding: 0 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  border: none;
}

.search-icon {
  width: 20px;
  height: 20px;
  color: #909399;
}

.search-btn {
  border-radius: 25px;
  padding: 0 24px;
}

/* Hero Image */
.hero-image {
  position: relative;
  height: 400px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.floating-cards {
  position: relative;
  width: 100%;
  height: 100%;
}

.card {
  position: absolute;
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  animation: float 6s ease-in-out infinite;
}

.card-1 {
  top: 20%;
  left: 10%;
  animation-delay: 0s;
}

.card-2 {
  top: 50%;
  right: 20%;
  animation-delay: 2s;
}

.card-3 {
  bottom: 20%;
  left: 30%;
  animation-delay: 4s;
}

.card-icon {
  width: 32px;
  height: 32px;
  color: #667eea;
}

@keyframes float {
  0%, 100% {
    transform: translateY(0px);
  }
  50% {
    transform: translateY(-20px);
  }
}

/* Categories Section */
.categories-section {
  padding: 60px 0;
}

.section-title {
  font-size: 32px;
  font-weight: 600;
  color: #2c3e50;
  text-align: center;
  margin-bottom: 40px;
}

.categories-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(120px, 1fr));
  gap: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.category-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  padding: 24px 16px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  transition: all 0.3s ease;
}

.category-item:hover {
  transform: translateY(-4px);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
}

.category-icon {
  font-size: 32px;
}

.category-name {
  font-size: 14px;
  font-weight: 500;
  color: #2c3e50;
}

/* Items Section */
.items-section {
  padding: 60px 0 80px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-end;
  margin-bottom: 40px;
}

.section-title-area {
  text-align: left;
}

.section-title {
  font-size: 28px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 8px 0;
}

.section-subtitle {
  color: #7f8c8d;
  margin: 0;
}

.filter-select {
  min-width: 120px;
}

/* Loading Grid */
.loading-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
}

.skeleton-card {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);
}

/* Empty State */
.empty-state {
  text-align: center;
  padding: 80px 20px;
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);
}

.empty-icon {
  width: 64px;
  height: 64px;
  color: #c0c4cc;
  margin: 0 auto 16px;
}

.empty-state h3 {
  font-size: 20px;
  color: #909399;
  margin: 0 0 8px 0;
}

.empty-state p {
  color: #c0c4cc;
  margin: 0;
}

/* Items Grid */
.items-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 24px;
  margin-bottom: 40px;
}

.item-card {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);
  cursor: pointer;
  transition: all 0.3s ease;
}

.item-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.15);
}

.item-image {
  position: relative;
  height: 220px;
  overflow: hidden;
}

.image {
  width: 100%;
  height: 100%;
  transition: transform 0.3s ease;
}

.item-card:hover .image {
  transform: scale(1.05);
}

.image-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.placeholder-icon {
  width: 48px;
  height: 48px;
  color: #c0c4cc;
}

.item-badge {
  position: absolute;
  top: 12px;
  left: 12px;
  background: rgba(64, 158, 255, 0.9);
  color: white;
  padding: 4px 12px;
  border-radius: 12px;
  font-size: 12px;
  font-weight: 500;
}

.item-content {
  padding: 20px;
}

.item-name {
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 12px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.item-price {
  font-size: 22px;
  font-weight: 700;
  color: #e74c3c;
  margin-bottom: 12px;
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

.meta-icon {
  width: 16px;
  height: 16px;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 40px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hero-section {
    grid-template-columns: 1fr;
    gap: 40px;
    padding: 40px 0;
    text-align: center;
  }

  .gradient-text {
    font-size: 36px;
  }

  .subtitle {
    font-size: 18px;
  }

  .section-header {
    flex-direction: column;
    gap: 20px;
    align-items: center;
    text-align: center;
  }

  .items-grid {
    grid-template-columns: repeat(auto-fill, minmax(240px, 1fr));
    gap: 16px;
  }
}
</style>
