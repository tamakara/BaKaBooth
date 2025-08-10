<template>
  <div class="item-variation">
    <div class="variation-header">
      <div class="variation-info">
        <h4 class="variation-name">{{ name }}</h4>
        <span class="variation-type">{{ type }}</span>
      </div>
      <div class="variation-price">
        <span class="price-currency">¥</span>
        <span class="price-amount">{{ formatPrice(price) }}</span>
      </div>
    </div>

    <div class="variation-actions">
      <el-button
        type="danger"
        size="large"
        class="buy-now-button"
        @click="handleBuyNow"
      >
        <el-icon class="button-icon"><BanknotesIcon /></el-icon>
        立即购买
      </el-button>

      <el-button
        size="large"
        class="add-cart-button"
        @click="handleAddToCart"
      >
        <el-icon class="button-icon"><ShoppingCartIcon /></el-icon>
        加入购物车
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ShoppingCartIcon, BanknotesIcon} from '@heroicons/vue/24/outline'

const props = defineProps<{
  name: string
  type: string
  price: string
}>();

const emit = defineEmits<{
  buyNow: [variation: { name: string; type: string; price: string }]
  addToCart: [variation: { name: string; type: string; price: string }]
}>();

const formatPrice = (price: string) => {
  // 如果价格包含非数字字符，直接返回
  if (!/^\d+(\.\d+)?$/.test(price.replace(/[￥¥,]/g, ''))) {
    return price;
  }
  const numPrice = parseFloat(price.replace(/[￥¥,]/g, ''));
  return numPrice.toFixed(2);
};

const handleBuyNow = () => {
  emit('buyNow', { name: props.name, type: props.type, price: props.price });
};

const handleAddToCart = () => {
  emit('addToCart', { name: props.name, type: props.type, price: props.price });
};
</script>

<style scoped>
.item-variation {
  background: white;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 20px;
  transition: all 0.3s ease;
}

.item-variation:hover {
  border-color: #409eff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.1);
}

.variation-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
  padding-bottom: 16px;
  border-bottom: 1px solid #f0f0f0;
}

.variation-info {
  flex: 1;
}

.variation-name {
  font-size: 18px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 8px 0;
  line-height: 1.4;
}

.variation-type {
  font-size: 14px;
  color: #909399;
  background: #f5f7fa;
  padding: 4px 8px;
  border-radius: 4px;
  display: inline-block;
}

.variation-price {
  display: flex;
  align-items: baseline;
  gap: 2px;
  text-align: right;
}

.price-currency {
  font-size: 16px;
  color: #f56c6c;
  font-weight: 500;
}

.price-amount {
  font-size: 24px;
  font-weight: 700;
  color: #f56c6c;
}

.variation-actions {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.buy-now-button,
.add-cart-button {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 500;
  border-radius: 8px;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
}

.buy-now-button {
  background: linear-gradient(135deg, #f56c6c 0%, #e74c3c 100%);
  border: none;
  color: white;
}

.buy-now-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(245, 108, 108, 0.3);
}

.add-cart-button {
  background: white;
  border: 2px solid #409eff;
  color: #409eff;
}

.add-cart-button:hover {
  background: #409eff;
  color: white;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.button-icon {
  font-size: 20px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .item-variation {
    padding: 16px;
  }

  .variation-header {
    flex-direction: column;
    gap: 12px;
    margin-bottom: 16px;
  }

  .variation-price {
    align-self: flex-start;
  }

  .variation-actions {
    gap: 10px;
  }

  .buy-now-button,
  .add-cart-button {
    height: 44px;
    font-size: 15px;
  }
}
</style>