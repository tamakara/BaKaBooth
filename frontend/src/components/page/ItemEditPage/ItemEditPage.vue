<template>
  <ItemEditPageLayout>
    <template #form>
      <el-form label-position="top" :model="formData" label-width="auto">
        <!-- 商品图片 -->
        <el-form-item label="商品图片" prop="images" class="image-upload-item">
          <el-upload
              v-model:file-list="imagesFiles"
              action="http://localhost:8080/file/upload"
              :headers="uploadHeaders"
              :data="{ isPublic: true }"
              name="file"
              list-type="picture-card"
              :on-success="handleImageSuccess"
              :on-remove="handleRemove"
              :limit="5"
              accept=".png,.jpg"
          >
            <el-icon class="upload-icon">
              <PlusIcon/>
            </el-icon>
            <template #tip>
              <div class="upload-tip">
                第一张图片将作为商品主视图，最多可上传5张图片
                <br/>
                建议图片尺寸为1:1且文件小于10MB
              </div>
            </template>
          </el-upload>
        </el-form-item>

        <!-- 商品基本信息 -->
        <el-form-item label="商品名称" prop="name" class="form-item">
          <el-input
              v-model="formData.name"
              placeholder="请输入商品名称"
              size="large"
              maxlength="50"
              show-word-limit
          />
        </el-form-item>

        <el-form-item label="商品描述" prop="description" class="form-item">
          <el-input
              v-model="formData.description"
              :rows="6"
              type="textarea"
              placeholder="请输入商品描述，详细描述有助于买家了解商品..."
              maxlength="500"
              show-word-limit
              resize="none"
          />
        </el-form-item>

        <!-- 商品标签 -->
        <el-form-item label="商品标签" prop="tags" class="form-item">
          <div class="tags-wrapper">
            <el-tag
                v-for="tag in formData.tags"
                :key="tag"
                closable
                @close="removeTag(tag)"
                class="tag-item"
            >
              {{ tag }}
            </el-tag>
            <el-input
                v-if="tagInput.visible"
                ref="tagInputRef"
                v-model="tagInput.value"
                size="default"
                @keyup.enter="confirmTag"
                @blur="confirmTag"
                class="tag-input"
            />
            <el-button
                v-else
                class="tag-button"
                size="default"
                @click="showTagInput"
            >
              <el-icon>
                <PlusIcon/>
              </el-icon>
              添加标签
            </el-button>
          </div>
        </el-form-item>

        <!-- 商品版本 -->
        <el-form-item label="商品版本" prop="variations" class="variations-item">
          <div class="variations-container">
            <Variation
                v-for="(variation, index) in formData.variations"
                :key="index"
                :variation="variation"
                :index="index"
                :can-delete="formData.variations.length > 1"
                @remove="removeVariation"
                @file-success="handleFileSuccess"
                @update-variation="handleUpdateVariation"
                @update-files="handleUpdateFiles"
            />

            <el-button
                type="primary"
                plain
                class="add-variation-btn"
                @click="addVariation"
            >
              <el-icon class="mr-2">
                <PlusIcon/>
              </el-icon>
              添加版本
            </el-button>
          </div>
        </el-form-item>

        <!-- 配送信息 -->
        <el-form-item label="发货时间" prop="deliveryTime" class="form-item">
          <el-input
              v-model="formData.deliveryTime"
              placeholder="请输入预计配送时间，如：3-7个工作日"
              size="large"
              maxlength="100"
              show-word-limit
          />
        </el-form-item>

        <el-form-item label="运费说明" prop="shippingDetails" class="form-item">
          <el-input
              v-model="formData.shippingDetails"
              :rows="4"
              type="textarea"
              placeholder="请输入物流配送相关信息，如：包邮政策、配送范围、配送方式等..."
              maxlength="300"
              show-word-limit
              resize="none"
          />
        </el-form-item>

        <!-- 售后服务 -->
        <el-form-item label="退换政策" prop="returnPolicy" class="form-item">
          <el-input
              v-model="formData.returnPolicy"
              :rows="4"
              type="textarea"
              placeholder="请输入退换货政策，如：7天无理由退货、退货条件等..."
              maxlength="300"
              show-word-limit
              resize="none"
          />
        </el-form-item>

        <el-form-item label="质保期限" prop="warrantyPeriod" class="form-item">
          <el-input
              v-model="formData.warrantyPeriod"
              placeholder="请输入保修期限，如：1年质保、终身维修等"
              size="large"
              maxlength="100"
              show-word-limit
          />
        </el-form-item>
      </el-form>
    </template>

    <template #action>
      <el-button class="action-btn cancel-btn" @click="handleBack">返回</el-button>
      <!-- 草稿状态：显示保存草稿和保存并上架 -->
      <template v-if="formData.stateCode === 1">
        <el-button type="primary" class="action-btn save-btn" @click="handleSave">保存草稿</el-button>
        <el-button type="primary" class="action-btn publish-btn" @click="handleSaveAndPublish">保存并上架</el-button>
      </template>
      <!-- 在售或下架状态：只显示保存按钮，不改变状态 -->
      <template v-else>
        <el-button type="primary" class="action-btn save-btn" @click="handleSaveOnly">保存</el-button>
      </template>
    </template>
  </ItemEditPageLayout>
</template>

<script setup lang="ts">
import ItemEditPageLayout from "./ItemEditPageLayout.vue";
import Variation from "./Variation.vue";
import {ref, computed, nextTick, watch, onMounted} from "vue";
import {PlusIcon} from "@heroicons/vue/24/outline";
import type {ItemEditFormVO, VariationsEditFormVO} from "@/types/item.d.ts";
import {getItemEditFormVO, updateItem} from "@/api/item.ts";
import {useRoute, useRouter} from "vue-router";
import {useUserStore} from "@/stores/user.ts";
import type {UploadUserFile} from "element-plus";
import {getFileVO} from "@/api/file.ts";
import type {FileVO} from "@/types/file.d.ts";

// 路由和状态
const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

// 表单数据
const formData = ref<ItemEditFormVO>({
  stateCode: 0,
  name: '',
  description: '',
  images: [],
  tags: [],
  variations: [{name: '', price: 0.00, stock: 10}],
  deliveryTime: '',
  shippingDetails: '',
  returnPolicy: '',
  warrantyPeriod: ''
});

const imagesFiles = ref<UploadUserFile[]>([]);
const variationFiles = ref<UploadUserFile[][]>([]);

// 标签输入状态
const tagInput = ref({
  visible: false,
  value: ''
});
const tagInputRef = ref();

// 计算属性
const uploadHeaders = computed(() => ({
  Authorization: 'Bearer ' + userStore.token
}));

// 初始化数据
onMounted(async () => {
  await loadItemData();
});

// 监听路由变化
watch(() => route.params.id, async (itemId) => {
  if (itemId) await loadItemData();
});

// 监听文件变化，同步到表单数据
watch(() => imagesFiles.value, (files) => {
  formData.value.images = files.map(file => file.uid as number);
}, {deep: true});

// 数据加载
async function loadItemData() {
  try {
    formData.value = await getItemEditFormVO(route.params.id as string);

    // 加载图片文件
    imagesFiles.value = await Promise.all(
        formData.value.images.map(async fileId => {
          const fileVO = await getFileVO(fileId);
          return {
            name: fileVO.name,
            uid: fileId,
            url: fileVO.url
          } as UploadUserFile;
        })
    );
  } catch (error) {
    console.error('加载商品数据失败:', error);
  }
}

// 标签管理
function removeTag(tag: string) {
  const index = formData.value.tags.indexOf(tag);
  if (index > -1) {
    formData.value.tags.splice(index, 1);
  }
}

function showTagInput() {
  tagInput.value.visible = true;
  nextTick(() => {
    tagInputRef.value?.input?.focus();
  });
}

function confirmTag() {
  const value = tagInput.value.value.trim();
  if (value && !formData.value.tags.includes(value)) {
    formData.value.tags.push(value);
  }
  tagInput.value.visible = false;
  tagInput.value.value = '';
}

// 版本管理
function addVariation() {
  formData.value.variations.push({
    name: '',
    price: 0.00,
    stock: 10,
  });
  variationFiles.value.push([]);
}

function removeVariation(index: number) {
  formData.value.variations.splice(index, 1);
  variationFiles.value.splice(index, 1);
}

function handleUpdateVariation(index: number, field: keyof VariationsEditFormVO, value: any) {
  if (formData.value.variations[index]) {
    (formData.value.variations[index] as any)[field] = value;
  }
}

function handleUpdateFiles(index: number, files: UploadUserFile[]) {
  if (variationFiles.value[index]) {
    variationFiles.value[index] = files;
  }
}

// 文件上传处理
function handleImageSuccess(response: FileVO, file: any) {
  file.name = response.name;
  file.uid = response.id;
  file.url = response.url;
}

function handleFileSuccess(response: FileVO, file: any) {
  file.name = response.name;
  file.uid = response.id;
  file.url = response.url;
}

function handleRemove(uploadFile: any, uploadFiles: any) {
  console.log('文件移除:', uploadFile, uploadFiles);
}

// 操作处理
function handleBack() {
  router.back();
}

async function handleSave() {
  try {
    // 设置状态为草稿
    formData.value.stateCode = 1;
    const newItemId = await updateItem(route.params.id as string, formData.value);
    // 跳转到新的itemId页面
    await router.push({name: 'item-edit', params: {id: newItemId}});
    // 可以添加成功提示
  } catch (error) {
    console.error('保存失败:', error);
    // 可以添加错误提示
  }
}

async function handleSaveAndPublish() {
  try {
    // 设置状态为上架
    formData.value.stateCode = 2;
    const newItemId = await updateItem(route.params.id as string, formData.value);
    // 跳转到新的itemId页面
    await router.push({name: 'item-edit', params: {id: newItemId}});
    // 可以添加成功提示
  } catch (error) {
    console.error('保存并上架失败:', error);
    // 可以添加错误提示
  }
}

async function handleSaveOnly() {
  try {
    const newItemId = await updateItem(route.params.id as string, formData.value);
    // 跳转到新的itemId页面
    await router.push({name: 'item-edit', params: {id: newItemId}});
    // 可以添加成功提示
  } catch (error) {
    console.error('保存失败:', error);
    // 可以添加错误提示
  }
}
</script>

<style scoped>
.tags-wrapper {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 8px;
}

.variations-container {
  display: flex;
  flex-direction: column;
}

/* 按钮样式优化 */
.action-btn {
  min-width: 100px;
  padding: 12px 24px;
  font-size: 14px;
  font-weight: 500;
  border-radius: 6px;
  margin-left: 12px;
}

.action-btn:first-child {
  margin-left: 0;
}

.cancel-btn {
  background-color: #f5f5f5;
  border-color: #d9d9d9;
  color: #666;
}

.cancel-btn:hover {
  background-color: #e6e6e6;
  border-color: #b3b3b3;
  color: #333;
}

.save-btn {
  background-color: #52c41a;
  border-color: #52c41a;
}

.save-btn:hover {
  background-color: #73d13d;
  border-color: #73d13d;
}

.publish-btn {
  background-color: #1890ff;
  border-color: #1890ff;
}

.publish-btn:hover {
  background-color: #40a9ff;
  border-color: #40a9ff;
}

/* 标签对齐优化 */
.tags-wrapper :deep(.el-tag) {
  height: 32px;
  line-height: 30px;
  margin-right: 8px;
  margin-bottom: 8px;
}

.tags-wrapper :deep(.el-input) {
  width: 90px;
  height: 32px;
  margin-right: 8px;
}

.tags-wrapper :deep(.el-input__wrapper) {
  height: 32px;
}

.tags-wrapper :deep(.el-input__inner) {
  height: 32px;
  line-height: 32px;
}

.tags-wrapper :deep(.el-button) {
  height: 32px;
  border-style: dashed;
  font-size: 14px;
  padding: 0 12px;
  border-radius: 4px;
}
</style>