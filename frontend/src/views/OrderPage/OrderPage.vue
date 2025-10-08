<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="order-page">
        <div class="container">
          <div v-if="loading" class="loading-container">
            <el-skeleton animated>
              <template #template>
                <el-skeleton-item variant="text" style="width: 100%; height: 40px; margin-bottom: 20px;"/>
                <el-skeleton-item variant="rect" style="width: 100%; height: 200px; margin-bottom: 20px;"/>
                <el-skeleton-item variant="text" style="width: 100%; height: 100px;"/>
              </template>
            </el-skeleton>
          </div>

          <div v-else class="order-content">
            <div class="order-header">
              <h1>订单详情</h1>
              <div class="order-status">
                <el-tag :type="getOrderStatusType(order.stateCode)" size="large">
                  {{ getOrderStatusText(order.stateCode) }}
                </el-tag>
              </div>
            </div>

            <div class="order-info">
              <div class="section">
                <h3>商品信息</h3>
                <div class="item-card" v-if="item" @click="goToItem(order.itemId)">
                  <div class="item-image">
                    <img
                      v-if="item.images && item.images.length > 0"
                      :src="item.images[0]"
                      alt="商品图片"
                      @error="handleImageError"
                    />
                    <div v-else class="image-placeholder">
                      <el-icon><Picture /></el-icon>
                      <span>暂无图片</span>
                    </div>
                  </div>
                  <div class="item-info">
                    <h4>{{ item.name }}</h4>
                    <div class="item-price">¥{{ formatPrice(item.price) }}</div>
                    <div class="item-seller" v-if="seller">
                      <div class="seller-info">
                        <img
                          v-if="seller.avatarUrl"
                          :src="seller.avatarUrl"
                          alt="卖家头像"
                          class="seller-avatar"
                          @error="handleAvatarError"
                        />
                        <div v-else class="avatar-placeholder">
                          <el-icon><User /></el-icon>
                        </div>
                        <span class="seller-name">{{ seller.username }}</span>
                      </div>
                    </div>
                  </div>
                </div>
                <div v-else class="loading-item">
                  <el-skeleton animated>
                    <template #template>
                      <div style="display: flex; gap: 16px;">
                        <el-skeleton-item variant="image" style="width: 100px; height: 100px;" />
                        <div style="flex: 1;">
                          <el-skeleton-item variant="text" style="width: 60%; margin-bottom: 8px;" />
                          <el-skeleton-item variant="text" style="width: 40%; margin-bottom: 8px;" />
                          <el-skeleton-item variant="text" style="width: 50%;" />
                        </div>
                      </div>
                    </template>
                  </el-skeleton>
                </div>
              </div>

              <div class="section">
                <h3>订单信息</h3>
                <div class="info-grid">
                  <div class="info-item">
                    <span class="label">订单号：</span>
                    <span class="value">{{ order.id }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">用户ID：</span>
                    <span class="value">{{ order.userId }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">商品ID：</span>
                    <span class="value">{{ order.itemId }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">创建时间：</span>
                    <span class="value">{{ formatTime(order.createdAt) }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">订单金额：</span>
                    <span class="value price">¥{{ formatPrice(order.payAmount) }}</span>
                  </div>
                  <div class="info-item" v-if="order.payAt">
                    <span class="label">付款时间：</span>
                    <span class="value">{{ formatTime(order.payAt) }}</span>
                  </div>
                  <div class="info-item" v-if="order.deliveryAt">
                    <span class="label">发货时间：</span>
                    <span class="value">{{ formatTime(order.deliveryAt) }}</span>
                  </div>
                  <div class="info-item" v-if="order.receiveAt">
                    <span class="label">收货时间：</span>
                    <span class="value">{{ formatTime(order.receiveAt) }}</span>
                  </div>
                  <div class="info-item" v-if="order.finishAt">
                    <span class="label">��成时间：</span>
                    <span class="value">{{ formatTime(order.finishAt) }}</span>
                  </div>
                </div>
              </div>

              <div class="section">
                <h3>交易状态</h3>
                <div class="status-timeline">
                  <el-steps :active="getActiveStep(order.stateCode)" direction="vertical">
                    <el-step title="订单创建" description="订单已创建，等待买家付款"/>
                    <el-step title="等待发货" description="买家已付款，等待卖家发货"/>
                    <el-step title="已发货" description="卖家已发货，商品正在运输中"/>
                    <el-step title="交易完成" description="买家已确认收货，交易完成"/>
                  </el-steps>
                </div>
              </div>

              <div class="section">
                <h3>操作</h3>
                <div class="order-actions">
                  <el-button
                      v-if="order.stateCode === 1"
                      type="primary"
                      @click="handlePayment"
                  >
                    立即付款
                  </el-button>
                  <el-button
                      v-if="order.stateCode === 3"
                      type="success"
                      @click="confirmReceipt"
                  >
                    确认收货
                  </el-button>
                  <el-button
                      v-if="[1, 2].includes(order.stateCode)"
                      type="danger"
                      @click="cancelOrder"
                  >
                    取消订单
                  </el-button>
                  <el-button
                      v-if="order.stateCode === 4"
                      type="warning"
                      @click="requestReturn"
                  >
                    申请退货
                  </el-button>
                  <el-button @click="goToItem(order.itemId)">查看商品</el-button>
                </div>
              </div>
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
import {ref, onMounted, computed} from "vue";
import {useRoute, useRouter} from "vue-router";
import {ElMessage, ElMessageBox, ElIcon} from "element-plus";
import {Picture, User} from "@element-plus/icons-vue";
import {getOrderVO} from "@/services/order.ts";
import {getItemVO} from "@/services/item.ts";
import {getUserVO} from "@/services/user.ts";
import type {OrderVO} from "@/types/order";
import type {ItemVO} from "@/types/item";
import type {UserVO} from "@/types/user";

const route = useRoute();
const router = useRouter();

// 响应式数据
const order = ref<OrderVO>({});
const item = ref<ItemVO | null>(null);
const seller = ref<UserVO | null>(null);
const loading = ref(true);

// 计算属���
const orderId = computed(() => route.params.id as string);

// 加载订单信息
const loadOrderInfo = async () => {
  try {
    loading.value = true;
    order.value = await getOrderVO(orderId.value);

    // 加载商品信息
    if (order.value.itemId) {
      item.value = await getItemVO(order.value.itemId.toString());

      // 加���卖家信息
      if (item.value && item.value.userId) {
        seller.value = await getUserVO(item.value.userId.toString());
      }
    }
  } catch (error) {
    console.error('加载订单失败:', error);
    ElMessage.error('加载订单失败');
  } finally {
    loading.value = false;
  }
};

// 获取订单��态类型
const getOrderStatusType = (stateCode: number) => {
  switch (stateCode) {
    case 0: return 'danger';   // 已取消
    case 1: return 'warning';  // 待付款
    case 2: return 'info';     // 待发货
    case 3: return 'primary';  // 待收货
    case 4: return 'success';  // 已完成
    case 5: return 'warning';  // 退货中
    case 6: return 'warning';  // 退款中
    default: return '';
  }
};

// 获取订单状态文本
const getOrderStatusText = (stateCode: number) => {
  switch (stateCode) {
    case 0: return '已取消';
    case 1: return '待付款';
    case 2: return '待发货';
    case 3: return '待收货';
    case 4: return '已完成';
    case 5: return '退货中';
    case 6: return '退款中';
    default: return '未知状态';
  }
};

// 获取当前步骤
const getActiveStep = (stateCode: number) => {
  switch (stateCode) {
    case 1: return 0; // 待付款
    case 2: return 1; // 待发货
    case 3: return 2; // 待收货
    case 4: return 3; // 已完成
    default: return 0;
  }
};

// 处理付款
const handlePayment = () => {
  ElMessage.info('跳转到付款页面（功能开���中）');
};

// 确认收货
const confirmReceipt = async () => {
  try {
    await ElMessageBox.confirm(
        '确认已收到商����并满意吗？确认后交易将完成。',
        '确认收货',
        {
          confirmButtonText: '确认收货',
          cancelButtonText: '取消',
          type: 'info',
        }
    );

    ElMessage.success('确认收货成功，交易已完成');
    loadOrderInfo();
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('确认收货失败');
    }
  }
};

// 取消订单
const cancelOrder = async () => {
  try {
    await ElMessageBox.confirm(
        '确定要取消这个订单吗？',
        '取消订单',
        {
          confirmButtonText: '确定取消',
          cancelButtonText: '继续等待',
          type: 'warning',
        }
    );

    ElMessage.success('订单已取消');
    loadOrderInfo();
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('取消订单失败');
    }
  }
};

// 申请退货
const requestReturn = async () => {
  try {
    await ElMessageBox.confirm(
        '确定要申请退货吗？',
        '申请退货',
        {
          confirmButtonText: '确定申请',
          cancelButtonText: '取消',
          type: 'warning',
        }
    );

    ElMessage.success('退货申请已提交');
    loadOrderInfo();
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('申请退货失败');
    }
  }
};

// 跳转到商品详情
const goToItem = (itemId: number) => {
  router.push(`/item/${itemId}`);
};

// 格式化价���
const formatPrice = (price: number) => {
  return price?.toFixed(2) || '0.00';
};

// 格式化时间
const formatTime = (time: string) => {
  if (!time) return '-';
  return new Date(time).toLocaleString();
};

// 图片加载错误处理
const handleImageError = (event: Event) => {
  const target = event.target as HTMLImageElement;
  target.style.display = 'none';
};

// 头像加载错误处理
const handleAvatarError = (event: Event) => {
  const target = event.target as HTMLImageElement;
  target.style.display = 'none';
};

// 页面加载时获取数据
onMounted(() => {
  loadOrderInfo();
});
</script>

<style scoped>
.order-page {
  min-height: 100vh;
  background-color: #f5f5f5;
  padding: 10px 0;
}

.container {
  max-width: 1000px;
  margin: 0 auto;
  padding: 10px 15px;
}

.loading-container {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.order-content {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 12px;
  border-bottom: 1px solid #e4e7ed;
}

.order-header h1 {
  font-size: 20px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0;
}

.section {
  margin-bottom: 24px;
}

.section h3 {
  font-size: 16px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 12px 0;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 12px;
}

.info-item {
  display: flex;
  align-items: center;
  min-height: 32px;
}

.label {
  font-weight: 500;
  color: #606266;
  margin-right: 8px;
  white-space: nowrap;
  font-size: 14px;
}

.value {
  color: #2c3e50;
  font-size: 14px;
  word-break: break-all;
}

.price {
  font-weight: 600;
  color: #e74c3c;
  font-size: 16px;
}

.item-card {
  display: flex;
  gap: 12px;
  padding: 12px;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
  align-items: flex-start;
}

.item-card:hover {
  border-color: #409eff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.1);
}

.item-image {
  width: 80px;
  height: 80px;
  flex-shrink: 0;
}

.item-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 6px;
}

.image-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  background-color: #f5f5f5;
  color: #c0c4cc;
  border-radius: 6px;
  font-size: 11px;
}

.image-placeholder .el-icon {
  font-size: 20px;
  margin-bottom: 2px;
}

.item-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.item-info h4 {
  font-size: 15px;
  font-weight: 500;
  color: #2c3e50;
  margin: 0 0 6px 0;
  line-height: 1.3;
  word-break: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.item-price {
  font-weight: 600;
  color: #e74c3c;
  margin: 4px 0 6px 0;
  font-size: 16px;
}

.item-seller {
  margin-top: auto;
}

.seller-info {
  display: flex;
  align-items: center;
  gap: 6px;
  color: #7f8c8d;
}

.seller-avatar {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  object-fit: cover;
}

.avatar-placeholder {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background-color: #f0f0f0;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #c0c4cc;
}

.avatar-placeholder .el-icon {
  font-size: 12px;
}

.seller-name {
  font-size: 13px;
  color: #7f8c8d;
}

.status-timeline {
  max-width: 350px;
}

.order-actions {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.order-actions .el-button {
  font-size: 14px;
  padding: 8px 16px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .container {
    padding: 8px 10px;
  }

  .order-content {
    padding: 16px;
  }

  .order-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 10px;
  }

  .order-header h1 {
    font-size: 18px;
  }

  .info-grid {
    grid-template-columns: 1fr;
    gap: 8px;
  }

  .item-card {
    gap: 10px;
    padding: 10px;
  }

  .item-image {
    width: 70px;
    height: 70px;
  }

  .image-placeholder {
    width: 70px;
    height: 70px;
  }

  .section {
    margin-bottom: 20px;
  }

  .status-timeline {
    max-width: 100%;
  }

  .order-actions {
    gap: 6px;
  }

  .order-actions .el-button {
    flex: 1;
    min-width: 0;
    font-size: 13px;
    padding: 6px 12px;
  }
}

@media (max-width: 480px) {
  .container {
    padding: 5px 8px;
  }

  .order-content {
    padding: 12px;
  }

  .order-header h1 {
    font-size: 16px;
  }

  .section h3 {
    font-size: 15px;
  }

  .item-image {
    width: 60px;
    height: 60px;
  }

  .image-placeholder {
    width: 60px;
    height: 60px;
  }

  .item-info h4 {
    font-size: 14px;
  }

  .item-price {
    font-size: 15px;
  }
}
</style>
