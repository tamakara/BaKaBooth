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
              <!-- 商品图片（仅上移，其余保持原布局思想） -->
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
                  <el-icon class="upload-icon"><PlusIcon/></el-icon>
                  <template #tip>
                    <div class="upload-tip">第一张图片为主图，最多 5 张（建议 1:1 且 < 10MB）</div>
                  </template>
                </el-upload>
              </el-form-item>

              <!-- 商品基本信息 -->
              <div class="form-section">
                <h3>商品信息</h3>
                <el-form-item label="商品名称" prop="name" class="form-item">
                  <el-input v-model="formData.name" placeholder="请输入商品名称" maxlength="50" show-word-limit />
                </el-form-item>

                <!-- 优化后的价格 & 配送方式（垂直排布） -->
                <div class="price-delivery-group">
                  <div class="price-block">
                    <el-form-item label="价格 (元)" prop="price" class="form-item no-bottom">
                      <el-input-number v-model="formData.price" :min="0.01" :max="99999" :precision="2" placeholder="请输入价格" class="full-width" />
                    </el-form-item>
                    <p class="help-text">建议参考同类在售价格，后续可再次编辑调整。</p>
                  </div>

                  <div class="delivery-block">
                    <el-form-item label="配送方式" prop="deliveryMethodCode" class="form-item no-bottom">
                      <el-radio-group v-model="formData.deliveryMethodCode" class="delivery-radios vertical">
                        <el-radio :value="0" class="delivery-line">自取<span class="radio-desc">（当面交付）</span></el-radio>
                        <el-radio :value="1" class="delivery-line">包邮<span class="radio-desc">（卖家承担运费）</span></el-radio>
                        <el-radio :value="2" class="delivery-line fixed-line">
                          <div class="fixed-line-inner">
                            <span>固定邮费</span>
                            <el-input-number
                              ref="postageInputRef"
                              v-model="formData.postage"
                              :min="0"
                              :max="9999"
                              :precision="2"
                              placeholder="邮费"
                              class="inline-postage-input"
                              :disabled="formData.deliveryMethodCode !== 2"
                            />
                            <span class="radio-desc" v-if="formData.deliveryMethodCode !== 2">（选择后可填写）</span>
                          </div>
                        </el-radio>
                      </el-radio-group>
                    </el-form-item>
                    <p class="help-text">自取：买家上门；包邮：你承担运费；固定邮费：买家支付设置金额。</p>
                  </div>
                </div>
              </div>

              <!-- 商品描述 -->
              <div class="form-section">
                <h3>商品描述</h3>
                <el-form-item prop="description" class="form-item">
                  <el-input
                    v-model="formData.description"
                    type="textarea"
                    :rows="6"
                    placeholder="请描述：成色 / 购买时间 / 使用频率 / 功能状态 / 配件情况 等（至少10个字）"
                    maxlength="500"
                    show-word-limit
                    resize="none"
                  />
                </el-form-item>
              </div>

              <!-- 操作按钮 -->
              <div class="form-actions">
                <el-button @click="handleCancel">取消</el-button>
                <el-button @click="handleSaveDraft" :loading="draftLoading">保存草稿</el-button>
                <el-button type="primary" @click="handlePublish" :loading="publishLoading">{{ isEdit ? '保存并上架' : '立即发布' }}</el-button>
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
import {ref, reactive, computed, onMounted, watch} from "vue";
import {useRoute, useRouter} from "vue-router";
import {ElMessage} from "element-plus";
import type {FormInstance, FormRules, UploadUserFile, UploadProps, FormItemRule} from "element-plus";
import {createItem, updateItem, getItemVO} from "@/services/item.ts";
import type {ItemEditFormVO, ItemVO} from "@/types/item.d.ts";
import {useUserStore} from "@/stores/user";
import {Plus as PlusIcon} from "@element-plus/icons-vue";

const route = useRoute();
const router = useRouter();
const userStore = useUserStore();

const formRef = ref<FormInstance>();
const imageFiles = ref<UploadUserFile[]>([]);
const draftLoading = ref(false);
const publishLoading = ref(false);
const postageInputRef = ref();

// 表单数据（已移除 tags）
const formData = reactive<ItemEditFormVO>({
  stateCode: 1,
  deliveryMethodCode: 0,
  name: '',
  description: '',
  price: 0,
  postage: 0,
  images: []
});

const itemId = computed(() => route.params.id as string);
const isEdit = computed(() => itemId.value && itemId.value !== 'new');
const uploadHeaders = computed(() => ({ Authorization: 'Bearer ' + userStore.token }));

// 自定义验证
const validatePostage: FormItemRule['validator'] = (_rule, value, callback) => {
  if (formData.deliveryMethodCode === 2) {
    if (value === null || value === undefined || value < 0) {
      callback(new Error('请输入有效邮费'));
      return;
    }
  } else if (formData.deliveryMethodCode !== 2 && formData.postage !== 0) {
    // 自动归零（更人性化）
    formData.postage = 0;
  }
  callback();
};

const rules: FormRules = {
  name: [
    { required: true, message: '请输入商品名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度 2-50 字', trigger: 'blur' }
  ],
  price: [
    { required: true, message: '请输入价格', trigger: 'change' },
    { type: 'number', min: 0.01, message: '价格需大于 0', trigger: 'change' }
  ],
  description: [
    { required: true, message: '请输入商品描述', trigger: 'blur' },
    { min: 10, message: '至少 10 个字', trigger: 'blur' }
  ],
  deliveryMethodCode: [
    { required: true, message: '请选择配送方式', trigger: 'change' }
  ],
  postage: [ { validator: validatePostage, trigger: 'blur' } ]
};

const resetFormData = () => {
  formData.stateCode = 1;
  formData.deliveryMethodCode = 0;
  formData.name = '';
  formData.description = '';
  formData.price = 0;
  formData.postage = 0;
  formData.images = [];
  imageFiles.value = [];
};

const loadItemData = async () => {
  try {
    const itemData: ItemVO | any = await getItemVO(itemId.value);
    formData.name = itemData.name;
    formData.price = itemData.price;
    formData.description = itemData.description;
    formData.postage = Number(itemData.postage) || 0;
    formData.stateCode = itemData.stateCode ?? 1;
    formData.deliveryMethodCode = itemData.deliveryMethodCode ?? 0;
    formData.images = (itemData.images || []).map((_url: string, index: number) => index + 1);
    imageFiles.value = (itemData.images || []).map((url: string, index: number) => ({
      name: `image_${index + 1}`,
      url,
      uid: index + 1
    }));
  } catch (e) {
    console.error(e);
    ElMessage.error('加载商品数据失败');
    router.push('/');
  }
};

const initPageData = async () => {
  if (isEdit.value) await loadItemData(); else resetFormData();
};

const handleImageSuccess: UploadProps['onSuccess'] = (response, file) => {
  file.uid = response;
  formData.images.push(response);
  ElMessage.success('上传成功');
};
const handleImageRemove: UploadProps['onRemove'] = (file) => {
  const idx = formData.images.findIndex(id => id === file.uid);
  if (idx > -1) formData.images.splice(idx, 1);
};

const handleCancel = () => router.back();

const handleSaveDraft = async () => {
  if (!formRef.value) return;
  try {
    await formRef.value.validate();
    draftLoading.value = true;
    formData.stateCode = 1;
    if (isEdit.value) {
      await updateItem(itemId.value, formData);
      ElMessage.success('草稿已保存');
    } else {
      const newId = await createItem(formData);
      ElMessage.success('草稿已保存');
      router.replace(`/item/${newId}/edit`);
    }
  } catch {
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
      ElMessage.error('请至少上传一张图片');
      return;
    }
    publishLoading.value = true;
    formData.stateCode = 2;
    if (isEdit.value) {
      await updateItem(itemId.value, formData);
      ElMessage.success('商品已更新');
      router.push(`/item/${itemId.value}`);
    } else {
      const newId = await createItem(formData);
      ElMessage.success('发布成功');
      router.push(`/item/${newId}`);
    }
  } catch {
    ElMessage.error('发布失败');
  } finally {
    publishLoading.value = false;
  }
};

watch(() => formData.deliveryMethodCode, (val) => {
  if (val !== 2) {
    formData.postage = 0;
  } else {
    requestAnimationFrame(() => postageInputRef.value?.focus?.());
  }
});

onMounted(() => initPageData());
</script>

<style scoped>
/* 基础整体 */
.item-edit-page { min-height: 100vh; background: #f5f6f7; }
.container { max-width: 800px; margin: 0 auto; padding: 20px; }
.page-header { text-align: center; margin-bottom: 32px; padding: 32px 20px; background:#fff; border-radius:12px; box-shadow:0 2px 12px rgba(0,0,0,.05); }
.page-header h1 { font-size:28px; font-weight:600; margin:0 0 8px; }
.page-header p { margin:0; color:#7f8c8d; }

.edit-content { background:#fff; border-radius:12px; padding:32px; box-shadow:0 2px 12px rgba(0,0,0,.05); }

/* 区块 */
.form-section { margin-bottom:32px; padding-bottom:24px; border-bottom:1px solid #f0f0f0; }
.form-section:last-of_type { border-bottom:none; margin-bottom:12px; }
.form-section h3 { font-size:18px; font-weight:600; margin:0 0 20px; color:#2c3e50; }

/* 行布局 */
.form-row { display:grid; grid-template-columns: 1fr 1fr; gap:20px; }
.form-item { margin-bottom:20px; }

/* 上传 */
.image-upload-item { margin-bottom:24px; }
.image-upload-item :deep(.el-upload) { --el-upload-picture-card-size:118px; }
.upload-tip { color:#909399; font-size:12px; line-height:1.4; margin-top:4px; text-align:center; }

/* 按钮 */
.form-actions { display:flex; justify-content:center; gap:16px; margin-top:24px; padding-top:24px; border-top:1px solid #f0f0f0; }
.form-actions .el-button { min-width:120px; }

/* 新增样式 */
.price-delivery-group { display:flex; flex-direction:column; gap:20px; margin-top:4px; }
.price-delivery-group .full-width { width:100%; }
.no-bottom { margin-bottom:4px; }
.help-text { margin:4px 0 0; font-size:12px; line-height:1.5; color:#9098a0; }
.help-text.small { margin-top:0; font-size:11px; color:#a0a6ad; }
.delivery-radios { display:flex; gap:28px; flex-wrap:wrap; }
.delivery-radios.vertical { flex-direction: column; align-items: flex-start; gap:12px; }
.delivery-line { display:flex; align-items:center; gap:6px; line-height:1.4; }
.fixed-line-inner { display:flex; align-items:center; gap:12px; flex-wrap:wrap; }
.inline-postage-input { width:180px; }
.radio-desc { font-size:12px; color:#9098a0; }

/* 移除旧邮费块样式不再使用 */
.postage-block, .inner-postage-item, .postage-input { display:none; }
.fade-slide-enter-active, .fade-slide-leave-active { transition: all .18s ease; }
.fade-slide-enter-from, .fade-slide-leave-to { opacity:0; transform: translateY(-4px); }

@media (max-width: 768px) {
  .form-row { grid-template-columns:1fr; }
  .edit-content { padding:24px 16px; }
  .delivery-radios { gap:16px; }
  .postage-input { width:100%; }
}
</style>
