<template>
  <ItemEditPageLayout>
    <template #form>
      <el-form
        label-position="top"
        class="form"
        :model="formData"
        label-width="auto"
      >
        <el-form-item label="商品图片" prop="images" class="image-upload-item">
          <el-upload
            v-model:file-list="formData.images"
            action=""
            list-type="picture-card"
            :on-remove="handleRemove"
            class="image-upload"
            :limit="5"
          >
            <el-icon class="upload-icon">
              <PlusIcon/>
            </el-icon>
            <template #tip>
              <div class="upload-tip">
                第一张图片会作为商品列表的主视图使用，最多可上传5张图片
              </div>
            </template>
          </el-upload>
        </el-form-item>

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

        <el-form-item label="商品版本" prop="variations" class="variations-item">
          <div class="variations-container">
            <div
              class="variation-card"
              v-for="(variation, index) in formData.variations"
              :key="index"
            >
              <div class="variation-header">
                <span class="variation-title">版本 {{ index + 1 }}</span>
                <el-button
                  v-if="formData.variations.length > 1"
                  type="danger"
                  size="small"
                  text
                  @click="removeVariation(index)"
                >
                  删除
                </el-button>
              </div>

              <el-form class="variation-form" label-position="top">
                <div class="variation-row">
                  <el-form-item label="版本名称" class="variation-name">
                    <el-input
                      v-model="variation.name"
                      placeholder="如：标准版、高级版等"
                      size="default"
                    />
                  </el-form-item>
                  <el-form-item label="价格" class="variation-price">
                    <el-input
                      v-model="variation.price"
                      placeholder="0.00"
                      size="default"
                    >
                      <template #prefix>¥</template>
                    </el-input>
                  </el-form-item>
                </div>

                <el-form-item label="商品文件" class="variation-file">
                  <el-upload
                    v-model:file-list="variation.fileList"
                    class="file-upload"
                    action=""
                    :limit="1"
                    accept=".zip,.rar,.7z,.pdf,.doc,.docx"
                  >
                    <el-button type="primary" size="default">
                      <el-icon class="mr-2"><PlusIcon /></el-icon>
                      选择文件
                    </el-button>
                    <template #tip>
                      <div class="file-upload-tip">
                        支持 zip、rar、7z、pdf、doc、docx 格式，单个文件不超过100MB
                      </div>
                    </template>
                  </el-upload>
                </el-form-item>
              </el-form>
            </div>

            <el-button
              type="primary"
              plain
              class="add-variation-btn"
              @click="addVariation"
            >
              <el-icon class="mr-2"><PlusIcon /></el-icon>
              添加新版本
            </el-button>
          </div>
        </el-form-item>
      </el-form>
    </template>

    <template #action>
      <div class="action-buttons">
        <el-button size="large" @click="handleCancel">取消</el-button>
        <el-button type="primary" size="large" @click="handleSave">
          保存商品
        </el-button>
      </div>
    </template>
  </ItemEditPageLayout>
</template>

<script setup lang="ts">
import ItemEditPageLayout from "@/components/page/ItemEditPage/ItemEditPageLayout.vue";
import {ref} from "vue";
import {PlusIcon} from "@heroicons/vue/24/outline";

const formData = ref({
  name: '',
  images: [],
  description: '',
  tags: [],
  variations: [{
    name: '',
    price: '',
    fileList: []
  }]
})

function handleRemove(uploadFile: any, uploadFiles: any) {
  console.log(uploadFile, uploadFiles)
}

function addVariation() {
  formData.value.variations.push({
    name: '',
    price: '',
    fileList: []
  })
}

function removeVariation(index: number) {
  formData.value.variations.splice(index, 1)
}

function handleCancel() {
  // 处理取消逻辑
  console.log('取消编辑')
}

function handleSave() {
  // 处理保存逻辑
  console.log('保存商品', formData.value)
}
</script>

<style scoped>
.form {
  max-width: 800px;
  margin: 0 auto;
  padding: 24px;
}

.form-item {
  margin-bottom: 24px;
}

.image-upload-item {
  margin-bottom: 32px;
}

.image-upload {
  width: 100%;
}

.upload-icon {
  font-size: 28px;
  color: #8c939d;
  width: 28px;
  height: 28px;
}

.upload-tip {
  font-size: 12px;
  color: #606266;
  text-align: center;
  margin-top: 8px;
  line-height: 1.4;
}

.variations-item {
  margin-bottom: 32px;
}

.variations-container {
  width: 100%;
}

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

.variation-form {
  margin: 0;
}

.variation-row {
  display: grid;
  grid-template-columns: 2fr 1fr;
  gap: 16px;
  margin-bottom: 16px;
}

.variation-name,
.variation-price,
.variation-file {
  margin-bottom: 0;
}

.file-upload {
  width: 100%;
}

.file-upload-tip {
  font-size: 12px;
  color: #909399;
  margin-top: 4px;
  line-height: 1.4;
}

.add-variation-btn {
  width: 100%;
  height: 48px;
  border-style: dashed;
  border-width: 2px;
  font-size: 14px;
  margin-top: 8px;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 16px;
  padding: 24px;
  background: white;
  border-top: 1px solid #e4e7ed;
  margin-top: 24px;
}

.mr-2 {
  margin-right: 8px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .form {
    max-width: 100%;
    padding: 16px;
  }

  .variation-row {
    grid-template-columns: 1fr;
    gap: 12px;
  }

  .action-buttons {
    flex-direction: column;
    gap: 12px;
  }

  .action-buttons .el-button {
    width: 100%;
  }
}
</style>