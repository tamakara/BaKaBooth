<template>
  <ItemInfoPageLayout>
    <!-- 商品图片 -->
    <template #item-image>
      <div class="item-image-wrapper">
        <ImageSlider v-if="!loading" :images="itemVO.imageUrls"/>
        <div v-else class="loading-placeholder">
          <el-skeleton animated>
            <template #template>
              <el-skeleton-item variant="image" style="width: 100%; height: 100%"/>
            </template>
          </el-skeleton>
        </div>
      </div>
    </template>

    <!-- 商品描述 -->
    <template #item-text>
      <div class="item-text">
        <el-skeleton v-if="loading" :rows="4" animated/>
        <span v-else>{{ itemVO.description }}</span>
      </div>
    </template>

    <!-- 商品基本信息 -->
    <template #item-header>
      <div class="item-header-container">
        <div class="item-title">
          <el-skeleton v-if="loading" animated>
            <template #template>
              <el-skeleton-item variant="h1" style="width: 60%"/>
            </template>
          </el-skeleton>
          <span v-else>{{ itemVO.name }}</span>
        </div>

        <div class="item-buttons">
          <div class="item-buttons-left">
            <el-button
                class="item-button-favorite"
                @click="handleFavoriteClick"
                :loading="favoriteLoading"
                :disabled="loading"
                size="large"
            >
              <el-icon size="20" style="margin-right: 6px">
                <HeartIcon :class="{ 'favorited': isFavorited }"/>
              </el-icon>
              <span class="favorite-text">收藏</span>
              <span class="favorite-count">{{ itemVO.favorites || 0 }}</span>
            </el-button>
          </div>
          <div class="item-buttons-right">
            <el-button
                class="item-button-action"
                @click="handleChatClick"
                :disabled="loading"
                size="large"
                type="primary"
                plain
            >
              <el-icon size="18">
                <ChatBubbleOvalLeftEllipsisIcon/>
              </el-icon>
            </el-button>

            <el-button
                class="item-button-action"
                @click="handleShareClick"
                :disabled="loading"
                size="large"
                type="info"
                plain
            >
              <el-icon size="18">
                <ShareIcon/>
              </el-icon>
            </el-button>

            <el-button
                class="item-button-action"
                @click="handleReportClick"
                :disabled="loading"
                size="large"
                type="warning"
                plain
            >
              <el-icon size="18">
                <ExclamationTriangleIcon/>
              </el-icon>
            </el-button>
          </div>
        </div>
      </div>
    </template>

    <!-- 商品规格和价格 -->
    <template #item-variations>
      <div v-if="loading">
        <el-skeleton v-for="i in 2" :key="i" animated>
          <template #template>
            <el-skeleton-item variant="text" style="width: 100%; margin-bottom: 20px"/>
          </template>
        </el-skeleton>
      </div>
      <div v-else>
        <div
            class="item-variations"
            v-for="(variation, index) in itemVO.variations"
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
                <span class="stock-count"
                      :class="{ 'low-stock': variation.stock < 10, 'out-of-stock': variation.stock === 0 }">
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
                    <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                         stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round"
                            d="M2.25 18.75a60.07 60.07 0 0 1 15.797 2.101c.727.198 1.453-.342 1.453-1.096V18.75M3.75 4.5v.75A.75.75 0 0 1 3 6h-.75m0 0v-.375c0-.621.504-1.125 1.125-1.125H3.75m0 0v-.375c0-.621.504-1.125 1.125-1.125m0 0h9.75m-9.75 0h9.75m0 0h-.375c.621 0 1.125.504 1.125 1.125v.375M3.75 3.75h.375c.621 0 1.125.504 1.125 1.125v.375m0 0h9.75v-.375c0-.621.504-1.125 1.125-1.125h.375M3.75 3.75v9.75M20.25 3.75v9.75M3.75 13.5h16.5"/>
                    </svg>
                  </el-icon>
                  {{ variation.stock === 0 ? '缺货' : '立即购买' }}
                </el-button>
              </div>
            </div>
          </div>
          <el-divider/>
        </div>
        <div v-if="!itemVO.variations?.length" class="no-variations">
          暂无商品规格信息
        </div>
      </div>
    </template>

    <!-- 商品服务信息 -->
    <template #item-terms>
      <div class="item-delivery">
        <div class="item-terms-title">
          <el-icon>
            <TruckIcon/>
          </el-icon>
          发货
        </div>
        <div class="item-terms-content">
          <div class="info-item">
            <span class="info-label">发货时间：</span>
            <span>3天内</span>
          </div>
          <div class="info-item">
            <span class="info-label">运费说明：</span>
            <span>包邮</span>
          </div>
        </div>
        <div class="item-terms-title">
          <el-icon>
            <ShieldCheckIcon/>
          </el-icon>
          售后
        </div>
        <div class="item-terms-content">
          <div class="info-item">
            <span class="info-label">退换政策：</span>
            <span>不支持退货</span>
          </div>
          <div class="info-item">
            <span class="info-label">质保期限：</span>
            <span>1周</span>
          </div>
        </div>
        <div class="item-terms-title">
          <el-icon>
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5"
                 stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round"
                    d="M9.568 3H5.25A2.25 2.25 0 0 0 3 5.25v4.318c0 .597.237 1.17.659 1.591l9.581 9.581c.699.699 1.78.872 2.607.33a18.095 18.095 0 0 0 5.223-5.223c.542-.827.369-1.908-.33-2.607L11.16 3.66A2.25 2.25 0 0 0 9.568 3Z"/>
              <path stroke-linecap="round" stroke-linejoin="round" d="M6 6h.008v.008H6V6Z"/>
            </svg>
          </el-icon>
          商品标签
        </div>
        <div class="item-terms-content">
          <div class="tags-container">
            <el-tag
                v-if="itemVO.tags?.length"
                v-for="tag in itemVO.tags"
                :key="tag"
                size="default"
                type="info"
                effect="light"
                class="tag-item"
            >
              {{ tag }}
            </el-tag>
            <span v-if="!itemVO.tags?.length" class="no-tags">暂无标签</span>
          </div>
        </div>
      </div>
    </template>

    <template #seller-info>
      <div class="seller-info-container">
        <div class="seller-content">
          <el-skeleton v-if="loading" animated>
            <template #template>
              <div class="seller-skeleton">
                <el-skeleton-item variant="circle" style="width: 80px; height: 80px"/>
                <div class="seller-skeleton-text">
                  <el-skeleton-item variant="text" style="width: 150px; height: 20px; margin-bottom: 12px;"/>
                  <el-skeleton-item variant="text" style="width: 250px; height: 16px; margin-bottom: 8px;"/>
                  <el-skeleton-item variant="text" style="width: 180px; height: 16px;"/>
                </div>
                <el-skeleton-item variant="button" style="width: 100px; height: 40px"/>
              </div>
            </template>
          </el-skeleton>

          <div v-else class="seller-card" @click="handleShopClick">
            <div class="seller-main">
              <div class="seller-avatar-section">
                <el-avatar :size="80" :src="sellerVO.avatarUrl" class="seller-avatar"/>
              </div>

              <div class="seller-info-main">
                <div class="seller-header-info">
                  <div class="seller-name-row">
                    <h3 class="seller-name">{{ sellerVO.username }}</h3>
                    <div class="seller-actions">
                      <el-button
                          @click.stop="handleStarClick"
                          :type="isFollowed ? 'primary' : 'default'"
                          size="small"
                          class="follow-btn"
                      >
                        <el-icon style="margin-right: 4px;">
                          <StarIcon :class="{ 'starred': isFollowed }"/>
                        </el-icon>
                        {{ isFollowed ? '已关注' : '关注' }}
                      </el-button>
                      <el-button
                          @click.stop="handleChatClick"
                          type="primary"
                          size="small"
                          plain
                          class="contact-btn"
                      >
                        <el-icon style="margin-right: 4px;">
                          <ChatBubbleOvalLeftEllipsisIcon/>
                        </el-icon>
                        联系卖家
                      </el-button>
                    </div>
                  </div>
                  <div class="seller-meta">
                    <div class="meta-item">
                      <el-icon size="14" color="#666">
                        <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
                          <path fill="currentColor"
                                d="M908.1 353.1l-253.9-36.9L540.7 86.1c-3.1-6.3-8.2-11.4-14.5-14.5-15.8-7.8-35-1.3-42.9 14.5L369.8 316.2l-253.9 36.9c-7 1-13.4 4.3-18.3 9.3a32.05 32.05 0 0 0 .6 45.3l183.7 179.1-43.4 252.9a31.95 31.95 0 0 0 46.4 33.7L512 754l227.1 119.4c6.2 3.3 13.4 4.4 20.3 3.2 17.4-3 29.1-19.5 26.1-36.9l-43.4-252.9 183.7-179.1c5-4.9 8.3-11.3 9.3-18.3 2.7-17.5-9.5-33.7-27-36.3z"/>
                        </svg>
                      </el-icon>
                      <span>关注者: 100</span>
                    </div>
                    <div class="meta-item">
                      <el-icon size="14" color="#666">
                        <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
                          <path fill="currentColor"
                                d="M908.1 353.1l-253.9-36.9L540.7 86.1c-3.1-6.3-8.2-11.4-14.5-14.5-15.8-7.8-35-1.3-42.9 14.5L369.8 316.2l-253.9 36.9c-7 1-13.4 4.3-18.3 9.3a32.05 32.05 0 0 0 .6 45.3l183.7 179.1-43.4 252.9a31.95 31.95 0 0 0 46.4 33.7L512 754l227.1 119.4c6.2 3.3 13.4 4.4 20.3 3.2 17.4-3 29.1-19.5 26.1-36.9l-43.4-252.9 183.7-179.1c5-4.9 8.3-11.3 9.3-18.3 2.7-17.5-9.5-33.7-27-36.3z"/>
                        </svg>
                      </el-icon>
                      <span>评分: 4.9/5.0</span>
                    </div>
                    <div class="meta-item">
                      <el-icon size="14" color="#666">
                        <svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
                          <path fill="currentColor"
                                d="M512 85.333333C276.053333 85.333333 85.333333 276.053333 85.333333 512s190.72 426.666667 426.666667 426.666667 426.666667-190.72 426.666667-426.666667S747.946667 85.333333 512 85.333333z m0 768c-188.416 0-341.333333-152.917333-341.333333-341.333333S323.584 170.666667 512 170.666667s341.333333 152.917333 341.333333 341.333333S700.416 853.333333 512 853.333333z"/>
                          <path fill="currentColor"
                                d="M512 298.666667c-23.552 0-42.666667 19.114667-42.666667 42.666666v170.666667c0 11.306667 4.48 22.186667 12.458667 30.165333l128 128c16.682667 16.682667 43.648 16.682667 60.330667 0s16.682667-43.648 0-60.330666L554.666667 494.378667V341.333333c0-23.552-19.114667-42.666667-42.666667-42.666666z"/>
                        </svg>
                      </el-icon>
                      <span>最近活跃: 2小时前</span>
                    </div>
                  </div>
                </div>

                <!--TODO-->
                <!--此处是商品预览-->

              </div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </ItemInfoPageLayout>
</template>

<script setup lang="ts">
import ItemInfoPageLayout from './ItemInfoPageLayout.vue';
import {useRoute, useRouter} from 'vue-router';
import ImageSlider from "@/components/common/ImageSlider.vue";
import {
  StarIcon,
  HeartIcon,
  ShareIcon,
  ExclamationTriangleIcon,
  ChatBubbleOvalLeftEllipsisIcon,
  TruckIcon,
  ShieldCheckIcon
} from '@heroicons/vue/24/outline'
import {onMounted, ref, computed} from "vue";
import {ElMessage, ElMessageBox} from 'element-plus';
import {getItemVO} from "@/api/item.ts";
import {getSellerUserVO} from "@/api/user.ts";
import type {ItemVO} from "@/types/ItemTypes";
import type {SellerVO} from "@/types/UserTypes";

const route = useRoute();
const router = useRouter();

// 响应式数据
const itemVO = ref<ItemVO>({} as ItemVO);
const sellerVO = ref<SellerVO>({} as SellerVO);
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

    // 并行获取商品和卖家信息
    const [itemResponse, sellerResponse] = await Promise.all([
      getItemVO(itemId.value),
      getItemVO(itemId.value).then(item => getSellerUserVO(item.userId?.toString() || ''))
    ]);

    itemVO.value = itemResponse;
    sellerVO.value = sellerResponse;

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
  router.push(`/shop/${sellerVO.value.username}`);
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
    const currentFavorites = parseInt(itemVO.value.favorites) || 0;
    itemVO.value.favorites = (currentFavorites + (isFavorited.value ? 1 : -1)).toString();

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
        title: itemVO.value.name,
        text: itemVO.value.description,
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
