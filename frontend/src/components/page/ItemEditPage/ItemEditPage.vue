<template>
  <ItemEditPageLayout>
    <template #form>
      <el-form
          label-position="top"
          class="form"
          :model="formData"
          label-width="auto"
      >
        <el-form-item label="商品状态" prop="state" class="form-item">
          <div class="state-switch-container">
            <el-switch
                v-model="isPublic"
                active-text="公开"
                inactive-text="不公开"
                active-value="public"
                inactive-value="private"
                size="large"
                @change="handleStateChange"
            />
            <div class="state-description">
              {{ isPublic === 'public' ? '商品将在商店中公开显示' : '商品不会在商店中显示' }}
            </div>
          </div>
        </el-form-item>

        <el-form-item label="商品图片" prop="images" class="image-upload-item">
          <el-upload
              v-model:file-list="imagesFiles"
              action="http://localhost:8080/file/upload"
              :headers="{ Authorization: 'Bearer ' + userStore.token }"
              :data="{ isPublic: false }"
              name="file"
              list-type="picture-card"
              :on-success="handleImageSuccess"
              :on-remove="handleRemove"
              class="image-upload"
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

        <el-form-item label="商品标签" prop="tags" class="form-item">
          <el-tag
              v-for="tag in formData.tags"
              :key="tag"
              closable
              @close="handleTagClose(tag)"
              class="tag-item"
          >
            {{ tag }}
          </el-tag>
          <el-input
              v-if="inputVisible"
              ref="InputRef"
              v-model="inputValue"
              size="small"
              @keyup.enter="handleInputConfirm"
              @blur="handleInputConfirm"
              class="tag-input"
          />
          <el-button v-else class="tag-button" size="default" @click="showInput">
            <el-icon>
              <PlusIcon/>
            </el-icon>
            添加标签
          </el-button>
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
                    <el-input-number
                        v-model="variation.price"
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
                        v-model="variation.stock"
                        :min="0"
                        :max="9999"
                        size="default"
                        controls-position="right"
                    />
                  </el-form-item>
                </div>

                <el-form-item label="商品文件" class="variation-file">
                  <el-upload
                      v-model:file-list="variationFiles[index]"
                      class="file-upload"
                      action="http://localhost:8080/file/upload"
                      :headers="{ Authorization: 'Bearer ' + userStore.token }"
                      name="file"
                      :on-success="handleFileSuccess"
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
      </el-form>
    </template>

    <template #action>
      <div class="action-buttons">
        <el-button class="action-btn cancel-btn" @click="handleBack">返回</el-button>
        <el-button type="primary" class="action-btn save-btn" @click="handleSave">保存</el-button>
      </div>
    </template>
  </ItemEditPageLayout>
</template>

<script setup lang="ts">
import ItemEditPageLayout from "@/components/page/ItemEditPage/ItemEditPageLayout.vue";
import {ref, computed, nextTick, watch, onMounted} from "vue";
import {PlusIcon} from "@heroicons/vue/24/outline";
import type {ItemEditFormVO} from "@/types/ItemTypes";
import {getItemEditFormVO, updateItem} from "@/api/item.ts";
import {useRoute} from "vue-router";
import {useUserStore} from "@/stores/user.ts";
import type {UploadUserFile} from "element-plus";
import {getFileVO} from "@/api/file.ts";
import type {FileVO} from "@/types/FileTypes.ts";

const route = useRoute()
const userStore = useUserStore()

const imagesFiles = ref<UploadUserFile[]>([])
const variationFiles = ref<UploadUserFile[][]>([])
const formData = ref<ItemEditFormVO>({})

onMounted(async () => {
  formData.value = await getItemEditFormVO(route.params.id as string)
  imagesFiles.value = await Promise.all(
      formData.value.images.map(async fileId => {
        const fileVO = await getFileVO(fileId)
        return {
          name: fileVO.name,
          uid: fileId,
          url: fileVO.url
        } as UploadUserFile
      })
  )
  variationFiles.value = await Promise.all(
      formData.value.variations.map(async variation =>
          Promise.all(
              variation.files.map(async fileId => {
                const fileVO = await getFileVO(fileId)
                return {
                  name: fileVO.name,
                  uid: fileId,
                  url: fileVO.url
                } as UploadUserFile
              })
          )
      )
  )
})

watch(() => imagesFiles.value, (files) => {
  formData.value.images = files.map(file => file.uid as number)
}, {deep: true})

watch(() => variationFiles.value, (files) => {
  formData.value.variations.forEach((variation, index) => {
        variation.files = files[index].map(file => file.uid as number)
      }
  )
}, {deep: true})

watch(() => route.params.id, async (itemId) => {
  if (itemId) formData.value = await getItemEditFormVO(itemId as string)
})

// 状态开关相关
const isPublic = computed({
  get: () => formData.value.state,
  set: (value) => {
    formData.value.state = value
  }
})

function handleStateChange(value: string) {
  formData.value.state = value
}

// 标签输入相关
const inputVisible = ref(false)
const inputValue = ref('')
const InputRef = ref()

function handleTagClose(tag: string) {
  formData.value.tags.splice(formData.value.tags.indexOf(tag), 1)
}

function showInput() {
  inputVisible.value = true
  nextTick(() => {
    InputRef.value.input.focus()
  })
}

function handleInputConfirm() {
  if (inputValue.value && !formData.value.tags.includes(inputValue.value)) {
    formData.value.tags.push(inputValue.value)
  }
  inputVisible.value = false
  inputValue.value = ''
}

function handleRemove(uploadFile: any, uploadFiles: any) {
  console.log(uploadFile, uploadFiles)
}

function addVariation() {
  formData.value.variations.push({
    name: '',
    price: 0.00,
    stock: 10,
    files: []
  })
}

function removeVariation(index: number) {
  formData.value.variations.splice(index, 1)
}

function handleBack() {
  // 处理取消逻辑
  console.log('返回')
}

function handleSave() {
  // 处理保存逻辑
  updateItem(route.params.id as string, formData.value)
}

function handleImageSuccess(response: FileVO, file) {
  file.name = response.name
  file.uid = response.id
  file.url = response.url
}

function handleFileSuccess(response: FileVO, file) {
  file.name = response.name
  file.uid = response.id
  file.url = response.url
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

.upload-icon {
  font-size: 28px;
  color: #8c939d;
  width: 28px;
  height: 28px;
}

.upload-tip {
  font-size: 12px;
  color: #606266;
  margin-top: 8px;
  line-height: 1.4;
}

.state-switch-container {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.state-description {
  font-size: 12px;
  color: #909399;
  line-height: 1.4;
}

.tag-item {
  margin-right: 8px;
  margin-bottom: 8px;
}

.tag-input {
  width: 90px;
  margin-right: 8px;
  vertical-align: bottom;
}

.tag-button {
  border-style: dashed;
  height: 32px;
  font-size: 14px;
  padding: 0 12px;
  border-radius: 4px;
}

.variations-item {
  margin-bottom: 32px;
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

.add-variation-btn {
  width: 100%;
  height: 48px;
  border-style: dashed;
  border-width: 2px;
  font-size: 14px;
  margin-top: 8px;
}

.upload-file-btn {
  height: 40px;
  font-size: 14px;
  padding: 0 20px;
  border-radius: 6px;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  padding: 32px 24px;
  background: white;
  border-top: 1px solid #e4e7ed;
  margin-top: 24px;
}

.action-btn {
  min-width: 120px;
  height: 44px;
  font-size: 16px;
  font-weight: 500;
  padding: 0 24px;
  border-radius: 6px;
  transition: all 0.3s ease;
}

.cancel-btn {
  color: #606266;
  border-color: #dcdfe6;
  background-color: #ffffff;
}

.cancel-btn:hover {
  color: #409eff;
  border-color: #409eff;
  background-color: #ecf5ff;
}

.save-btn {
  background-color: #409eff;
  border-color: #409eff;
  color: #ffffff;
}

.save-btn:hover {
  background-color: #66b1ff;
  border-color: #66b1ff;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .form {
    padding: 16px;
  }

  .variation-row {
    grid-template-columns: 1fr;
    gap: 12px;
  }

  .action-buttons {
    flex-direction: column;
    gap: 12px;
    padding: 24px 16px;
  }

  .action-btn {
    width: 100%;
    min-width: auto;
  }
}
</style>