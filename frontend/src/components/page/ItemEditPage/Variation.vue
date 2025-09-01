<template>
  <div class="variation-card">
    <div class="variation-header">
      <span class="variation-title">版本 {{ index + 1 }}</span>
      <el-button
          v-if="canDelete"
          type="danger"
          size="small"
          text
          @click="$emit('remove', index)"
      >
        删除
      </el-button>
    </div>

    <el-form class="variation-form" label-position="top">
      <div class="variation-row">
        <el-form-item label="版本名称" class="variation-name">
          <el-input
              :model-value="variation.name"
              @update:model-value="updateVariation('name', $event)"
              placeholder="如：标准版、高级版等"
              size="default"
          />
        </el-form-item>
        <el-form-item label="价格" class="variation-price">
          <el-input-number
              :model-value="variation.price"
              @update:model-value="updateVariation('price', $event)"
              placeholder="0.00"
              size="default"
              :min="0"
              :max="999999"
              :precision="2"
              :step="0.01"
              controls-position="right"
          >
            <template #prefix>¥</template>
          </el-input-number>
        </el-form-item>
        <el-form-item label="库存" class="variation-stock">
          <el-input-number
              :model-value="variation.stock"
              @update:model-value="updateVariation('stock', $event)"
              :min="0"
              :max="9999"
              size="default"
              controls-position="right"
          />
        </el-form-item>
      </div>

      <el-form-item label="商品文件" class="variation-file">
        <el-upload
            :file-list="files"
            @update:file-list="updateFiles"
            class="file-upload"
            action="http://localhost:8080/file/upload"
            :headers="uploadHeaders"
            name="file"
            :on-success="handleFileSuccess"
            :on-remove="handleFileRemove"
        >
          <el-button type="primary" size="large" class="upload-file-btn">
            <el-icon class="mr-2">
              <PlusIcon/>
            </el-icon>
            上传文件
          </el-button>
          <template #tip>
            <div class="file-upload-tip">
              剩余容量 666GB / 888GB
            </div>
          </template>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { computed } from 'vue';
import { PlusIcon } from "@heroicons/vue/24/outline";
import { useUserStore } from "@/stores/user.ts";
import type { VariationsEditFormVO } from "@/types/ItemTypes";
import type { UploadUserFile } from "element-plus";
import type { FileVO } from "@/types/FileTypes.ts";

interface Props {
  variation: VariationsEditFormVO;
  index: number;
  files: UploadUserFile[];
  canDelete: boolean;
}

const props = defineProps<Props>();

const emit = defineEmits<{
  remove: [index: number];
  fileSuccess: [response: FileVO, file: any];
  updateVariation: [index: number, field: keyof VariationsEditFormVO, value: any];
  updateFiles: [index: number, files: UploadUserFile[]];
}>();

const userStore = useUserStore();

const uploadHeaders = computed(() => ({
  Authorization: 'Bearer ' + userStore.token
}));

function handleFileSuccess(response: FileVO, file: any) {
  emit('fileSuccess', response, file);
}

function handleFileRemove(_uploadFile: any, uploadFiles: any) {
  emit('updateFiles', props.index, uploadFiles);
}

function updateVariation(field: keyof VariationsEditFormVO, value: any) {
  emit('updateVariation', props.index, field, value);
}

function updateFiles(files: UploadUserFile[]) {
  emit('updateFiles', props.index, files);
}
</script>

<style scoped>
.variation-card {
  background: #f8f9fa;
  border: 1px solid #e4e7ed;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 16px;
  transition: all 0.3s ease;
}

.variation-card:hover {
  border-color: #409eff;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.1);
}

.variation-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 16px;
  padding-bottom: 12px;
  border-bottom: 1px solid #e4e7ed;
}

.variation-title {
  font-size: 16px;
  font-weight: 600;
  color: #303133;
}

.variation-row {
  display: grid;
  grid-template-columns: 2fr 1fr 1fr;
  gap: 16px;
  margin-bottom: 16px;
}

.file-upload-tip {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
  line-height: 1.4;
}

.upload-file-btn {
  height: 40px;
  font-size: 14px;
  padding: 0 20px;
  border-radius: 6px;
}

.file-upload :deep(.el-upload-list__item-name) {
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.file-upload :deep(.el-upload-list__item) {
  max-width: 100%;
}

/* 响应式设计 */
@media (max-width: 1200px) {
  .file-upload :deep(.el-upload-list__item-name) {
    max-width: 250px;
  }
}

@media (max-width: 768px) {
  .variation-row {
    grid-template-columns: 1fr;
    gap: 12px;
  }

  .file-upload :deep(.el-upload-list__item-name) {
    max-width: 180px;
  }
}
</style>
