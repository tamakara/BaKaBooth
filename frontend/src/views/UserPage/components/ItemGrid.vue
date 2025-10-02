<template>
  <div v-if="loading" class="loading-grid">
    <el-skeleton
        v-for="i in 8"
        :key="i"
        animated
        class="skeleton-item"
    >
      <template #template>
        <el-skeleton-item variant="image" style="width: 100%; height: 200px"/>
        <el-skeleton-item variant="text" style="width: 80%; margin-top: 10px"/>
        <el-skeleton-item variant="text" style="width: 60%; margin-top: 5px"/>
      </template>
    </el-skeleton>
  </div>

  <div v-else-if="items.length === 0" class="empty-state">
    <el-empty description="暂无商品">
      <slot name="empty-action"></slot>
    </el-empty>
  </div>

  <div v-else class="items-grid">
    <div
        v-for="item in items"
        :key="item.id"
        class="item-card"
        @click="handleItemClick(item.id)"
    >
      <div class="item-image">
        <el-image
            :src="item.images[0]"
            fit="cover"
            style="width: 100%; height: 200px"
        >
          <template #placeholder>
            <div class="image-placeholder">
              <PhotoIcon class="placeholder-icon"/>
            </div>
          </template>
          <template #error>
            <div class="image-placeholder">
              <PhotoIcon class="placeholder-icon"/>
            </div>
          </template>
        </el-image>

        <!-- 商品状态标签 -->
        <div class="item-status" v-if="showStatus">
          <el-tag :type="getStatusType(item.stateCode)" size="small">
            {{ getStatusText(item.stateCode) }}
          </el-tag>
        </div>
      </div>

      <div class="item-info">
        <h3 class="item-name">{{ item.name }}</h3>
        <div class="item-price">¥{{ formatPrice(item.price) }}</div>
        <div class="item-meta">
          <span class="favorites">
            <HeartIcon class="meta-icon"/>
            {{ item.favorites }}
          </span>
          <span class="time">{{ formatTime(item.createdAt) }}</span>
        </div>

        <!-- 商品标签 -->
        <div class="item-tags" v-if="item.tags && item.tags.length > 0">
          <el-tag
              v-for="tag in item.tags.slice(0, 2)"
              :key="tag"
              size="small"
              type="info"
              effect="light"
          >
            {{ tag }}
          </el-tag>
          <span v-if="item.tags.length > 2" class="more-tags">+{{ item.tags.length - 2 }}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { PhotoIcon, HeartIcon } from "@heroicons/vue/24/outline";
import type {ItemVO} from "@/types/item.d.ts";

interface Props {
  items: ItemVO[]
  loading?: boolean
  showStatus?: boolean
}

const props = withDefaults(defineProps<Props>(), {
  loading: false,
  showStatus: false
});

const emit = defineEmits<{
  itemClick: [itemId: number]
}>();

// 处理商品点击
const handleItemClick = (itemId: number) => {
  emit('itemClick', itemId);
};

// 获取状态类型
const getStatusType = (stateCode: number) => {
  switch (stateCode) {
    case 1: return 'info';
    case 2: return 'success';
    case 3: return 'warning';
    default: return '';
  }
};

// 获取状态文本
const getStatusText = (stateCode: number) => {
  switch (stateCode) {
    case 1: return '草稿';
    case 2: return '在售';
    case 3: return '已下架';
    default: return '未知';
  }
};

// 格式化价格
const formatPrice = (price: number) => {
  return price.toFixed(2);
};

// 格式化时间
const formatTime = (time: string) => {
  return new Date(time).toLocaleDateString();
};
</script>

<style scoped>
.loading-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.skeleton-item {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 16px;
  border: 1px solid #e9ecef;
}

.empty-state {
  padding: 60px 20px;
  text-align: center;
}

.items-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
  gap: 20px;
}

.item-card {
  background: white;
  border-radius: 8px;
  overflow: hidden;
  border: 1px solid #e9ecef;
  transition: transform 0.2s, box-shadow 0.2s;
  cursor: pointer;
}

.item-card:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}

.item-image {
  position: relative;
  overflow: hidden;
}

.image-placeholder {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 200px;
  background-color: #f5f5f5;
  color: #c0c4cc;
  font-size: 24px;
}

.item-status {
  position: absolute;
  top: 8px;
  left: 8px;
}

.item-info {
  padding: 16px;
}

.item-name {
  font-size: 16px;
  font-weight: 500;
  color: #2c3e50;
  margin: 0 0 8px 0;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.item-price {
  font-size: 18px;
  font-weight: 600;
  color: #e74c3c;
  margin-bottom: 8px;
}

.item-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 14px;
  color: #7f8c8d;
  margin-bottom: 8px;
}

.favorites {
  display: flex;
  align-items: center;
  gap: 4px;
}

.item-tags {
  display: flex;
  gap: 4px;
  align-items: center;
  flex-wrap: wrap;
}

.more-tags {
  font-size: 12px;
  color: #7f8c8d;
  background: #f5f5f5;
  padding: 2px 6px;
  border-radius: 4px;
}

.placeholder-icon {
  width: 24px;
  height: 24px;
}

.meta-icon {
  width: 16px;
  height: 16px;
}
</style>
