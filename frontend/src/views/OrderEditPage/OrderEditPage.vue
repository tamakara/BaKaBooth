<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="order-edit-page">
        <div class="container">
          <div v-if="loading" class="loading-container">
            <el-skeleton animated>
              <template #template>
                <el-skeleton-item variant="text" style="width: 100%; height: 40px; margin-bottom: 20px;"/>
                <el-skeleton-item variant="rect" style="width: 100%; height: 300px;"/>
              </template>
            </el-skeleton>
          </div>

          <div v-else class="order-content">
            <div class="page-header">
              <h1>订单处理</h1>
              <div class="order-status">
                <el-tag :type="getOrderStatusType(order.status)" size="large">
                  {{ getOrderStatusText(order.status) }}
                </el-tag>
              </div>
            </div>

            <!-- 订单基本信息 -->
            <div class="section">
              <h3>订单信息</h3>
              <div class="order-basic-info">
                <div class="info-grid">
                  <div class="info-item">
                    <span class="label">订单号：</span>
                    <span class="value">{{ order.id }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">买家：</span>
                    <span class="value">{{ order.buyerName }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">下单时间：</span>
                    <span class="value">{{ formatTime(order.createdAt) }}</span>
                  </div>
                  <div class="info-item">
                    <span class="label">订单金额：</span>
                    <span class="value price">¥{{ formatPrice(order.totalAmount) }}</span>
                  </div>
                </div>
              </div>
            </div>

            <!-- 商品信息 -->
            <div class="section">
              <h3>商品信息</h3>
              <div class="item-info">
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
                <div class="item-details">
                  <h4>{{ order.itemName }}</h4>
                  <p class="item-price">¥{{ formatPrice(order.itemPrice) }}</p>
                  <p class="item-description">{{ order.itemDescription }}</p>
                </div>
              </div>
            </div>

            <!-- 收货地址 -->
            <div class="section" v-if="order.shippingAddress">
              <h3>收货地址</h3>
              <div class="address-info">
                <p><strong>收货人：</strong>{{ order.shippingAddress.receiverName }}</p>
                <p><strong>联系电话：</strong>{{ order.shippingAddress.phone }}</p>
                <p><strong>收货地址：</strong>{{ order.shippingAddress.fullAddress }}</p>
              </div>
            </div>

            <!-- 订单操作 -->
            <div class="section">
              <h3>订单操作</h3>
              <div class="order-actions">
                <template v-if="order.status === 'PENDING_PAYMENT'">
                  <el-button type="warning" @click="cancelOrder">取消订单</el-button>
                  <p class="action-note">等待买家付款中...</p>
                </template>

                <template v-else-if="order.status === 'PENDING_SHIPMENT'">
                  <el-form
                      ref="shipFormRef"
                      :model="shipForm"
                      :rules="shipRules"
                      label-position="top"
                      class="ship-form"
                  >
                    <div class="form-row">
                      <el-form-item label="物流公司" prop="shippingCompany" class="form-item">
                        <el-select v-model="shipForm.shippingCompany" placeholder="请选择物流公司">
                          <el-option label="顺丰速运" value="SF"/>
                          <el-option label="圆通速递" value="YTO"/>
                          <el-option label="中通快递" value="ZTO"/>
                          <el-option label="韵达速递" value="YUNDA"/>
                          <el-option label="申通快递" value="STO"/>
                          <el-option label="百世快递" value="BEST"/>
                        </el-select>
                      </el-form-item>
                      <el-form-item label="物流单号" prop="trackingNumber" class="form-item">
                        <el-input
                            v-model="shipForm.trackingNumber"
                            placeholder="请输入物流单号"
                            maxlength="30"
                        />
                      </el-form-item>
                    </div>
                    <el-form-item>
                      <el-button
                          type="primary"
                          @click="confirmShipment"
                          :loading="shipLoading"
                      >
                        确认发货
                      </el-button>
                    </el-form-item>
                  </el-form>
                </template>

                <template v-else-if="order.status === 'SHIPPED'">
                  <div class="shipping-info">
                    <p><strong>物流公司：</strong>{{ order.shippingCompany }}</p>
                    <p><strong>物流单号：</strong>{{ order.trackingNumber }}</p>
                    <el-button @click="trackShipment">查看物流</el-button>
                  </div>
                  <p class="action-note">商品已发货，等待买家确认收货</p>
                </template>

                <template v-else-if="order.status === 'COMPLETED'">
                  <el-result icon="success" title="交易已完成" sub-title="感谢您的交易！"/>
                </template>

                <template v-else-if="order.status === 'CANCELLED'">
                  <el-result icon="error" title="订单已取消" :sub-title="`取消原因：${order.cancelReason || '未知'}`"/>
                </template>
              </div>
            </div>

            <!-- 订单备注 -->
            <div class="section">
              <h3>订单备注</h3>
              <el-input
                  v-model="orderNote"
                  type="textarea"
                  :rows="3"
                  placeholder="添加订单备注..."
                  maxlength="200"
                  show-word-limit
              />
              <div class="note-actions">
                <el-button @click="saveNote" :loading="noteLoading">保存备注</el-button>
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
import {ref, reactive, onMounted, computed} from "vue";
import {useRoute, useRouter} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import type {FormInstance, FormRules} from "element-plus";
import {Picture} from "@element-plus/icons-vue";
import {getOrderVO} from "@/services/order.ts";

const route = useRoute();
const router = useRouter();

// 响应式数据
const order = ref<any>({});
const loading = ref(true);
const shipLoading = ref(false);
const noteLoading = ref(false);
const orderNote = ref('');
const shipFormRef = ref<FormInstance>();

// 发货表单
const shipForm = reactive({
  shippingCompany: '',
  trackingNumber: ''
});

// 发货表单验证规则
const shipRules: FormRules = {
  shippingCompany: [
    { required: true, message: '请选择物流公司', trigger: 'change' }
  ],
  trackingNumber: [
    { required: true, message: '请输入物流单号', trigger: 'blur' },
    { min: 8, message: '物流单号长度不能少于8位', trigger: 'blur' }
  ]
};

// 计算属性
const orderId = computed(() => route.params.id as string);

// 加载订单信息
const loadOrderInfo = async () => {
  try {
    loading.value = true;
    order.value = await getOrderVO(orderId.value);
    orderNote.value = order.value.note || '';
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

// 取消订单
const cancelOrder = async () => {
  try {
    await ElMessageBox.confirm(
        '确定要取消这个订单吗？',
        '取消订单',
        {
          confirmButtonText: '确定取消',
          cancelButtonText: '继续处理',
          type: 'warning',
        }
    );

    // TODO: 调用取消订单API
    ElMessage.success('订单已取消');
    loadOrderInfo();
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('取消订单失败');
    }
  }
};

// 确认发货
const confirmShipment = async () => {
  if (!shipFormRef.value) return;

  try {
    await shipFormRef.value.validate();
    shipLoading.value = true;

    // TODO: 调用确认发货API
    ElMessage.success('发货信息已提交');
    loadOrderInfo();
  } catch (error) {
    ElMessage.error('发货失败');
  } finally {
    shipLoading.value = false;
  }
};

// 查看物流
const trackShipment = () => {
  ElMessage.info('查看物流功能开发中');
};

// 保存备注
const saveNote = async () => {
  try {
    noteLoading.value = true;
    // TODO: 调用保存备注API
    ElMessage.success('备注已保存');
  } catch (error) {
    ElMessage.error('保存备注失败');
  } finally {
    noteLoading.value = false;
  }
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
.order-edit-page {
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

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 32px;
  padding-bottom: 16px;
  border-bottom: 1px solid #e4e7ed;
}

.page-header h1 {
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

.item-info {
  display: flex;
  gap: 16px;
  padding: 16px;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
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

.item-details h4 {
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

.item-description {
  color: #7f8c8d;
  margin: 4px 0;
}

.address-info p {
  margin: 8px 0;
  color: #2c3e50;
}

.ship-form {
  max-width: 500px;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.shipping-info p {
  margin: 8px 0;
  color: #2c3e50;
}

.action-note {
  color: #7f8c8d;
  font-style: italic;
  margin-top: 12px;
}

.note-actions {
  margin-top: 12px;
}
</style>
