<template>
  <ItemManagePageLayout>
    <template #actions-left>
      <el-radio-group v-model="currentStateCode" class="state-filter">
        <el-radio-button label="所有" :value="0"/>
        <el-radio-button label="在售" :value="2"/>
        <el-radio-button label="草稿" :value="1"/>
        <el-radio-button label="已下架" :value="3"/>
      </el-radio-group>
    </template>
    <template #actions-right>
      <div class="add-item">
        <el-button
            class="add-item-button"
            color="#2c9ba6"
            @click="handleAddItem"
        >
          <el-icon style="margin-right: 5px">
            <PlusIcon/>
          </el-icon>
          添加商品
        </el-button>
      </div>
    </template>
    <template #items>
      <div
          class="item"
          v-for="item in items"
          :key="item.id"
      >
        <div class="item-header">
          <div class="item-info">
            <el-image
                lazy
                :src="item.coverUrl"
                fit="cover"
                class="item-image"
            />
            <div class="item-details">
              <div class="item-state">{{ getStateText(item.stateCode) }}</div>
              <div class="item-name">{{ item.name }}</div>
              <div class="item-url">http://localhost:5173/item/{{ item.id }}</div>
            </div>
          </div>
          <div class="item-actions">
            <el-button
                v-if="item.stateCode === 2"
                size="small"
                type="warning"
                @click="handleTakeOffItem(item.id)"
                class="takeoff-button"
            >
              <el-icon style="margin-right: 4px">
                <ArrowDown/>
              </el-icon>
              下架
            </el-button>
            <el-button
                v-else-if="item.stateCode === 3"
                size="small"
                type="success"
                @click="handlePublishItem(item.id)"
                class="publish-button"
            >
              <el-icon style="margin-right: 4px">
                <ArrowUp/>
              </el-icon>
              上架
            </el-button>
            <el-button
                size="small"
                type="primary"
                @click="handleEditItem(item.id)"
                class="edit-button"
            >
              <el-icon style="margin-right: 4px">
                <Edit/>
              </el-icon>
              编辑
            </el-button>
          </div>
        </div>
        <div class="variations">
          <div
              class="variation"
              v-for="(variation, index) in item.variations"
              :key="index"
          >
            <div class="variation-name">
              <el-icon class="variation-icon">
                <CloudArrowDownIcon/>
              </el-icon>
              {{ variation.name }}
            </div>
            <div class="variation-data">
              <div class="variation-data-heading">销量</div>
              <div class="variation-data-value">{{ variation.sales || 0 }}</div>
            </div>
            <div class="variation-data">
              <div class="variation-data-heading">收藏</div>
              <div class="variation-data-value">{{ item.favorite }}</div>
            </div>
            <div class="variation-data">
              <div class="variation-data-heading">售价</div>
              <div class="variation-data-value">¥{{ variation.price }}</div>
            </div>
            <div class="variation-data">
              <div class="variation-data-heading">存库</div>
              <div class="variation-data-value">{{ variation.stock }}</div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </ItemManagePageLayout>
</template>

<script setup lang="ts">
import ItemManagePageLayout from "./ItemManagePageLayout.vue";
import {onMounted, ref, watch} from "vue";
import {CloudArrowDownIcon, PlusIcon} from "@heroicons/vue/24/outline";
import {Edit, ArrowDown, ArrowUp} from "@element-plus/icons-vue";
import {createItem} from "@/services/item.ts";
import {useRouter} from "vue-router";

const router = useRouter()

function getStateText(stateCode: number): string {
  const stateMap: Record<number, string> = {
    1: '草稿',
    2: '在售',
    3: '已下架'
  }
  return stateMap[stateCode] || '未知'
}

async function handleAddItem() {
  const itemId = await createItem()
  await router.push({name: 'item-edit', params: {id: itemId}})
}

async function handleEditItem(itemId: number) {
  await router.push({name: 'item-edit', params: {id: itemId}})
}

async function handleTakeOffItem(itemId: number) {
  // TODO: 实现下架逻辑，调用下架接口
  console.log('下架商品:', itemId)
  // 下架后刷新列表
  items.value = await getItemManageVO(currentStateCode.value)
}

async function handlePublishItem(itemId: number) {
  // TODO: 实现上架逻辑，调用上架接口
  console.log('上架商品:', itemId)
  // 上架后刷新列表
  items.value = await getItemManageVO(currentStateCode.value)
}

const currentStateCode = ref(0);

const items = ref<Array<ItemManageVO>>()

onMounted(async () => {
  items.value = await getItemManageVO(currentStateCode.value)
})

watch(currentStateCode, async (newState) => {
  items.value = await getItemManageVO(newState)
})
</script>

<style scoped>
.add-item-button {
  width: 137px;
  height: 42px;
  border-radius: 6px;
  font-size: 16px;
  font-weight: 500;
}

.item {
  width: 100%;
  padding: 24px;
  border: 1px solid #e4e7ed;
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
  transition: box-shadow 0.2s ease;
}

.item:hover {
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.item-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
}

.item-info {
  display: flex;
  gap: 16px;
  flex: 1;
}

.item-image {
  width: 80px;
  height: 80px;
  border-radius: 6px;
  border: 1px solid #f0f2f5;
  object-fit: cover;
}

.item-details {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 8px;
}

.item-state {
  width: 60px;
  height: 24px;
  font-size: 12px;
  font-weight: 600;
  text-align: center;
  line-height: 22px;
  border: 1px solid #d3d4d6;
  border-radius: 4px;
  color: #606266;
  background-color: #f5f7fa;
}

.item-name {
  font-size: 16px;
  font-weight: 500;
  color: #303133;
  line-height: 1.4;
}

.item-url {
  font-size: 13px;
  color: #909399;
}

.item-actions {
  margin-left: 16px;
  display: flex;
  gap: 8px;
}

.edit-button {
  height: 32px;
  padding: 0 16px;
  border-radius: 6px;
  font-size: 14px;
}

.takeoff-button {
  height: 32px;
  padding: 0 16px;
  border-radius: 6px;
  font-size: 14px;
}

.publish-button {
  height: 32px;
  padding: 0 16px;
  border-radius: 6px;
  font-size: 14px;
}

.variations {
  border-top: 1px dashed #e4e7ed;
}

.variation {
  height: 48px;
  border-bottom: 1px dashed #e4e7ed;
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #606266;
  padding: 0 4px;
}

.variation:last-child {
  border-bottom: none;
}

.variation-name {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 15px;
  font-weight: 500;
  color: #303133;
}

.variation-icon {
  font-size: 16px;
  color: #909399;
}

.variation-data {
  width: 90px;
  height: 100%;
  text-align: right;
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 2px;
}

.variation-data-heading {
  color: #909399;
  font-weight: 400;
  font-size: 12px;
  line-height: 1.2;
}

.variation-data-value {
  color: #303133;
  font-weight: 600;
  font-size: 14px;
  line-height: 1.2;
}

.state-filter :deep(.el-radio-button__inner) {
  height: 42px;
  line-height: 40px;
  padding: 0 20px;
  font-size: 16px;
  font-weight: 500;
}

.state-filter :deep(.el-radio-button:first-child .el-radio-button__inner) {
  border-top-left-radius: 6px;
  border-bottom-left-radius: 6px;
}

.state-filter :deep(.el-radio-button:last-child .el-radio-button__inner) {
  border-top-right-radius: 6px;
  border-bottom-right-radius: 6px;
}
</style>