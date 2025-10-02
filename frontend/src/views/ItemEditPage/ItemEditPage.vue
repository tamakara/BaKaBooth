<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="item-edit-page">
        <div class="container">
          <div class="page-header">
            <h1>{{ isEdit ? '编辑商品' : '发布商品' }}</h1>
            <p>{{ isEdit ? '修改商品信息' : '发布您的闲置物品，让它们找到新主人' }}</p>
          </div>

          <div class="edit-content">
            <el-form
                ref="formRef"
                :model="formData"
                :rules="rules"
                label-position="top"
                class="item-form"
            >
              <!-- 商品图片 -->
              <el-form-item label="商品图片" prop="images" class="image-upload-item">
                <el-upload
                    v-model:file-list="imageFiles"
                    action="http://localhost:8080/file/upload"
                    :headers="uploadHeaders"
                    name="file"
                    list-type="picture-card"
                    :on-success="handleImageSuccess"
                    :on-remove="handleImageRemove"
                    :limit="5"
                    accept=".png,.jpg,.jpeg"
                >
                  <el-icon class="upload-icon">
                    <Plus/>
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
              <div class="form-section">
                <h3>商品信息</h3>
                <div class="form-row">
                  <el-form-item label="商品名称" prop="name" class="form-item">
                    <el-input
                        v-model="formData.name"
                        placeholder="请输入商品名称"
                        maxlength="50"
                        show-word-limit
                    />
                  </el-form-item>
                  <el-form-item label="商品价格" prop="price" class="form-item">
                    <el-input-number
                        v-model="formData.price"
                        :min="0.01"
                        :max="99999"
                        :precision="2"
                        placeholder="请输入价格"
                        style="width: 100%"
                    />
                  </el-form-item>
                </div>

                <el-form-item label="商品描述" prop="description" class="form-item">
                  <el-input
                      v-model="formData.description"
                      :rows="6"
                      type="textarea"
                      placeholder="请详细描述商品的状况、特点、使用情况等..."
                      maxlength="500"
                      show-word-limit
                      resize="none"
                  />
                </el-form-item>
              </div>

              <!-- 商品标签 -->
              <div class="form-section">
                <h3>商品标签</h3>
                <el-form-item label="标签" prop="tags" class="form-item">
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
                        size="small"
                        @keyup.enter="confirmTag"
                        @blur="confirmTag"
                        class="tag-input"
                        placeholder="输入标签"
                    />
                    <el-button
                        v-else
                        class="tag-button"
                        size="small"
                        @click="showTagInput"
                    >
                      <el-icon>
                        <Plus/>
                      </el-icon>
                      添加标签
                    </el-button>
                  </div>
                </el-form-item>
              </div>

              <!-- 交易信息 -->
              <div class="form-section">
                <h3>交易信息</h3>
                <div class="form-row">
                  <el-form-item label="发货时间" prop="deliveryPeriod" class="form-item">
                    <el-input
                        v-model="formData.deliveryPeriod"
                        placeholder="如：3-7个工作日"
                        maxlength="50"
                    />
                  </el-form-item>
                  <el-form-item label="退换政策" prop="returnPeriod" class="form-item">
                    <el-input
                        v-model="formData.returnPeriod"
                        placeholder="如：7天无理由退货"
                        maxlength="50"
                    />
                  </el-form-item>
                </div>

                <el-form-item label="运费说明" prop="postage" class="form-item">
                  <el-input
                      v-model="formData.postage"
                      :rows="3"
                      type="textarea"
                      placeholder="请说明运费政策，如：包邮、买家承担运费等..."
                      maxlength="200"
                      show-word-limit
                  />
                </el-form-item>
              </div>

              <!-- 操作按钮 -->
              <div class="form-actions">
                <el-button @click="handleCancel">取消</el-button>
                <el-button @click="handleSaveDraft" :loading="draftLoading">
                  保存草稿
                </el-button>
                <el-button type="primary" @click="handlePublish" :loading="publishLoading">
                  {{ isEdit ? '保存并上架' : '立即发布' }}
                </el-button>
              </div>
            </el-form>
          </div>
        </div>
      </div>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/layout/BaseLayout.vue";
import Banner from "@/components/business/Banner.vue";
import {ref, reactive, computed, onMounted, nextTick} from "vue";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import {PlusIcon} from "@heroicons/vue/24/outline";
import type {FormInstance, FormRules, UploadUserFile, UploadProps} from "element-plus";
import {createItem, updateItem, getItemVO} from "@/services/item.ts";
import type {ItemEditFormVO, ItemVO} from "@/types/item.d.ts";
import {useUserStore} from "@/stores/user";

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

// 响应式数据
const formRef = ref<FormInstance>();
const tagInputRef = ref();
const imageFiles = ref<UploadUserFile[]>([]);
const draftLoading = ref(false);
const publishLoading = ref(false);

// 表单数据
const formData = reactive<ItemEditFormVO>({
  stateCode: 1, // 1: 草稿, 2: 在售
  name: '',
  price: 0,
  description: '',
  postage: '',
  returnPeriod: '',
  deliveryPeriod: '',
  images: [],
  tags: []
});

// 标签输入状态
const tagInput = reactive({
  visible: false,
  value: ''
});

// 计算属性
const itemId = computed(() => route.params.id as string);
const isEdit = computed(() => itemId.value && itemId.value !== 'new');
const uploadHeaders = computed(() => ({
  Authorization: 'Bearer ' + userStore.token
}));

// 表单验证规则
const rules: FormRules = {
  name: [
    { required: true, message: '请输入商品名称', trigger: 'blur' },
    { min: 2, max: 50, message: '商品名称长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  price: [
    { required: true, message: '请输入商品价格', trigger: 'blur' },
    { type: 'number', min: 0.01, message: '价格必须大于0', trigger: 'blur' }
  ],
  description: [
    { required: true, message: '请输入商品描述', trigger: 'blur' },
    { min: 10, message: '商品描述至��10个字符', trigger: 'blur' }
  ],
  deliveryPeriod: [
    { required: true, message: '请输入发货时间', trigger: 'blur' }
  ],
  returnPeriod: [
    { required: true, message: '请输入退换政策', trigger: 'blur' }
  ],
  postage: [
    { required: true, message: '请输入运费说明', trigger: 'blur' }
  ]
};

// 加载商品数据（编辑模式）
const loadItemData = async () => {
  if (!isEdit.value) return;

  try {
    const itemData: ItemVO = await getItemVO(itemId.value);

    // 填充表单数据
    formData.name = itemData.name;
    formData.price = itemData.price;
    formData.description = itemData.description;
    formData.postage = itemData.postage;
    formData.returnPeriod = itemData.returnPeriod;
    formData.deliveryPeriod = itemData.deliveryPeriod;
    formData.tags = [...itemData.tags];
    formData.stateCode = itemData.stateCode;

    // 处理图片数据
    formData.images = itemData.images.map((url, index) => index + 1); // 临时处理
    imageFiles.value = itemData.images.map((url, index) => ({
      name: `image_${index + 1}`,
      url: url,
      uid: index + 1
    }));

  } catch (error) {
    console.error('加载商品数据��败:', error);
    ElMessage.error('加载商品数据失败');
  }
};

// 图片上传成功处理
const handleImageSuccess: UploadProps['onSuccess'] = (response, file) => {
  file.uid = response;
  formData.images.push(response);
  ElMessage.success('图片上传成功');
};

// 图片移除处理
const handleImageRemove: UploadProps['onRemove'] = (file) => {
  const index = formData.images.findIndex(id => id === file.uid);
  if (index > -1) {
    formData.images.splice(index, 1);
  }
};

// 标签管理
const removeTag = (tag: string) => {
  const index = formData.tags.indexOf(tag);
  if (index > -1) {
    formData.tags.splice(index, 1);
  }
};

const showTagInput = () => {
  tagInput.visible = true;
  nextTick(() => {
    tagInputRef.value?.input?.focus();
  });
};

const confirmTag = () => {
  const value = tagInput.value.trim();
  if (value && !formData.tags.includes(value) && formData.tags.length < 5) {
    formData.tags.push(value);
  }
  tagInput.visible = false;
  tagInput.value = '';
};

// 操作处理
const handleCancel = () => {
  router.back();
};

const handleSaveDraft = async () => {
  if (!formRef.value) return;

  try {
    await formRef.value.validate();
    draftLoading.value = true;

    formData.stateCode = 1; // 草稿状态

    if (isEdit.value) {
      await updateItem(itemId.value, formData);
      ElMessage.success('草稿保��成功');
    } else {
      const newItemId = await createItem(formData);
      ElMessage.success('草稿保存成功');
      router.replace(`/item/${newItemId}/edit`);
    }
  } catch (error) {
    console.error('保存草稿失败:', error);
    ElMessage.error('保存草稿失败');
  } finally {
    draftLoading.value = false;
  }
};

const handlePublish = async () => {
  if (!formRef.value) return;

  try {
    await formRef.value.validate();

    if (formData.images.length === 0) {
      ElMessage.error('请至少上传一张商品图片');
      return;
    }

    publishLoading.value = true;
    formData.stateCode = 2; // 在售状态

    if (isEdit.value) {
      await updateItem(itemId.value, formData);
      ElMessage.success('商品更新成功');
      router.push(`/item/${itemId.value}`);
    } else {
      const newItemId = await createItem(formData);
      ElMessage.success('商品发布成功');
      router.push(`/item/${newItemId}`);
    }
  } catch (error) {
    console.error('发布失败:', error);
    ElMessage.error('发布失败');
  } finally {
    publishLoading.value = false;
  }
};

// 页面加载时初始化
onMounted(() => {
  loadItemData();
});
</script>

<style scoped>
.item-edit-page {
  min-height: 100vh;
  background-color: #f5f5f5;
}

.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 20px;
}

.page-header {
  text-align: center;
  margin-bottom: 32px;
  padding: 32px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.page-header h1 {
  font-size: 28px;
  font-weight: 600;
  color: #2c3e50;
  margin-bottom: 8px;
}

.page-header p {
  color: #7f8c8d;
  margin: 0;
}

.edit-content {
  background: white;
  border-radius: 12px;
  padding: 32px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.form-section {
  margin-bottom: 32px;
  padding-bottom: 24px;
  border-bottom: 1px solid #f0f0f0;
}

.form-section:last-of-type {
  border-bottom: none;
  margin-bottom: 0;
}

.form-section h3 {
  font-size: 18px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 20px 0;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;
}

.image-upload-item {
  margin-bottom: 32px;
}

.upload-tip {
  color: #7f8c8d;
  font-size: 14px;
  text-align: center;
  margin-top: 8px;
}

.tags-wrapper {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: 8px;
}

.tag-item {
  margin: 0;
}

.tag-input {
  width: 120px;
}

.tag-button {
  border-style: dashed;
  height: 32px;
}

.form-actions {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-top: 32px;
  padding-top: 24px;
  border-top: 1px solid #f0f0f0;
}

.form-actions .el-button {
  min-width: 120px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .form-row {
    grid-template-columns: 1fr;
  }

  .container {
    padding: 16px;
  }

  .edit-content {
    padding: 24px 16px;
  }
}
</style>
