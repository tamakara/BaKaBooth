<template>
  <ShopManagePageLayout>
    <template #dashboard-header-left>
      <div class="shop-header">
        <div class="shop-info">
          <!-- 店铺信息统一区域 -->
          <div class="shop-info-grid">
            <!-- 店铺状态 -->
            <div class="info-item">
              <el-icon>
                <EyeIcon v-if="shopManageData.state === 'public'"/>
                <EyeSlashIcon v-else-if="shopManageData.state === 'private'"/>
                <ExclamationTriangleIcon v-else-if="shopManageData.state === 'blocked'"/>
              </el-icon>
              <span :class="['status-text', `status-${shopManageData.state}`]">
                {{ getStatusText(shopManageData.state) }}
              </span>
            </div>
            <!-- 关注者数量 -->
            <div class="info-item">
              <el-icon>
                <UserPlusIcon/>
              </el-icon>
              <span>{{ formatNumber(shopManageData.followers) }} 关注者</span>
            </div>
          </div>

          <!-- 被封禁提示 -->
          <div v-if="shopManageData.state === 'blocked'" class="blocked-notice">
            <el-alert
                title="店铺已被封禁"
                type="error"
                :closable="false"
                show-icon>
              <template #default>
                您的店铺因违反平台规定已被封禁，无法设为公开状态。如有疑问请联系客服。
              </template>
            </el-alert>
          </div>

          <!-- 店铺描述 -->
          <div class="shop-description" v-if="shopManageData.description">
            {{ shopManageData.description }}
          </div>
        </div>

        <div class="shop-actions">
          <el-button
              @click="handleToggleStatus"
              :loading="statusLoading"
              :disabled="shopManageData.state === 'blocked'"
              :type="shopManageData.state === 'blocked' ? 'danger' : 'primary'"
          >
            <el-icon>
              <EyeSlashIcon v-if="shopManageData.state === 'public'"/>
              <EyeIcon v-else-if="shopManageData.state === 'private'"/>
              <ExclamationTriangleIcon v-else-if="shopManageData.state === 'blocked'"/>
            </el-icon>
            {{
              shopManageData.state === 'blocked' ? '已封禁' :
                  shopManageData.state === 'public' ? '设为私有' : '设为公开'
            }}
          </el-button>
          <el-button type="default" plain @click="handleEditDescription">
            <el-icon>
              <DocumentTextIcon/>
            </el-icon>
            设置店铺描述
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
            <div class="stat-label">本月收入</div>
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
          <div class="page-nav-item" @click="goToRoute('item-manage')">
            <div class="page-nav-icon">
              <el-icon>
                <ShoppingBagIcon/>
              </el-icon>
            </div>
            <div class="page-nav-content">
              <div class="page-nav-title">商品管理</div>
              <div class="page-nav-desc">{{ formatNumber(analyticsData.totalProducts) }} 件商品</div>
            </div>
            <el-icon class="page-nav-arrow">
              <ChevronRightIcon/>
            </el-icon>
          </div>

          <div class="page-nav-item" @click="goToRoute('orders')">
            <div class="page-nav-icon">
              <el-icon>
                <DocumentTextIcon/>
              </el-icon>
            </div>
            <div class="page-nav-content">
              <div class="page-nav-title">订单管理</div>
              <div class="page-nav-desc">{{ formatNumber(analyticsData.pendingOrders) }} 待处理</div>
            </div>
            <el-icon class="page-nav-arrow">
              <ChevronRightIcon/>
            </el-icon>
          </div>

          <div class="page-nav-item" @click="goToRoute('revenue')">
            <div class="page-nav-icon">
              <el-icon>
                <ChartBarIcon/>
              </el-icon>
            </div>
            <div class="page-nav-content">
              <div class="page-nav-title">收益管理</div>
              <div class="page-nav-desc">本月 +¥{{ revenueData.month }}</div>
            </div>
            <el-icon class="page-nav-arrow">
              <ChevronRightIcon/>
            </el-icon>
          </div>

          <div class="page-nav-item" @click="handleShopSettings">
            <div class="page-nav-icon">
              <el-icon>
                <CogIcon/>
              </el-icon>
            </div>
            <div class="page-nav-content">
              <div class="page-nav-title">店铺设置</div>
              <div class="page-nav-desc">个性化配置</div>
            </div>
            <el-icon class="page-nav-arrow">
              <ChevronRightIcon/>
            </el-icon>
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
  ChartPieIcon,
  ExclamationTriangleIcon
} from '@heroicons/vue/24/outline';
import ShopManagePageLayout from '@/components/page/ShopManagePage/ShopManagePageLayout.vue';
import {getShopManagePageVO} from '@/api/user.ts';
import type {ShopManageVO} from '@/types/user';

// 响应式数据
const router = useRouter();
const shopUrl = ref('');
const statusLoading = ref(false);

const shopManageData = ref<ShopManageVO>({});

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
const isOpening = computed(() => shopManageData.value.state === 'opening');

// 工具函数
const formatNumber = (num: number): string => {
  if (!num) return '0';
  return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
};

const getStatusText = (state: string): string => {
  switch (state) {
    case 'opening':
      return '营业中';
    case 'closed':
      return '闭店中';
    case 'blocked':
      return '已封禁';
    default:
      return '未知状态';
  }
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
  // 如果店铺被封禁，不允许切换状态
  if (shopManageData.value.state === 'blocked') {
    ElMessage.error('店铺已被封禁，无法修改状态');
    return;
  }

  const action = isOpening.value ? '设为私有' : '设为公开';
  const message = isOpening.value
      ? '设为私有后，其他用户将无法访问您的店铺。确定要继续吗？'
      : '设为公开后其他用户可以访问您的店铺。确定要继续吗？';

  try {
    await ElMessageBox.confirm(message, `确认${action}`, {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    });

    statusLoading.value = true;

    // 模拟API调用
    setTimeout(() => {
      const newState = isOpening.value ? 'private' : 'public';
      shopManageData.value.state = newState;
      statusLoading.value = false;

      ElMessage.success(
          newState === 'public' ? '营业中' : '闭店中'
      );
    }, 1000);

  } catch {
    // 用户取消操作
  }
};

const handleEditDescription = async () => {
  try {
    const {value: description} = await ElMessageBox.prompt(
        '请输入店铺描述',
        '设置店铺描述',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputType: 'textarea',
          inputValue: shopManageData.value.description,
          inputPlaceholder: '请输入店铺描述...',
          inputValidator: (value: string) => {
            if (value && value.length > 500) {
              return '描述不能超过500个字符';
            }
            return true;
          },
          inputErrorMessage: '描述格式不正确'
        }
    );

    // 更新描述
    shopManageData.value.description = description || '';

    // TODO: 调用API保存描述
    // await updateShopDescription(shopManageData.value.userId, description);

    ElMessage.success('店铺描述已更新');

  } catch {
    // 用户取消操作
  }
};

// 初始化数据
const initializeData = async () => {
  try {
    shopManageData.value = await getShopManagePageVO();
    shopUrl.value = generateShopUrl(shopManageData.value.userId);

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

/* 店铺状态和名称区域 */
.shop-status-wrapper {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.shop-status :deep(.el-tag) {
  align-self: flex-start;
  height: 32px;
  padding: 0 16px;
  font-size: 14px;
  font-weight: 600;
  border-radius: 8px;
}

.shop-status :deep(.el-tag .el-icon) {
  width: 18px;
  height: 18px;
  margin-right: 8px;
}

/* 封禁提示 */
.blocked-notice {
  margin: 4px 0;
}

.blocked-notice :deep(.el-alert) {
  border-radius: 8px;
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
  padding: 10px 16px;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
  transition: all 0.2s ease;
}

.detail-item:hover {
  background: #f3f4f6;
  border-color: #d1d5db;
}

.detail-item :deep(.el-icon) {
  width: 18px;
  height: 18px;
  color: #6b7280;
}

/* 操作按钮 */
.shop-actions {
  min-width: 260px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.shop-actions :deep(.el-button) {
  width: 100%;
  justify-content: center;
  height: 44px;
  font-size: 15px;
  font-weight: 600;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.shop-actions :deep(.el-button:disabled) {
  opacity: 0.6;
  cursor: not-allowed;
}

/* 店铺信息网格 */
.shop-info-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 16px;
}

.info-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  color: #4b5563;
  background: #f9fafb;
  padding: 12px 16px;
  border-radius: 8px;
  border: 1px solid #e5e7eb;
  transition: all 0.2s ease;
  min-height: 48px;
  justify-content: flex-start;
}

.info-item:hover {
  background: #f3f4f6;
  border-color: #d1d5db;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.info-item :deep(.el-icon) {
  width: 18px;
  height: 18px;
  color: #6b7280;
  flex-shrink: 0;
}

.info-item :deep(.el-link) {
  font-size: 14px;
  font-weight: 500;
}

/* 状态项统一样式 */
.status-item {
  background: #f9fafb;
  border: 1px solid #e5e7eb;
  padding: 12px 16px;
  justify-content: flex-start;
}

.status-item:hover {
  background: #f3f4f6;
  border-color: #d1d5db;
  transform: translateY(-1px);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}

.status-item :deep(.el-tag) {
  height: auto;
  padding: 4px 12px;
  font-size: 14px;
  font-weight: 500;
  border-radius: 6px;
  border: none;
  margin: 0;
}

.status-item :deep(.el-tag .el-icon) {
  width: 16px;
  height: 16px;
  margin-right: 6px;
  color: inherit;
}

/* 状态文本样式 */
.status-text {
  font-size: 14px;
  font-weight: 500;
}

.status-public {
  color: #16a34a;
}

.status-private {
  color: #f59e0b;
}

.status-blocked {
  color: #dc2626;
}

/* 状态项图标颜色 */
.info-item:has(.status-public) :deep(.el-icon) {
  color: #16a34a !important;
}

.info-item:has(.status-private) :deep(.el-icon) {
  color: #f59e0b !important;
}

.info-item:has(.status-blocked) :deep(.el-icon) {
  color: #dc2626 !important;
}

/* 关注者数量样式 */
.info-item span:not(.status-text) {
  font-weight: 500;
  color: #1f2937;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .shop-header {
    flex-direction: column;
    gap: 24px;
  }

  .shop-details {
    flex-direction: column;
    align-items: flex-start;
    gap: 12px;
  }

  .shop-actions {
    width: 100%;
    min-width: unset;
  }

  .shop-info-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 480px) {

  .shop-actions :deep(.el-button) {
    height: 40px;
    font-size: 14px;
  }
}
</style>