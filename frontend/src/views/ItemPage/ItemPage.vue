<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="item-page">
        <div class="container" v-loading.fullscreen.lock="pageLoading">
          <!-- 面包屑 / 返回 -->
          <div class="breadcrumb-bar">
            <el-button text type="primary" @click="goBack" class="back-btn">
              <el-icon style="margin-right: 6px;"><ArrowLeftIcon/></el-icon>
              返回
            </el-button>
          </div>

          <div class="content-layout">
            <!-- 左侧：图片 + 描述 -->
            <div class="main-content">
              <div class="media-container">
                <div v-if="loading" class="media-skeleton">
                  <el-skeleton animated>
                    <template #template>
                      <el-skeleton-item variant="image" style="width:100%;aspect-ratio:1/1;border-radius:20px"/>
                    </template>
                  </el-skeleton>
                </div>
                <div v-else class="media-wrapper">
                  <ImageSlider :images="itemVO.images || []"/>
                </div>
              </div>

              <div class="description-card">
                <div class="card-header">
                  <h3 class="card-title">商品描述</h3>
                </div>
                <div class="description-content" v-if="!loading">
                  {{ itemVO.description || '暂无描述' }}
                </div>
                <el-skeleton v-else :rows="4" animated/>
              </div>
            </div>

            <!-- 右侧：信息栏 -->
            <aside class="sidebar">
              <!-- 卖家信息卡片 -->
              <div class="seller-card">
                <div v-if="!loading" class="seller-content">
                  <div class="seller-header">
                    <el-avatar :size="56" :src="sellerVO.avatarUrl" class="seller-avatar"/>
                    <div class="seller-info">
                      <div class="seller-name-row">
                        <span class="seller-name" @click="goSeller">{{ sellerVO.username }}</span>
                        <el-tag v-if="sellerVO.isCurrentUser" size="small" type="success">我</el-tag>
                      </div>
                      <div class="seller-meta">
                        <el-icon class="meta-icon"><UserGroupIcon/></el-icon>
                        <span>{{ sellerVO.followers || 0 }} 关注者</span>
                      </div>
                    </div>
                  </div>
                  <div class="seller-actions">
                    <el-button
                      size="small"
                      :type="isFollowed ? 'primary':'default'"
                      @click="toggleFollow"
                      class="action-btn"
                    >
                      <el-icon><component :is="isFollowed ? CheckCircleIcon : PlusCircleIcon"/></el-icon>
                      {{ isFollowed ? '已关注':'关注' }}
                    </el-button>
                    <el-button size="small" type="primary" plain @click="chatSeller" class="action-btn">
                      <el-icon><ChatBubbleLeftRightIcon/></el-icon>
                      联系
                    </el-button>
                  </div>
                </div>
                <div v-else class="seller-skeleton">
                  <el-skeleton animated :rows="3"/>
                </div>
              </div>

              <!-- 商品信息卡片 -->
              <div class="product-card">
                <div class="product-header">
                  <h1 class="product-title" v-if="!loading">{{ itemVO.name }}</h1>
                  <el-skeleton v-else animated :rows="1"/>
                </div>

                <div class="price-section" v-if="!loading">
                  <div class="price-main">
                    <span class="currency">¥</span>
                    <span class="price-amount">{{ formatPrice(itemVO.price) }}</span>
                  </div>
                  <div class="price-meta">
                    <span class="shipping-info" v-if="itemVO.deliveryMethodCode !== undefined">
                      {{ shippingInline }}
                    </span>
                    <el-tag
                      v-if="itemVO.stateCode === 0 || itemVO.stateCode === 3"
                      size="small"
                      type="warning"
                    >
                      已下架
                    </el-tag>
                  </div>
                </div>
                <div v-else class="price-skeleton">
                  <el-skeleton animated :rows="2"/>
                </div>

                <div class="meta-section" v-if="!loading">
                  <div class="meta-item">
                    <el-icon class="meta-icon"><HeartIcon/></el-icon>
                    <span>{{ favoriteCount }} 人收藏</span>
                  </div>
                  <div class="meta-item">
                    <el-icon class="meta-icon"><ClockIcon/></el-icon>
                    <span>发布于 {{ formatTime(itemVO.createdAt) }}</span>
                  </div>
                </div>
                <el-skeleton v-else animated :rows="1"/>

                <!-- 操作按钮 -->
                <div v-if="!loading" class="action-section">
                  <el-button
                    type="primary"
                    size="large"
                    class="buy-button"
                    :disabled="!isOnSale"
                    @click="handleBuy"
                  >
                    <el-icon style="margin-right: 8px;"><ShoppingCartIcon/></el-icon>
                    立即购买
                  </el-button>

                  <div class="secondary-actions">
                    <el-button
                      :type="isFavorited ? 'danger':'default'"
                      :loading="favoriteLoading"
                      class="secondary-btn"
                      @click="toggleFavorite"
                    >
                      <el-icon><HeartIcon :class="{ 'favorited': isFavorited }"/></el-icon>
                      {{ isFavorited ? '已收藏':'收藏' }}
                    </el-button>
                    <el-button class="secondary-btn" @click="shareItem">
                      <el-icon><ShareIcon/></el-icon>
                      分享
                    </el-button>
                    <el-button class="secondary-btn report-btn" @click="reportItem">
                      <el-icon><FlagIcon/></el-icon>
                      举报
                    </el-button>
                  </div>
                </div>
                <div v-else class="action-skeleton">
                  <el-skeleton animated :rows="2"/>
                </div>
              </div>
            </aside>
          </div>
        </div>
      </div>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import {ref, onMounted, watch, computed} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import {ElMessage, ElMessageBox} from 'element-plus'
import {getUserVO, favorite, unfavorite, getFavoriteCount, isFavorite} from '@/services/user.ts'
import {getItemVO} from '@/services/item.ts'
import {createOrder} from '@/services/order.ts'
import type {ItemVO} from '@/types/item.d.ts'
import type {UserVO} from '@/types/user.d.ts'
import ImageSlider from '@/components/business/ImageSlider.vue'
import BaseLayout from '@/components/layout/BaseLayout.vue'
import Banner from '@/components/business/Banner.vue'
import {HeartIcon,  ShareIcon, FlagIcon, UserGroupIcon, PlusCircleIcon, ChatBubbleLeftRightIcon, CheckCircleIcon, ArrowLeftIcon, ClockIcon, ShoppingCartIcon} from '@heroicons/vue/24/outline'

const route = useRoute()
const router = useRouter()

const itemVO = ref<ItemVO>({} as ItemVO)
const sellerVO = ref<UserVO>({} as UserVO)
const loading = ref(true)
const pageLoading = ref(false)
const favoriteLoading = ref(false)
const isFavorited = ref(false)
const isFollowed = ref(false)
const favoriteCount = ref(0)

const itemId = computed(() => route.params.id as string)
const isOnSale = computed(()=> itemVO.value.stateCode === 2)

async function fetchData() {
  try {
    loading.value = true
    const vo = await getItemVO(itemId.value)
    // 兜底处理防止 null 进入渲染
    if (vo && (vo as any).price == null) (vo as any).price = 0
    if (vo && (vo as any).postage == null) (vo as any).postage = 0
    itemVO.value = vo
    sellerVO.value = await getUserVO(vo.userId.toString())
    isFavorited.value = await isFavorite(Number(itemId.value))
    favoriteCount.value = await getFavoriteCount(Number(itemId.value))
  } catch (e) {
    ElMessage.error('加载商品失败')
  } finally {
    loading.value = false
  }
}

function formatPrice(p: number | string | null | undefined) {
  if (p === null || p === undefined) return '0.00'
  const n = typeof p === 'string' ? parseFloat(p) : p
  if (Number.isNaN(n)) return '0.00'
  return Number(n).toFixed(2)
}

function formatTime(t?: string) {
  if (!t) return '——'
  const d = new Date(t)
  const now = Date.now()
  const diff = now - d.getTime()
  const mins = Math.floor(diff / 60000)
  if (mins < 60) return `${mins}分钟前`
  const hours = Math.floor(mins / 60)
  if (hours < 24) return `${hours}小时前`
  const days = Math.floor(hours / 24)
  if (days < 7) return `${days}天前`
  return d.toLocaleDateString()
}

async function toggleFavorite() {
  if (favoriteLoading.value || loading.value) return
  try {
    favoriteLoading.value = true
    if (isFavorited.value) {
      await unfavorite(Number(itemId.value))
      isFavorited.value = false
    } else {
      await favorite(Number(itemId.value))
      isFavorited.value = true
    }
    favoriteCount.value = await getFavoriteCount(Number(itemId.value))
  } catch {
    ElMessage.error('操作失败')
  } finally {
    favoriteLoading.value = false
  }
}

async function handleBuy() {
  if (loading.value) return
  try {
    pageLoading.value = true
    const orderId = await createOrder(Number(itemId.value))
    await router.push({name: 'order-edit', params: {id: orderId}})
  } catch {
    ElMessage.error('创建订单失败')
  } finally {
    pageLoading.value = false
  }
}

async function shareItem() {
  if (loading.value) return
  try {
    if (navigator.share) {
      await navigator.share({title: itemVO.value.name, text: itemVO.value.description, url: location.href})
    } else {
      await navigator.clipboard.writeText(location.href)
      ElMessage.success('链接已复制')
    }
  } catch {
    ElMessage.error('分享失败')
  }
}

function reportItem() {
  if (loading.value) return
  ElMessageBox.confirm('确认举报该商品？', '举报', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    ElMessage.success('已提交举报')
  }).catch(() => {})
}

function toggleFollow() {
  isFollowed.value = !isFollowed.value
  ElMessage.success(isFollowed.value ? '已关注' : '已取消关注')
}

function chatSeller() {
  ElMessage.info('聊天功能开发中')
}

function goSeller() {
  if (!sellerVO.value.id) return
  router.push(`/user/${sellerVO.value.id}`)
}

function goBack() {
  router.back()
}

const shippingInline = computed(() => {
  const code = itemVO.value.deliveryMethodCode
  switch (code) {
    case 0: return '（自取）'
    case 1: return '（包邮）'
    case 2: return `（邮费 ¥${formatPrice(itemVO.value.postage)}）`
    default: return ''
  }
})

watch(itemId, fetchData)
onMounted(fetchData)
</script>

<style scoped>
.item-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #f8fafc 0%, #f1f5f9 100%);
  padding: 24px 0 80px;
}

.container {
  max-width: 880px;
  margin: 0 auto;
  padding: 0 20px;
}

.breadcrumb-bar {
  margin-bottom: 24px;
}

.back-btn {
  display: inline-flex;
  align-items: center;
  font-weight: 500;
  transition: all 0.2s ease;
}

.back-btn:hover {
  transform: translateX(-2px);
}

.content-layout {
  display: grid;
  grid-template-columns: 1fr 320px;
  gap: 32px;
  align-items: start;
}

.main-content {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.media-container {
  background: white;
  border-radius: 20px;
  padding: 0;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  transition: box-shadow 0.3s ease;
}

.media-container:hover {
  box-shadow: 0 12px 48px rgba(0, 0, 0, 0.12);
}

.media-wrapper {

}

.media-wrapper :deep(.image-slider-root),
.media-wrapper :deep(.slider-wrapper),
.media-wrapper :deep(.slider-main) {
  width: 100%;
  height: 100%;
  border-radius: 20px;
}

.media-wrapper :deep(img) {
  object-fit: cover;
  width: 100%;
  height: 100%;
}

.media-skeleton {
  aspect-ratio: 1/1;
  padding: 20px;
}

.description-card {
  background: white;
  border-radius: 16px;
  padding: 24px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  border: 1px solid rgba(226, 232, 240, 0.8);
}

.card-header {
  margin-bottom: 16px;
}

.card-title {
  font-size: 18px;
  font-weight: 600;
  color: #1e293b;
  margin: 0;
}

.description-content {
  font-size: 15px;
  line-height: 1.7;
  color: #475569;
  white-space: pre-wrap;
  word-break: break-word;
}

.sidebar {
  display: flex;
  flex-direction: column;
  gap: 20px;
  position: sticky;
  top: 90px;
}

.seller-card,
.product-card {
  background: white;
  border-radius: 16px;
  padding: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
  border: 1px solid rgba(226, 232, 240, 0.8);
  transition: box-shadow 0.2s ease;
}

.seller-card:hover,
.product-card:hover {
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
}

.seller-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.seller-header {
  display: flex;
  align-items: center;
  gap: 12px;
}

.seller-avatar {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.seller-info {
  flex: 1;
}

.seller-name-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 4px;
}

.seller-name {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  cursor: pointer;
  transition: color 0.2s ease;
}

.seller-name:hover {
  color: #3b82f6;
}

.seller-meta {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: #64748b;
}

.meta-icon {
  font-size: 14px;
  color: #94a3b8;
}

.seller-actions {
  display: flex;
  gap: 8px;
}

.action-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 6px;
  font-weight: 500;
}

.product-header {
  margin-bottom: 20px;
}

.product-title {
  font-size: 20px;
  font-weight: 700;
  color: #1e293b;
  line-height: 1.3;
  margin: 0;
}

.price-section {
  margin-bottom: 20px;
}

.price-main {
  display: flex;
  align-items: baseline;
  gap: 4px;
  margin-bottom: 8px;
}

.currency {
  font-size: 18px;
  font-weight: 600;
  color: #64748b;
}

.price-amount {
  font-size: 32px;
  font-weight: 700;
  background: linear-gradient(135deg, #3b82f6, #8b5cf6);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
}

.price-meta {
  display: flex;
  align-items: center;
  gap: 12px;
}

.shipping-info {
  font-size: 13px;
  color: #64748b;
  background: #f1f5f9;
  padding: 4px 8px;
  border-radius: 6px;
}

.meta-section {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 24px;
  padding: 16px;
  background: #f8fafc;
  border-radius: 12px;
}

.meta-item {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  color: #64748b;
}

.action-section {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.buy-button {
  height: 48px;
  font-size: 16px;
  font-weight: 600;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(59, 130, 246, 0.3);
  transition: all 0.2s ease;
}

.buy-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 6px 24px rgba(59, 130, 246, 0.4);
}

.secondary-actions {
  display: flex;
  gap: 8px;
}

.secondary-btn {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 4px;
  padding: 8px 12px;
  font-size: 13px;
  font-weight: 500;
  border-radius: 8px;
  background: #f8fafc;
  border: 1px solid #e2e8f0;
  transition: all 0.2s ease;
}

.secondary-btn:hover {
  background: #f1f5f9;
  border-color: #cbd5e1;
}

.report-btn {
  color: #dc2626;
}

.report-btn:hover {
  background: #fef2f2;
  border-color: #fecaca;
}

.favorited {
  color: #dc2626;
}

.price-skeleton,
.action-skeleton,
.seller-skeleton {
  padding: 8px 0;
}

/* 响应式设计 */
@media (max-width: 1024px) {
  .content-layout {
    grid-template-columns: 1fr;
    gap: 24px;
  }

  .sidebar {
    position: static;
    order: -1;
  }
}

@media (max-width: 640px) {
  .container {
    padding: 0 16px;
  }

  .item-page {
    padding: 16px 0 60px;
  }

  .media-container,
  .description-card,
  .seller-card,
  .product-card {
    border-radius: 12px;
  }

  .price-amount {
    font-size: 28px;
  }

  .secondary-actions {
    flex-direction: column;
  }
}
</style>
