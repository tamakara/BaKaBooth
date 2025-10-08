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
              返回
            </el-button>
          </div>

          <div class="content-grid">
            <!-- 左侧：图片 + 描述 -->
            <div class="left-column">
              <div class="media-card">
                <div v-if="loading" class="media-skeleton">
                  <el-skeleton animated :throttle="0">
                    <template #template>
                      <el-skeleton-item variant="image" style="width:100%;height:420px;border-radius:12px"/>
                    </template>
                  </el-skeleton>
                </div>
                <div v-else>
                  <ImageSlider :images="itemVO.images || []"/>
                </div>
              </div>

              <div class="section-card">
                <h3 class="section-title">商品描述</h3>
                <div class="description" v-if="!loading">{{ itemVO.description || '暂无描述' }}</div>
                <el-skeleton v-else :rows="5" animated/>
              </div>
            </div>

            <!-- 右侧：核心信息 -->
            <div class="right-column">
              <div class="seller-card info-card">
                <div class="seller-header">
                  <div class="avatar-box">
                    <el-skeleton animated v-if="loading">
                      <template #template>
                        <el-skeleton-item variant="circle" style="width:64px;height:64px"/>
                      </template>
                    </el-skeleton>
                    <el-avatar v-else :size="64" :src="sellerVO.avatarUrl"/>
                  </div>
                  <div class="seller-main" v-if="!loading">
                    <div class="seller-top">
                      <span class="seller-name" @click="goSeller">{{ sellerVO.username }}</span>
                      <el-tag v-if="sellerVO.isCurrentUser" size="small" type="success" effect="dark">我</el-tag>
                    </div>
                    <div class="seller-meta-line">
                      关注者 {{ sellerVO.followers || 0 }}
                    </div>
                    <div class="seller-actions">
                      <el-button size="small" :type="isFollowed ? 'primary':'default'" @click="toggleFollow">
                        <el-icon style="margin-right:4px">
                          <StarIcon/>
                        </el-icon>
                        {{ isFollowed ? '已关注' : '关注' }}
                      </el-button>
                      <el-button size="small" type="primary" plain @click="chatSeller">
                        <el-icon style="margin-right:4px">
                          <ChatBubbleOvalLeftEllipsisIcon/>
                        </el-icon>
                        联系
                      </el-button>
                    </div>
                  </div>
                </div>
              </div>

              <div class="info-card">
                <div class="title-row">
                  <h1 class="item-name">
                    <el-skeleton v-if="loading" animated :rows="1"/>
                    <template v-else>{{ itemVO.name }}</template>
                  </h1>
                </div>

                <div class="price-row" v-if="!loading">
                  <span class="currency">¥</span>
                  <span class="amount">{{ formatPrice(itemVO.price) }}</span>
                  <span class="shipping-text" v-if="itemVO.deliveryMethodCode !== undefined">{{ shippingInline }}</span>
                  <el-tag v-if="itemVO.stateCode ===0" size="small" type="warning" effect="light">已下架</el-tag>
                </div>
                <div v-else class="price-skeleton">
                  <el-skeleton-item variant="text" style="width:160px;height:40px"/>
                </div>

                <div class="meta-row" v-if="!loading">
                  <span class="meta-item">
                    <el-icon><HeartIcon/></el-icon>
                    {{ favoriteCount }} 人收藏
                  </span>
                  <span class="meta-item">创建于 {{ formatTime(itemVO.createdAt) }}</span>
                  <span class="meta-item" v-if="itemVO.updatedAt">更新 {{ formatTime(itemVO.updatedAt) }}</span>
                </div>
                <el-skeleton v-else :rows="1" animated/>

                <div class="action-row">
                  <el-button
                      :type="isFavorited ? 'danger' : 'default'"
                      :loading="favoriteLoading"
                      @click="toggleFavorite"
                      class="fav-btn"
                  >
                    <el-icon style="margin-right:4px">
                      <HeartIcon :class="{ active:isFavorited }"/>
                    </el-icon>
                    {{ isFavorited ? '已收藏' : '收藏' }}
                  </el-button>
                  <el-button type="primary" :disabled="loading || itemVO.stateCode !==2" @click="handleBuy">
                    立即购买
                  </el-button>
                  <el-button @click="shareItem">分享</el-button>
                  <el-button type="warning" @click="reportItem" plain>举报</el-button>
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
import {HeartIcon, ChatBubbleOvalLeftEllipsisIcon, StarIcon} from '@heroicons/vue/24/outline'

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

async function fetchData() {
  try {
    loading.value = true
    const vo = await getItemVO(itemId.value)
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

function formatPrice(p: number | string | undefined) {
  if (p === undefined) return '0.00'
  const n = typeof p === 'string' ? parseFloat(p) : p
  if (Number.isNaN(n)) return '0.00'
  return n.toFixed(2)
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
  background: linear-gradient(180deg, #f8faff 0%, #f5f5f5 100%);
  padding: 30px 0 60px;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
}

.breadcrumb-bar {
  margin-bottom: 16px;
}

.content-grid {
  display: grid;
  grid-template-columns: 1fr 360px;
  gap: 32px;
  align-items: flex-start;
}

.left-column, .right-column {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.media-card, .section-card, .info-card {
  background: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, .06);
  padding: 20px;
  position: relative;
}

.media-card {
  padding: 0;
}

.media-skeleton {
  height: 420px;
}

.section-title {
  margin: 0 0 16px;
  font-size: 18px;
  font-weight: 600;
}

.description {
  line-height: 1.65;
  color: #444;
  white-space: pre-wrap;
}

.info-card {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.item-name {
  font-size: 24px;
  font-weight: 600;
  margin: 0;
  line-height: 1.3;
}

.price-row {
  display: flex;
  align-items: center;
  gap: 12px;
  font-size: 40px;
  font-weight: 700;
  background: linear-gradient(135deg, #667eea, #764ba2);
  -webkit-background-clip: text;
  background-clip: text; /* 标准属性补充 */
  color: transparent;
}

.currency {
  font-size: 22px;
  font-weight: 500;
}

.meta-row {
  display: flex;
  flex-wrap: wrap;
  gap: 14px;
  font-size: 13px;
  color: #666;
}

.meta-item {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

.action-row {
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}

.seller-header {
  display: flex;
  gap: 16px;
}

.avatar-box {
  width: 64px;
  height: 64px;
  flex-shrink: 0;
}

.seller-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 6px;
}

.seller-top {
  display: flex;
  align-items: center;
  gap: 8px;
}

.seller-name {
  font-weight: 600;
  cursor: pointer;
}

.seller-name:hover {
  color: #409eff;
}

.seller-meta-line {
  font-size: 12px;
  color: #666;
}

.seller-actions {
  display: flex;
  gap: 8px;
  margin-top: 4px;
}

/* 新增运费小字样式 */
.shipping-text { font-size:14px; font-weight:400; margin-left:8px; color:#8a92a0; }

/* 移除物流信息表格样式 */
.logistics-section { display:none; }

@media (max-width: 1100px) {
  .content-grid {
    grid-template-columns: 1fr;
  }

  .right-column {
    order: -1;
  }
}
</style>
