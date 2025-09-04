<template>
  <ShopManagePageLayout>
    <template #dashboard-header-left>
      <div class="shop-info">
        <div class="shop-status">
          <el-tag :type="shopData.isPublic ? 'success' : 'warning'" size="small">
            {{ shopData.isPublic ? '店铺公开中' : '店铺隐藏中' }}
          </el-tag>
        </div>

        <div class="shop-name">
          {{ shopData.name }}
          <el-button type="primary" text size="small" @click="handleEditShop">
            [编辑]
          </el-button>
        </div>

        <div class="shop-url">
          <el-link :href="shopData.url" target="_blank" type="primary" :underline="false">
            {{ shopData.url }}
          </el-link>
        </div>

        <div class="shop-followers">
          <el-icon class="follower-icon">
            <UserPlusIcon/>
          </el-icon>
          <span>{{ shopData.followers }} 关注者</span>
        </div>
      </div>
    </template>

    <template #dashboard-header-right>
      <div class="shop-actions">
        <el-button
            type="info"
            plain
            @click="handleShopSettings"
            class="action-button"
        >
          <el-icon class="mr-2">
            <CogIcon/>
          </el-icon>
          店铺设置
        </el-button>
        <el-button
            :type="shopData.isPublic ? 'warning' : 'success'"
            @click="handleToggleStatus"
            class="action-button"
        >
          <el-icon class="mr-2">
            <EyeIcon v-if="!shopData.isPublic"/>
            <EyeSlashIcon v-else/>
          </el-icon>
          {{ shopData.isPublic ? '隐藏店铺' : '公开店铺' }}
        </el-button>
      </div>
    </template>

    <template #dashboard-pane-left>
      <div class="revenue-section">
        <h3 class="section-title">
          <el-icon class="title-icon">
            <CurrencyDollarIcon/>
          </el-icon>
          收益概览
        </h3>
        <div class="revenue-stats">
          <div class="stat-item">
            <div class="stat-label">今日收益</div>
            <div class="stat-value">¥{{ revenueData.today }}</div>
          </div>
          <div class="stat-item">
            <div class="stat-label">本月收益</div>
            <div class="stat-value">¥{{ revenueData.thisMonth }}</div>
          </div>
          <div class="stat-item">
            <div class="stat-label">总收益</div>
            <div class="stat-value">¥{{ revenueData.total }}</div>
          </div>
        </div>
      </div>
    </template>

    <template #dashboard-pane-right>
      <div class="management-nav">
        <h3 class="section-title">
          <el-icon class="title-icon">
            <Squares2X2Icon/>
          </el-icon>
          管理功能
        </h3>

        <div class="nav-grid">
          <div class="nav-card" @click="goToRoute('shop-manage-item')">
            <div class="nav-card-icon">
              <el-icon size="24">
                <ShoppingBagIcon/>
              </el-icon>
            </div>
            <div class="nav-card-content">
              <h4>商品管理</h4>
              <p>管理您的商品信息</p>
            </div>
            <div class="nav-card-arrow">
              <el-icon>
                <ChevronRightIcon/>
              </el-icon>
            </div>
          </div>

          <div class="nav-card" @click="goToRoute('shop-orders')">
            <div class="nav-card-icon">
              <el-icon size="24">
                <DocumentTextIcon/>
              </el-icon>
            </div>
            <div class="nav-card-content">
              <h4>订单管理</h4>
              <p>查看和处理订单</p>
            </div>
            <div class="nav-card-arrow">
              <el-icon>
                <ChevronRightIcon/>
              </el-icon>
            </div>
          </div>

          <div class="nav-card" @click="goToRoute('shop-revenue')">
            <div class="nav-card-icon">
              <el-icon size="24">
                <ChartBarIcon/>
              </el-icon>
            </div>
            <div class="nav-card-content">
              <h4>收益管理</h4>
              <p>查看收益统计</p>
            </div>
            <div class="nav-card-arrow">
              <el-icon>
                <ChevronRightIcon/>
              </el-icon>
            </div>
          </div>
        </div>
      </div>
    </template>
  </ShopManagePageLayout>
</template>

<script setup lang="ts">
import ShopManagePageLayout from "@/components/page/ShopManagePage/ShopManagePageLayout.vue";
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
  ChevronRightIcon
} from "@heroicons/vue/24/outline";
import {useRouter} from "vue-router";
import {ref} from "vue";

const router = useRouter();

const shopData = ref({
  name: 'tamakara',
  url: 'xxx.tamakara.com',
  followers: 666,
  isPublic: false
});

const revenueData = ref({
  today: '1,234.56',
  thisMonth: '12,345.67',
  total: '123,456.78'
});

const goToRoute = (name: string) => router.push({name});

function handleEditShop() {
  console.log('编辑店铺信息');
}

function handleShopSettings() {
  console.log('店铺设置');
}

function handleToggleStatus() {
  shopData.value.isPublic = !shopData.value.isPublic;
}
</script>

<style scoped>
.shop-info {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.shop-status .el-tag {
  font-size: 12px;
  padding: 4px 8px;
}

.shop-name {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  display: flex;
  align-items: center;
  gap: 8px;
}

.shop-url {
  font-size: 14px;
}

.shop-followers {
  font-size: 14px;
  color: #606266;
  display: flex;
  align-items: center;
  gap: 8px;
}

.follower-icon {
  color: #409eff;
}

.shop-actions {
  display: flex;
  gap: 16px;
  align-items: center;
}

.action-button {
  height: 42px;
  padding: 0 16px;
  font-size: 14px;
}

.section-title {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 20px 0;
  display: flex;
  align-items: center;
  gap: 8px;
}

.title-icon {
  color: #409eff;
}

.revenue-stats {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.stat-item {
  padding: 16px;
  background: white;
  border-radius: 8px;
  border: 1px solid #e4e7ed;
  transition: all 0.3s ease;
}

.stat-item:hover {
  border-color: #409eff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.1);
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 20px;
  font-weight: 600;
  color: #409eff;
}

.nav-grid {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.nav-card {
  display: flex;
  align-items: center;
  padding: 16px;
  background: white;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.nav-card:hover {
  border-color: #409eff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.1);
  transform: translateY(-1px);
}

.nav-card-icon {
  width: 48px;
  height: 48px;
  border-radius: 8px;
  background: linear-gradient(135deg, #409eff 0%, #67c23a 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  margin-right: 16px;
}

.nav-card-content {
  flex: 1;
}

.nav-card-content h4 {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 4px 0;
}

.nav-card-content p {
  font-size: 12px;
  color: #909399;
  margin: 0;
}

.nav-card-arrow {
  color: #c0c4cc;
}

.mr-2 {
  margin-right: 8px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .shop-actions {
    flex-direction: column;
    gap: 12px;
    width: 100%;
  }

  .action-button {
    width: 100%;
  }

  .revenue-stats {
    gap: 12px;
  }

  .nav-card {
    padding: 12px;
  }

  .nav-card-icon {
    width: 40px;
    height: 40px;
    margin-right: 12px;
  }
}
</style>