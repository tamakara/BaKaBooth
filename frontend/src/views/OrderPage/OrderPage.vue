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
                <el-tag :type="getOrderStatusType(order.status)" size="large">
                  {{ getOrderStatusText(order.status) }}
                </el-tag>
              </div>
            </div>

            <div class="order-info">
              <div class="section">
                <h3>订单信息</h3>
                <div class="info-grid">
                  <div class="info-item">
                    <span class="label">订单号：</span>
                    <span class="value">{{ order.id }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">创建时间：</span>
                    <span class="value">{{ formatTime(order.createdAt) }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">订单金额：</span>
                    <span class="value price">¥{{ formatPrice(order.totalAmount) }}</span>
                  </div>
                </div>
              </div>

              <div class="section">
                <h3>商品信息</h3>
                <div class="item-card" @click="goToItem(order.itemId)">
                  <div class="item-image">
                    <el-image
                        :src="order.itemImage"
                        fit="cover"
                        style="width: 100px; height: 100px; border-radius: 8px;"
                    >
                      <template #placeholder>
                        <div class="image-placeholder">
                          <el-icon><Picture/></el-icon>
                        </div>
                      </template>
                      <template #error>
                        <div class="image-placeholder">
                          <el-icon><Picture/></el-icon>
                        </div>
                      </template>
                    </el-image>
                  </div>
                  <div class="item-info">
                    <h4>{{ order.itemName }}</h4>
                    <p class="item-price">¥{{ formatPrice(order.itemPrice) }}</p>
                    <p class="item-seller">卖家：{{ order.sellerName }}</p>
                  </div>
                </div>
              </div>

              <div class="section">
                <h3>交易状态</h3>
                <div class="status-timeline">
                  <el-steps :active="getActiveStep(order.status)" direction="vertical">
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
                      v-if="order.status === 'PENDING_PAYMENT'"
                      type="primary"
                      @click="handlePayment"
                  >
                    立即付款
                  </el-button>
                  <el-button
                      v-if="order.status === 'SHIPPED'"
                      type="success"
                      @click="confirmReceipt"
                  >
                    确认收货
                  </el-button>
                  <el-button
                      v-if="['PENDING_PAYMENT', 'PENDING_SHIPMENT'].includes(order.status)"
                      type="danger"
                      @click="cancelOrder"
                  >
                    取消订单
                  </el-button>
                  <el-button @click="contactSeller">联系卖家</el-button>
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
import {ElMessage, ElMessageBox} from "element-plus";
import {Picture} from "@element-plus/icons-vue";
import {getOrderVO} from "@/services/order.ts";

const route = useRoute();
const router = useRouter();

// 响应式数据
const order = ref<any>({});
const loading = ref(true);

// 计算属性
const orderId = computed(() => route.params.id as string);

// 加载订单信息
const loadOrderInfo = async () => {
  try {
    loading.value = true;
    order.value = await getOrderVO(orderId.value);
  } catch (error) {
    console.error('加载订单失败:', error);
    ElMessage.error('加载订单失败');
  } finally {
    loading.value = false;
  }
};

// 获取订单状态类型
const getOrderStatusType = (status: string) => {
  switch (status) {
    case 'PENDING_PAYMENT': return 'warning';
    case 'PENDING_SHIPMENT': return 'info';
    case 'SHIPPED': return 'primary';
    case 'COMPLETED': return 'success';
    case 'CANCELLED': return 'danger';
    default: return '';
  }
};

// 获取订单状态文本
const getOrderStatusText = (status: string) => {
  switch (status) {
    case 'PENDING_PAYMENT': return '待付款';
    case 'PENDING_SHIPMENT': return '待发货';
    case 'SHIPPED': return '已发货';
    case 'COMPLETED': return '已完成';
    case 'CANCELLED': return '已取消';
    default: return '未知状态';
  }
};

// 获取当前步骤
const getActiveStep = (status: string) => {
  switch (status) {
    case 'PENDING_PAYMENT': return 0;
    case 'PENDING_SHIPMENT': return 1;
    case 'SHIPPED': return 2;
    case 'COMPLETED': return 3;
    default: return 0;
  }
};

// 处理付款
const handlePayment = () => {
  ElMessage.info('跳转到付款页面（功能开发中）');
};

// 确认收货
const confirmReceipt = async () => {
  try {
    await ElMessageBox.confirm(
        '确认已收到商品并满意吗？确认后交易将完成。',
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

// 联系卖家
const contactSeller = () => {
  ElMessage.info('联系卖家功能开发中');
};

// 跳转到商品详情
const goToItem = (itemId: number) => {
  router.push(`/item/${itemId}`);
};

// 格式化价格
const formatPrice = (price: number) => {
  return price?.toFixed(2) || '0.00';
};

// 格式化时间
const formatTime = (time: string) => {
  return new Date(time).toLocaleString();
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
}

.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.loading-container {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.order-content {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e4e7ed;
}

.order-header h1 {
  font-size: 24px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0;
}

.section {
  margin-bottom: 32px;
}

.section h3 {
  font-size: 18px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 16px 0;
}

.info-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 16px;
}

.info-item {
  display: flex;
  align-items: center;
}

.label {
  font-weight: 500;
  color: #606266;
  margin-right: 8px;
}

.value {
  color: #2c3e50;
}

.price {
  font-weight: 600;
  color: #e74c3c;
  font-size: 16px;
}

.item-card {
  display: flex;
  gap: 16px;
  padding: 16px;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  cursor: pointer;
  transition: all 0.3s;
}

.item-card:hover {
  border-color: #409eff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.1);
}

.image-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100px;
  height: 100px;
  background-color: #f5f5f5;
  color: #c0c4cc;
  border-radius: 8px;
}

.item-info h4 {
  font-size: 16px;
  font-weight: 500;
  color: #2c3e50;
  margin: 0 0 8px 0;
}

.item-price {
  font-weight: 600;
  color: #e74c3c;
  margin: 4px 0;
}

.item-seller {
  color: #7f8c8d;
  margin: 4px 0;
}

.status-timeline {
  max-width: 400px;
}

.order-actions {
  display: flex;
  gap: 12px;
  flex-wrap: wrap;
}
</style>
