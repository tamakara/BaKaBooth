<template>
  <ItemInfoPageLayout>
    <template #item-image>
      <div class="item-image-wrapper">
        <ImageSlider v-if="!loading" :images="itemData.imageUrls" />
        <div v-else class="loading-placeholder">
          <el-skeleton animated>
            <template #template>
              <el-skeleton-item variant="image" style="width: 100%; height: 100%" />
            </template>
          </el-skeleton>
        </div>
      </div>
    </template>

    <template #item-text>
      <div class="item-text">
        <el-skeleton v-if="loading" :rows="4" animated />
        <span v-else>{{ itemData.description || '暂无商品描述' }}</span>
      </div>
    </template>

    <template #item-header>
      <div class="shop-avatar-name" @click="handleShopClick" :class="{ 'pointer': !loading }">
        <el-skeleton v-if="loading" animated>
          <template #template>
            <el-skeleton-item variant="circle" style="width: 24px; height: 24px" />
            <el-skeleton-item variant="text" style="width: 100px" />
          </template>
        </el-skeleton>
        <template v-else>
          <el-avatar :size="24" :src="sellerUserData.avatar">
            {{ sellerUserData.username?.charAt(0)?.toUpperCase() || 'U' }}
          </el-avatar>
          <div class="shop-name">{{ sellerUserData.username || '未知店铺' }}</div>
          <el-button
            text
            @click.stop="handleStarClick"
            style="width: 20px"
            :aria-label="isFollowed ? '取消关注' : '关注店铺'"
          >
            <el-icon :size="20">
              <StarIcon :class="{ 'starred': isFollowed }" />
            </el-icon>
          </el-button>
        </template>
      </div>

      <div class="item-title">
        <el-skeleton v-if="loading" animated>
          <template #template>
            <el-skeleton-item variant="h1" style="width: 60%" />
          </template>
        </el-skeleton>
        <span v-else>{{ itemData.name || '商品名称' }}</span>
      </div>

      <div class="item-buttons">
        <div class="item-buttons-left">
          <el-button
            class="item-button-favorite"
            @click="handleFavoriteClick"
            :loading="favoriteLoading"
            :disabled="loading"
          >
            <el-icon size="24" style="margin-right: 3px">
              <HeartIcon :class="{ 'favorited': isFavorited }" />
            </el-icon>
            {{ itemData.favorites || 0 }}
          </el-button>
        </div>
        <div class="item-buttons-right">
          <el-button
            class="item-button-chat"
            text
            @click="handleChatClick"
            :disabled="loading"
            aria-label="联系卖家"
          >
            <el-icon size="24">
              <ChatBubbleOvalLeftEllipsisIcon />
            </el-icon>
          </el-button>

          <el-button
            class="item-button-share"
            text
            @click="handleShareClick"
            :disabled="loading"
            aria-label="分享商品"
          >
            <el-icon size="24">
              <ShareIcon />
            </el-icon>
          </el-button>

          <el-button
            class="item-button-report"
            text
            @click="handleReportClick"
            :disabled="loading"
            aria-label="举报商品"
          >
            <el-icon size="24">
              <ExclamationTriangleIcon />
            </el-icon>
          </el-button>
        </div>
      </div>
    </template>

    <template #item-variations>

      <div v-if="loading">
        <el-skeleton v-for="i in 2" :key="i" animated>
          <template #template>
            <el-skeleton-item variant="text" style="width: 100%; margin-bottom: 20px" />
          </template>
        </el-skeleton>
      </div>
      <div v-else>
        <div
          class="item-variations"
          v-for="(variation, index) in itemData.variations"
          :key="index"
        >
          <div class="variation-container">
            <div class="variation-header">
              <div class="variation-info">
                <h4 class="variation-name">{{ variation.name }}</h4>
              </div>
              <div class="variation-price">
                <span class="price-currency">¥</span>
                <span class="price-amount">{{ formatPrice(variation.price) }}</span>
              </div>
            </div>

            <div class="variation-details">
              <div class="variation-stock">
                <span class="stock-label">库存：</span>
                <span class="stock-count" :class="{ 'low-stock': variation.stock < 10, 'out-of-stock': variation.stock === 0 }">
                  {{ variation.stock > 0 ? `${variation.stock}件` : '缺货' }}
                </span>
              </div>

              <div class="variation-actions">
                <el-button
                  type="danger"
                  size="default"
                  class="buy-now-button"
                  @click="handleBuyNow(variation)"
                  :disabled="variation.stock === 0"
                >
                  <el-icon class="button-icon">
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" d="M2.25 18.75a60.07 60.07 0 0 1 15.797 2.101c.727.198 1.453-.342 1.453-1.096V18.75M3.75 4.5v.75A.75.75 0 0 1 3 6h-.75m0 0v-.375c0-.621.504-1.125 1.125-1.125H3.75m0 0v-.375c0-.621.504-1.125 1.125-1.125m0 0h9.75m-9.75 0h9.75m0 0h-.375c.621 0 1.125.504 1.125 1.125v.375M3.75 3.75h.375c.621 0 1.125.504 1.125 1.125v.375m0 0h9.75v-.375c0-.621.504-1.125 1.125-1.125h.375M3.75 3.75v9.75M20.25 3.75v9.75M3.75 13.5h16.5" />
                    </svg>
                  </el-icon>
                  {{ variation.stock === 0 ? '缺货' : '立即购买' }}
                </el-button>
              </div>
            </div>
          </div>
          <el-divider />
        </div>
        <div v-if="!itemData.variations?.length" class="no-variations">
          暂无商品规格信息
        </div>
      </div>
    </template>

    <template #item-terms>
      <div class="item-info">
        <div class="item-terms-title">
          <el-icon>
            <InformationCircleIcon />
          </el-icon>
          商品信息
        </div>
        <div class="item-terms-content">
          <div class="info-item">
            <span class="info-label">商品状态：</span>
            <span>在售</span>
          </div>
          <div class="info-item">
            <span class="info-label">商品编号：</span>
            <span>{{ itemId }}</span>
          </div>
        </div>
      </div>

      <div class="item-delivery">
        <div class="item-terms-title">
          <el-icon>
            <TruckIcon />
          </el-icon>
          发货方式
        </div>
        <div class="item-terms-content">
          <div class="info-item">
            <span class="info-label">发货类型：</span>
            <span>人工发货</span>
          </div>
          <div class="info-item">
            <span class="info-label">发货时间：</span>
            <span>1-3个工作日</span>
          </div>
          <div class="info-item">
            <span class="info-label">运费说明：</span>
            <span>请联系卖家确认</span>
          </div>
        </div>
      </div>

      <div class="item-service">
        <div class="item-terms-title">
          <el-icon>
            <ShieldCheckIcon />
          </el-icon>
          售后服务
        </div>
        <div class="item-terms-content">
          <div class="info-item">
            <span class="info-label">退换政策：</span>
            <span>请联系卖家确认</span>
          </div>
          <div class="info-item">
            <span class="info-label">质保期限：</span>
            <span>请联系卖家确认</span>
          </div>
          <div class="info-item">
            <span class="info-label">客服支持：</span>
            <span>在线客服 9:00-18:00</span>
          </div>
        </div>
      </div>

      <!-- 商品标签移动到底部 -->
      <div class="item-tags">
        <div class="item-terms-title">
          <el-icon>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" d="M9.568 3H5.25A2.25 2.25 0 0 0 3 5.25v4.318c0 .597.237 1.17.659 1.591l9.581 9.581c.699.699 1.78.872 2.607.33a18.095 18.095 0 0 0 5.223-5.223c.542-.827.369-1.908-.33-2.607L11.16 3.66A2.25 2.25 0 0 0 9.568 3Z" />
              <path stroke-linecap="round" stroke-linejoin="round" d="M6 6h.008v.008H6V6Z" />
            </svg>
          </el-icon>
          商品标签
        </div>
        <div class="item-terms-content">
          <div class="tags-container">
            <el-tag
              v-for="tag in itemData.tags"
              :key="tag"
              size="default"
              type="info"
              effect="light"
              class="tag-item"
              v-if="itemData.tags?.length"
            >
              {{ tag }}
            </el-tag>
            <span v-if="!itemData.tags?.length" class="no-tags">暂无标签</span>
          </div>
        </div>
      </div>
    </template>
  </ItemInfoPageLayout>
</template>

<script setup lang="ts">
import ItemInfoPageLayout from './ItemInfoPageLayout.vue';
import { useRoute, useRouter } from 'vue-router';
import ImageSlider from "@/components/common/ImageSlider.vue";
import ItemVariation from "@/components/common/ItemVariation.vue";
import {
  StarIcon,
  HeartIcon,
  ShareIcon,
  ExclamationTriangleIcon,
  ChatBubbleOvalLeftEllipsisIcon,
  InformationCircleIcon,
  TruckIcon,
  ShieldCheckIcon
} from '@heroicons/vue/24/outline'
import { onMounted, ref, computed } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus';
import { getItemVO } from "@/api/item.ts";
import { getSellerUserVO } from "@/api/user.ts";
import type { ItemVO } from "@/types/ItemTypes";
import type { SellerVO } from "@/types/UserTypes";

const route = useRoute();
const router = useRouter();

// 响应式数据
const itemData = ref<ItemVO>({} as ItemVO);
const sellerUserData = ref<SellerVO>({} as SellerVO);
const loading = ref(true);
const favoriteLoading = ref(false);
const isFavorited = ref(false);
const isFollowed = ref(false);

// 计算属性
const itemId = computed(() => route.params.id as string);

// 数据获取
const fetchData = async () => {
  try {
    loading.value = true;

    if (!itemId.value) {
      throw new Error('商品ID不存在');
    }

    // 并行获取商品和卖家信息
    const [itemResponse, sellerResponse] = await Promise.all([
      getItemVO(itemId.value),
      getItemVO(itemId.value).then(item => getSellerUserVO(item.userId?.toString() || ''))
    ]);

    itemData.value = itemResponse;
    sellerUserData.value = sellerResponse;

    // TODO: 检查用户是否收藏/关注
    // isFavorited.value = await checkIfFavorited(itemId.value);
    // isFollowed.value = await checkIfFollowed(sellerUserData.value.id);

  } catch (error) {
    console.error('获取数据失败:', error);
    ElMessage.error('获取商品信息失败，请稍后��试');
    // 可选择跳转到错误页面或返回上一页
    // router.back();
  } finally {
    loading.value = false;
  }
};

// 事件处理函数
const handleShopClick = () => {
  if (loading.value) return;
  router.push(`/shop/${sellerUserData.value.id}`);
};

const handleStarClick = async () => {
  try {
    // TODO: 实现关注/取消关注逻辑
    isFollowed.value = !isFollowed.value;
    ElMessage.success(isFollowed.value ? '关注成功' : '已取消关注');
  } catch (error) {
    ElMessage.error('操作失败，请稍后重试');
  }
};

const handleFavoriteClick = async () => {
  try {
    favoriteLoading.value = true;
    // TODO: 实现收藏/取消收藏逻辑
    isFavorited.value = !isFavorited.value;

    // ��新收藏数量
    const currentFavorites = parseInt(itemData.value.favorites) || 0;
    itemData.value.favorites = (currentFavorites + (isFavorited.value ? 1 : -1)).toString();

    ElMessage.success(isFavorited.value ? '收藏成功' : '已取消收藏');
  } catch (error) {
    ElMessage.error('操作失败，请稍后重试');
  } finally {
    favoriteLoading.value = false;
  }
};

const handleChatClick = () => {
  if (loading.value) return;
  // TODO: 实现聊天功能
  ElMessage.info('聊天功能开发中');
};

const handleShareClick = async () => {
  if (loading.value) return;

  try {
    if (navigator.share) {
      await navigator.share({
        title: itemData.value.name,
        text: itemData.value.description,
        url: window.location.href
      });
    } else {
      // 复制链接到剪贴板
      await navigator.clipboard.writeText(window.location.href);
      ElMessage.success('链接已复制到剪贴板');
    }
  } catch (error) {
    ElMessage.error('分享失败');
  }
};

const handleReportClick = async () => {
  if (loading.value) return;

  try {
    await ElMessageBox.confirm(
      '您确定要举报这个商品吗？我们会认真处理您的举报。',
      '举报商品',
      {
        confirmButtonText: '确定举报',
        cancelButtonText: '取���',
        type: 'warning',
      }
    );

    // TODO: 实现举报功能
    ElMessage.success('举报已提交，我们会尽快处理');
  } catch {
    // 用户取消举报
  }
};

const handleBuyNow = (variation: any) => {
  if (loading.value || variation.stock === 0) return;
  // TODO: 实现立即购买逻辑
  ElMessage.info(`准备购买：${variation.name} - ${variation.type}`);
  // router.push(`/checkout?itemId=${itemId.value}&variationName=${variation.name}`);
};

// 工具函数
const formatPrice = (price: number | string) => {
  const numPrice = typeof price === 'string' ? parseFloat(price) : price;
  return numPrice.toFixed(2);
};

// 生命周期
onMounted(() => {
  fetchData();
});
</script>

<style scoped>
.item-image-wrapper {
  width: 550px;
  height: 550px;
  background-color: #ebedf2;
  flex-shrink: 0; /* 防止收缩 */
}

.item-text {
  font-size: 16px;
  white-space: pre-wrap;
  word-wrap: break-word;
  word-break: break-word; /* 强制换行 */
  overflow-wrap: break-word; /* 现代浏览器支持 */
  max-width: 100%; /* 限制最大宽度 */
  box-sizing: border-box; /* 包含padding和border */
  flex: 1; /* 撑满剩余空间 */
  display: flex;
  flex-direction: column;
  min-height: 0; /* 允许内容收缩 */
}

.shop-avatar-name {
  height: 30px;
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  max-width: 100%; /* 限制最大宽度 */
  box-sizing: border-box;
}

.shop-name {
  flex: 1;
  font-size: 16px;
  font-weight: 300;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis; /* 添加省略号 */
  white-space: nowrap; /* 确保单行显示 */
  min-width: 0; /* 允许收缩 */
}

.item-title {
  font-size: 24px;
  font-weight: 700;
  word-wrap: break-word;
  word-break: break-word;
  overflow-wrap: break-word;
  max-width: 100%;
  box-sizing: border-box;
  margin: 20px 0; /* 增大上下间距 */
  padding: 12px 0; /* 添加内边距 */
}

.item-buttons {
  display: flex;
  justify-content: space-between;
  max-width: 100%;
  box-sizing: border-box;
}

.item-buttons-left {
  flex-shrink: 0; /* 防止收缩 */
}

.item-buttons-right {
  display: flex;
  flex-shrink: 0; /* 防止收缩 */
}

.item-button-favorite {
  width: 80px;
  height: 50px;
  border: 2px solid #e4e7ed;
  border-radius: 8px;
  display: flex;
  font-size: 18px;
  flex-shrink: 0; /* 防止收缩 */
}

.item-button-chat,
.item-button-share,
.item-button-report {
  width: 50px;
  height: 50px;
  flex-shrink: 0; /* 防止收缩 */
}

.item-terms-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 5px;
  word-wrap: break-word;
  word-break: break-word;
  overflow-wrap: break-word;
  max-width: 100%;
  box-sizing: border-box;
}

.item-terms-content {
  font-size: 14px;
  font-weight: 320;
  line-height: 1.6;
  word-wrap: break-word;
  word-break: break-word;
  overflow-wrap: break-word;
  max-width: 100%;
  box-sizing: border-box;
}

.loading-placeholder {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0; /* 防止收缩 */
}

.pointer {
  cursor: pointer;
}

.starred {
  color: #f39c12;
}

.favorited {
  color: #e74c3c;
}

.no-variations {
  text-align: center;
  color: #909399;
  padding: 20px;
  font-size: 14px;
  word-wrap: break-word;
  word-break: break-word;
  overflow-wrap: break-word;
  max-width: 100%;
  box-sizing: border-box;
}

.info-item {
  margin-bottom: 8px;
  display: flex;
  align-items: flex-start; /* 改为顶部对齐，处理多行文本 */
  max-width: 100%;
  box-sizing: border-box;
}

.info-label {
  font-weight: 500;
  color: #606266;
  min-width: 80px;
  flex-shrink: 0; /* 防止标��收缩 */
  margin-right: 8px; /* 添加右边距 */
}

/* 添加信息内容样式 */
.info-item > span:last-child,
.info-item > div:last-child {
  word-wrap: break-word;
  word-break: break-word;
  overflow-wrap: break-word;
  flex: 1; /* 占据剩余空间 */
  min-width: 0; /* 允许收缩 */
  max-width: calc(100% - 88px); /* 减去标签宽度和边距 */
}

/* 标签容器样式 */
.info-item .el-tag {
  margin-right: 5px;
  margin-bottom: 2px; /* 多行时的间距 */
  word-break: break-all; /* 标签内容也要换行 */
}

/* 确���变体信息也不会溢出 */
.item-variations {
  max-width: 100%;
  box-sizing: border-box;
  overflow: hidden; /* 防止溢出 */
}

/* 变体信息样式 */
.variation-container {
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 12px;
  margin-bottom: 16px;
  border: 1px solid #e4e7ed;
}

.variation-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 12px;
}

.variation-info {
  flex: 1;
}

.variation-name {
  font-size: 18px;
  font-weight: 500;
  margin: 0 0 4px 0;
  color: #2c3e50;
}

.variation-type {
  font-size: 14px;
  color: #7f8c8d;
  background-color: #ecf0f1;
  padding: 2px 8px;
  border-radius: 12px;
  display: inline-block;
}

.variation-price {
  font-size: 18px;
  font-weight: 700;
  color: #e74c3c;
  text-align: right;
}

.price-currency {
  font-size: 14px;
  vertical-align: super;
}

.price-amount {
  font-size: 24px;
}

.variation-details {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 16px;
}

.variation-stock {
  flex: 1;
}

.stock-label {
  font-size: 14px;
  color: #666;
  margin-right: 4px;
}

.stock-count {
  font-size: 16px;
  font-weight: 500;
}

.low-stock {
  color: #e67e22;
}

.out-of-stock {
  color: #e74c3c;
}

.variation-actions {
  flex-shrink: 0;
}

.buy-now-button {
  width: 120px; /* 缩小按钮宽度 */
  height: 36px; /* 缩小按钮高度 */
  font-size: 14px; /* 缩小字体 */
  font-weight: 500;
  border-radius: 6px;
  padding: 0 12px;
}

/* 按钮图标样式 */
.button-icon {
  margin-right: 4px; /* 减小图标间距 */
  vertical-align: middle;
  width: 16px; /* 设置图标大小 */
  height: 16px;
}

/* 商品标签样式 */
.item-tags {
  margin-top: 24px;
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.tag-item {
  padding: 8px 12px;
  border-radius: 16px;
  font-size: 14px;
  font-weight: 500;
}

.no-tags {
  font-size: 14px;
  color: #909399;
  padding: 8px 0;
}
</style>
