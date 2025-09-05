<template>
  <ShopManagePageLayout>
    <template #dashboard-header-left>
      <div class="shop-header">
        <div class="shop-info">
          <div class="shop-status">
            <el-tag :type="getStatusType(shopManagePageData.state)">
              <el-icon>
                <EyeIcon v-if="shopManagePageData.state === 'public'"/>
                <EyeSlashIcon v-else/>
              </el-icon>
              {{ getStatusText(shopManagePageData.state) }}
            </el-tag>
          </div>

          <div class="shop-name-section">
            <h1 class="shop-name">{{ shopManagePageData.name || '未命名店铺' }}</h1>
          </div>

          <div class="shop-description" v-if="shopManagePageData.description">
            {{ shopManagePageData.description }}
          </div>

          <div class="shop-details">
            <div class="detail-item">
              <el-icon>
                <LinkIcon/>
              </el-icon>
              <el-link :href="shopUrl" target="_blank" type="primary" :underline="false">
                {{ shopUrl }}
              </el-link>
            </div>
            <div class="detail-item">
              <el-icon>
                <UserPlusIcon/>
              </el-icon>
              <span>{{ formatNumber(shopManagePageData.followers) }} 关注者</span>
            </div>
          </div>
        </div>

        <div class="shop-actions">
          <el-button type="default" plain @click="handleShopSettings">
            <el-icon>
              <CogIcon/>
            </el-icon>
            设置
          </el-button>
          <el-button
              :type="shopManagePageData.state === 'public' ? 'warning' : 'success'"
              @click="handleToggleStatus"
              :loading="statusLoading"
          >
            <el-icon>
              <EyeSlashIcon v-if="shopManagePageData.state === 'public'"/>
              <EyeIcon v-else/>
            </el-icon>
            {{ shopManagePageData.state === 'public' ? '设为私有' : '设为公开' }}
          </el-button>
        </div>
      </div>
    </template>

    <template #dashboard-pane-left>
      <!-- 收益概览 -->
      <div class="card">
        <h3 class="card-title">
          <el-icon class="card-icon">
            <CurrencyDollarIcon/>
          </el-icon>
          收益概览
        </h3>
        <div class="stats-grid">
          <div class="stat-item">
            <div class="stat-value">¥{{ revenueData.day }}</div>
            <div class="stat-label">今日收益</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">¥{{ revenueData.month }}</div>
            <div class="stat-label">本月收益</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">¥{{ revenueData.total }}</div>
            <div class="stat-label">总收益</div>
          </div>
        </div>
      </div>

      <!-- 店铺数据 -->
      <div class="card">
        <h3 class="card-title">
          <el-icon class="card-icon">
            <ChartPieIcon/>
          </el-icon>
          店铺数据
        </h3>
        <div class="stats-grid">
          <div class="stat-item">
            <div class="stat-value">{{ formatNumber(analyticsData.totalProducts) }}</div>
            <div class="stat-label">商品总数</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">{{ formatNumber(analyticsData.totalOrders) }}</div>
            <div class="stat-label">订单总数</div>
          </div>
          <div class="stat-item">
            <div class="stat-value">{{ formatNumber(analyticsData.monthlyViews) }}</div>
            <div class="stat-label">月访问量</div>
          </div>
        </div>
      </div>
    </template>

    <template #dashboard-pane-right>
      <!-- 管理功能 -->
      <div class="card">
        <h3 class="card-title">
          <el-icon class="card-icon">
            <Squares2X2Icon/>
          </el-icon>
          管理功能
        </h3>

        <div class="nav-list">
          <div class="nav-item" @click="goToRoute('shop-manage-item')">
            <div class="nav-icon">
              <el-icon>
                <ShoppingBagIcon/>
              </el-icon>
            </div>
            <div class="nav-content">
              <div class="nav-title">商品管理</div>
              <div class="nav-desc">{{ formatNumber(analyticsData.totalProducts) }} 件商品</div>
            </div>
            <el-icon class="nav-arrow">
              <ChevronRightIcon/>
            </el-icon>
          </div>

          <div class="nav-item" @click="goToRoute('shop-orders')">
            <div class="nav-icon">
              <el-icon>
                <DocumentTextIcon/>
              </el-icon>
            </div>
            <div class="nav-content">
              <div class="nav-title">订单管理</div>
              <div class="nav-desc">{{ formatNumber(analyticsData.pendingOrders) }} 待处理</div>
            </div>
            <el-icon class="nav-arrow">
              <ChevronRightIcon/>
            </el-icon>
          </div>

          <div class="nav-item" @click="goToRoute('shop-revenue')">
            <div class="nav-icon">
              <el-icon>
                <ChartBarIcon/>
              </el-icon>
            </div>
            <div class="nav-content">
              <div class="nav-title">收益分析</div>
              <div class="nav-desc">本月 +¥{{ revenueData.month }}</div>
            </div>
            <el-icon class="nav-arrow">
              <ChevronRightIcon/>
            </el-icon>
          </div>

          <div class="nav-item" @click="handleShopSettings">
            <div class="nav-icon">
              <el-icon>
                <CogIcon/>
              </el-icon>
            </div>
            <div class="nav-content">
              <div class="nav-title">店铺设置</div>
              <div class="nav-desc">个性化配置</div>
            </div>
            <el-icon class="nav-arrow">
              <ChevronRightIcon/>
            </el-icon>
          </div>
        </div>

        <div class="actions-section">
          <div class="actions-title">快捷操作</div>
          <div class="actions-buttons">
            <el-button type="primary" @click="goToRoute('shop-manage-item')" size="small">
              <el-icon>
                <PlusIcon/>
              </el-icon>
              添加商品
            </el-button>
            <el-button type="success" @click="goToRoute('shop-orders')" size="small">
              <el-icon>
                <TruckIcon/>
              </el-icon>
              处理订单
            </el-button>
          </div>
        </div>
      </div>
    </template>
  </ShopManagePageLayout>
</template>

<script setup lang="ts">
import {ref, computed, onMounted} from 'vue';
import {useRouter} from 'vue-router';
import {ElMessage, ElMessageBox} from 'element-plus';
import {
  UserPlusIcon,
  ShoppingBagIcon,
  CogIcon,
  EyeIcon,
  EyeSlashIcon,
  CurrencyDollarIcon,
  Squares2X2Icon,
  DocumentTextIcon,
  ChartBarIcon,
  ChevronRightIcon,
  LinkIcon,
  ChartPieIcon,
  PlusIcon,
  TruckIcon
} from '@heroicons/vue/24/outline';
import ShopManagePageLayout from '@/components/page/ShopManagePage/ShopManagePageLayout.vue';
import {getShopManagePageVO} from '@/api/user.ts';
import type {ShopManagePageVO} from '@/types/UserTypes';

// 响应式数据
const router = useRouter();
const shopUrl = ref('');
const statusLoading = ref(false);

const shopManagePageData = ref<ShopManagePageVO>({
  userId: 0,
  name: '',
  description: '',
  followers: 0,
  state: 'private'
});

const revenueData = ref({
  day: '1,234.56',
  month: '12,345.67',
  total: '123,456.78',
});

const analyticsData = ref({
  totalProducts: 128,
  totalOrders: 856,
  monthlyViews: 12500,
  pendingOrders: 23,
});

// 计算属性
const isPublicShop = computed(() => shopManagePageData.value.state === 'public');

// 工具函数
const formatNumber = (num: number): string => {
  if (!num) return '0';
  return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
};

const getStatusText = (state: string): string => {
  return state === 'public' ? '公开中' : '私密中';
};

const getStatusType = (state: string): 'success' | 'danger' => {
  return state === 'public' ? 'success' : 'danger';
};

const generateShopUrl = (userId: number): string => {
  return `https://bakabooth.com/shop/${userId}`;
};

// 导航函数
const goToRoute = (name: string) => {
  router.push({name});
};

const handleShopSettings = () => {
  ElMessage.info('店铺设置功能开发中...');
  // TODO: 跳转到店铺设置页面
  // router.push({ name: 'shop-settings' });
};

const handleToggleStatus = async () => {
  const action = isPublicShop.value ? '设为私有' : '设为公开';
  const message = isPublicShop.value
      ? '设为私有后，其他用户将无法访问您的店铺。确定要继续吗？'
      : '设为公开后，其他用户可以访问您的店铺。确定要继续吗？';

  try {
    await ElMessageBox.confirm(message, `确认${action}`, {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });

    statusLoading.value = true;

    // 模拟API调用
    setTimeout(() => {
      const newState = isPublicShop.value ? 'private' : 'public';
      shopManagePageData.value.state = newState;
      statusLoading.value = false;

      ElMessage.success(
          `店铺已${newState === 'public' ? '设为公开' : '设为私有'}`
      );
    }, 1000);

  } catch {
    // 用户取消操作
  }
};

// 初始化数据
const initializeData = async () => {
  try {
    shopManagePageData.value = await getShopManagePageVO();
    shopUrl.value = generateShopUrl(shopManagePageData.value.userId);

    // 模拟加载分析数据
    // TODO: 从API获取真实数据
    analyticsData.value = {
      totalProducts: Math.floor(Math.random() * 200) + 50,
      totalOrders: Math.floor(Math.random() * 1000) + 200,
      monthlyViews: Math.floor(Math.random() * 20000) + 5000,
      pendingOrders: Math.floor(Math.random() * 50) + 5,
    };

  } catch (error) {
    ElMessage.error('加载店铺数据失败，请刷新页面重试');
    console.error('Failed to load shop data:', error);
  }
};

// 生命周期钩子
onMounted(() => {
  initializeData();
});
</script>

<style scoped>
/* 店铺头部信息 */
.shop-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  gap: 32px;
}

.shop-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.shop-name-section {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
}

.shop-name {
  font-size: 28px;
  font-weight: 700;
  color: #1f2937;
  line-height: 1.2;
  margin: 0;
}

.shop-description {
  font-size: 15px;
  line-height: 1.6;
  background: #f0f9ff;
  padding: 16px 20px;
  border-radius: 8px;
  border-left: 4px solid #3b82f6;
  color: #1e40af;
}

.shop-details {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  align-items: center;
}

.detail-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #4b5563;
  background: #f9fafb;
  padding: 8px 12px;
  border-radius: 6px;
  border: 1px solid #e5e7eb;
}

.detail-item .el-icon {
  width: 18px;
  height: 18px;
  color: #6b7280;
}

/* 操作按钮 */
.shop-actions {
  min-width: 240px;
  display: flex;
  flex-direction: column;
  gap: 14px;
}

.shop-actions .el-button {
  width: 100%;
  justify-content: center;
  height: 40px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 8px;
}

/* 快捷操作区域 */
.actions-section {
  margin-top: 24px;
  padding-top: 20px;
  border-top: 1px solid #e5e7eb;
}

.actions-title {
  font-size: 14px;
  font-weight: 600;
  color: #4b5563;
  margin-bottom: 16px;
}

.actions-buttons {
  display: flex;
  gap: 12px;
}

.actions-buttons .el-button {
  flex: 1;
  height: 36px;
  font-size: 13px;
  border-radius: 6px;
}

/* 标签样式 */
.shop-status .el-tag {
  height: 28px;
  padding: 0 12px;
  font-size: 13px;
  font-weight: 500;
  border-radius: 6px;
}

.shop-status .el-tag .el-icon {
  width: 16px;
  height: 16px;
  margin-right: 6px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .shop-header {
    flex-direction: column;
    gap: 24px;
  }

  .shop-name-section {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
  }

  .shop-name {
    font-size: 24px;
  }

  .shop-details {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .shop-actions {
    width: 100%;
  }

  .actions-buttons {
    flex-direction: column;
    gap: 8px;
  }
}
</style>