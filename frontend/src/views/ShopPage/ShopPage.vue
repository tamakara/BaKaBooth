<template>
  <ShopPageLayout>
    <template #left-sidebar>
      <div class="shop-info-card">
        <div class="shop-header">
          <el-avatar :size="64" class="shop-avatar">{{ shop.name.charAt(0) }}</el-avatar>
          <div class="shop-basic-info">
            <h2 class="shop-name">{{ shop.name }}</h2>
            <div class="shop-stats">
              <div class="stat-item">
                <el-icon class="stat-icon"><UserGroupIcon /></el-icon>
                <span>1.2k 关注者</span>
              </div>
              <div class="stat-item">
                <el-icon class="stat-icon"><ShoppingBagIcon /></el-icon>
                <span>{{ shop.products.length }} 个商品</span>
              </div>
            </div>
          </div>
        </div>

        <div class="shop-actions">
          <el-button type="primary" size="large" class="follow-btn">
            <el-icon class="mr-2"><StarIcon /></el-icon>
            关注店铺
          </el-button>
          <el-button size="large" class="contact-btn">
            <el-icon class="mr-2"><ChatBubbleOvalLeftEllipsisIcon /></el-icon>
            联系店主
          </el-button>
        </div>

        <div class="shop-description">
          <h3 class="section-title">
            <el-icon class="title-icon"><InformationCircleIcon /></el-icon>
            店铺介绍
          </h3>
          <p class="description-text">{{ shop.description }}</p>
        </div>

        <div class="shop-announcement">
          <h3 class="section-title">
            <el-icon class="title-icon"><SpeakerWaveIcon /></el-icon>
            店铺公告
          </h3>
          <div class="announcement-content">
            {{ shop.announcement }}
          </div>
        </div>
      </div>
    </template>

    <template #content>
      <div class="product-section">
        <div class="section-header">
          <h2 class="section-title">
            <el-icon class="title-icon"><CubeIcon /></el-icon>
            {{ shop.name }} 的商品
          </h2>
          <div class="section-actions">
            <el-input
              v-model="searchQuery"
              placeholder="搜索商品..."
              class="search-input"
              clearable
            >
              <template #prefix>
                <el-icon><MagnifyingGlassIcon /></el-icon>
              </template>
            </el-input>
            <el-select v-model="sortBy" placeholder="排序方式" class="sort-select">
              <el-option label="最新发布" value="newest" />
              <el-option label="价格从低到高" value="price-asc" />
              <el-option label="价格从高到低" value="price-desc" />
              <el-option label="最受欢迎" value="popular" />
            </el-select>
          </div>
        </div>

        <div class="products-grid">
          <div
            class="product-card"
            v-for="product in shop.products"
            :key="product.id"
            @click="goToProduct(product.id)"
          >
            <div class="product-image">
              <img :src="product.image" :alt="product.name" />
              <div class="product-overlay">
                <el-button type="primary" size="small">查看详情</el-button>
              </div>
            </div>
            <div class="product-info">
              <h4 class="product-name">{{ product.name }}</h4>
              <div class="product-price">¥{{ product.price }}</div>
              <div class="product-stats">
                <span class="stat">
                  <el-icon><HeartIcon /></el-icon>
                  128
                </span>
                <span class="stat">
                  <el-icon><EyeIcon /></el-icon>
                  1.2k
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </ShopPageLayout>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import ShopPageLayout from './ShopPageLayout.vue';
import { useRouter } from 'vue-router';
import {
  UserGroupIcon,
  ShoppingBagIcon,
  StarIcon,
  ChatBubbleOvalLeftEllipsisIcon,
  InformationCircleIcon,
  SpeakerWaveIcon,
  CubeIcon,
  MagnifyingGlassIcon,
  HeartIcon,
  EyeIcon
} from '@heroicons/vue/24/outline';

const router = useRouter();
const searchQuery = ref('');
const sortBy = ref('newest');

const shop = ref({
  name: '数字艺术工作室',
  description: '专注于高质量数字艺术作品和创意设计，致力于为���户提供独特的视觉体验。我们的作品涵盖插画、3D建模、UI设计等多个领域。',
  announcement: '🎉 新品上架！所有商品七天无理由退换，质量保证。欢迎关注我们的最新作品！',
  products: [
    {
      id: 1,
      name: '科幻风格数字插画',
      price: 299,
      image: '/assets/product1.jpg'
    },
    {
      id: 2,
      name: '高精度3D角色模型',
      price: 599,
      image: '/assets/product2.jpg'
    },
    {
      id: 3,
      name: 'UI设计素材包',
      price: 199,
      image: '/assets/product3.jpg'
    },
    {
      id: 4,
      name: '动漫风格头像定制',
      price: 399,
      image: '/assets/product4.jpg'
    }
  ]
});

const goToProduct = (productId: number) => {
  router.push(`/item/${productId}`);
};
</script>

<style scoped>
.shop-info-card {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.shop-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  gap: 16px;
}

.shop-avatar {
  background: linear-gradient(135deg, #409eff 0%, #67c23a 100%);
  color: white;
  font-size: 24px;
  font-weight: 600;
}

.shop-name {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
  margin: 0;
}

.shop-stats {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-top: 8px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #606266;
}

.stat-icon {
  color: #409eff;
  font-size: 16px;
}

.shop-actions {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.follow-btn,
.contact-btn {
  width: 100%;
  height: 40px;
}

.section-title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 12px 0;
  display: flex;
  align-items: center;
  gap: 8px;
}

.title-icon {
  color: #409eff;
  font-size: 18px;
}

.description-text {
  font-size: 14px;
  line-height: 1.6;
  color: #606266;
  margin: 0;
}

.announcement-content {
  background: #f0f9ff;
  border: 1px solid #b3d8ff;
  border-radius: 6px;
  padding: 12px;
  font-size: 14px;
  color: #1f2937;
  line-height: 1.5;
}

.product-section {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.section-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-bottom: 16px;
  border-bottom: 1px solid #e4e7ed;
}

.section-actions {
  display: flex;
  gap: 16px;
  align-items: center;
}

.search-input {
  width: 200px;
}

.sort-select {
  width: 140px;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
  gap: 20px;
}

.product-card {
  background: white;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  transition: all 0.3s ease;
}

.product-card:hover {
  border-color: #409eff;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.15);
  transform: translateY(-2px);
}

.product-image {
  position: relative;
  width: 100%;
  height: 150px;
  overflow: hidden;
}

.product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.product-overlay {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.product-card:hover .product-overlay {
  opacity: 1;
}

.product-info {
  padding: 16px;
}

.product-name {
  font-size: 14px;
  font-weight: 500;
  color: #303133;
  margin: 0 0 8px 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.product-price {
  font-size: 16px;
  font-weight: 600;
  color: #f56c6c;
  margin-bottom: 8px;
}

.product-stats {
  display: flex;
  justify-content: space-between;
}

.stat {
  display: flex;
  align-items: center;
  gap: 4px;
  font-size: 12px;
  color: #909399;
}

.mr-2 {
  margin-right: 8px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .section-header {
    flex-direction: column;
    gap: 16px;
    align-items: stretch;
  }

  .section-actions {
    flex-direction: column;
    gap: 12px;
  }

  .search-input,
  .sort-select {
    width: 100%;
  }

  .products-grid {
    grid-template-columns: repeat(auto-fill, minmax(150px, 1fr));
    gap: 16px;
  }

  .shop-header {
    flex-direction: row;
    text-align: left;
  }

  .shop-actions {
    flex-direction: row;
  }
}
</style>
