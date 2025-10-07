<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="user-edit-page">
        <div class="container">
          <div class="page-header">
            <h1>编辑个人资料</h1>
            <p>管理您的个人信息和账户设置</p>
          </div>

          <div v-if="loading" class="loading-content">
            <el-skeleton animated>
              <template #template>
                <el-skeleton-item variant="text" style="width: 100%; height: 40px; margin-bottom: 20px;"/>
                <el-skeleton-item variant="rect" style="width: 100%; height: 300px;"/>
              </template>
            </el-skeleton>
          </div>

          <div v-else class="edit-content">
            <!-- 头像设置 -->
            <div class="section">
              <h2>头像设置</h2>
              <div class="avatar-section">
                <div class="current-avatar">
                  <el-avatar :size="120" :src="userInfo.avatarUrl">
                    {{ userInfo.username?.[0] }}
                  </el-avatar>
                </div>
                <div class="avatar-upload">
                  <el-upload
                      action="http://localhost:8080/file/upload"
                      :headers="uploadHeaders"
                      :show-file-list="false"
                      :on-success="handleAvatarSuccess"
                      :before-upload="beforeAvatarUpload"
                      accept=".jpg,.jpeg,.png"
                  >
                    <el-button type="primary">更换头像</el-button>
                  </el-upload>
                  <p class="upload-tip">支持 JPG、PNG 格式，文件大小不超过 2MB</p>
                </div>
              </div>
            </div>

            <!-- 基本信息 -->
            <div class="section">
              <h2>基本信息</h2>
              <el-form
                  ref="profileFormRef"
                  :model="profileForm"
                  :rules="profileRules"
                  label-position="top"
                  class="profile-form"
              >
                <div class="form-row">
                  <el-form-item label="用户名" prop="username" class="form-item">
                    <el-input
                        v-model="profileForm.username"
                        placeholder="请输入用户名"
                        maxlength="20"
                        show-word-limit
                    />
                  </el-form-item>
                  <el-form-item label="手机号" prop="phone" class="form-item">
                    <el-input
                        v-model="profileForm.phone"
                        placeholder="手机号"
                        disabled
                    />
                  </el-form-item>
                </div>

                <el-form-item label="个人简介" prop="introduction" class="form-item">
                  <el-input
                      v-model="profileForm.introduction"
                      type="textarea"
                      :rows="4"
                      placeholder="介绍一下自己..."
                      maxlength="200"
                      show-word-limit
                  />
                </el-form-item>

                <el-form-item label="个人公告" prop="announcement" class="form-item">
                  <el-input
                      v-model="profileForm.announcement"
                      type="textarea"
                      :rows="3"
                      placeholder="发布个人公告，如交易说明、联系方式等"
                      maxlength="300"
                      show-word-limit
                  />
                </el-form-item>

                <el-form-item>
                  <el-button
                      type="primary"
                      @click="updateProfile"
                      :loading="updateLoading"
                  >
                    保存个人信息
                  </el-button>
                  <el-button @click="cancelEdit">取消</el-button>
                </el-form-item>
              </el-form>
            </div>

            <!-- 账户统计 -->
            <div class="section">
              <h2>账户数据</h2>
              <div class="stats-grid">
                <div class="stat-card">
                  <div class="stat-number">{{ stats.totalItems }}</div>
                  <div class="stat-label">发布商品</div>
                </div>
                <div class="stat-card">
                  <div class="stat-number">{{ stats.onSaleItems }}</div>
                  <div class="stat-label">在售商品</div>
                </div>
                <div class="stat-card">
                  <div class="stat-number">{{ userInfo.followers }}</div>
                  <div class="stat-label">关注者</div>
                </div>
                <div class="stat-card">
                  <div class="stat-number">{{ stats.totalOrders }}</div>
                  <div class="stat-label">交易订单</div>
                </div>
              </div>
            </div>

            <!-- 快捷操作 -->
            <div class="section">
              <h2>快捷操作</h2>
              <div class="quick-actions">
                <el-button type="primary" @click="goToItemManage">
                  <ShoppingBagIcon class="button-icon"/>
                  商品管理
                </el-button>
                <el-button type="success" @click="createNewItem">
                  <PlusIcon class="button-icon"/>
                  发布商品
                </el-button>
                <el-button @click="viewMyProfile">
                  <UserIcon class="button-icon"/>
                  查看我的主页
                </el-button>
                <el-button @click="goToFavorites">
                  <HeartIcon class="button-icon"/>
                  我的收藏
                </el-button>
              </div>
            </div>

            <!-- 账户安全 -->
            <div class="section">
              <h2>账户安全</h2>
              <div class="security-section">
                <div class="security-item">
                  <div class="security-info">
                    <h4>修改密码</h4>
                    <p>定期修改密码，保护账户安全</p>
                  </div>
                  <el-button @click="showChangePassword">修改密码</el-button>
                </div>
                <div class="security-item">
                  <div class="security-info">
                    <h4>账户注销</h4>
                    <p>注销后将无法恢复，请谨慎操作</p>
                  </div>
                  <el-button type="danger" @click="showDeleteAccount">注销账户</el-button>
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
import BaseLayout from "@/components/layout/BaseLayout.vue";
import Banner from "@/components/business/Banner.vue";
import {ref, reactive, onMounted, computed} from "vue";
import {useRouter} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";
import type {FormInstance, FormRules, UploadProps} from "element-plus";
import {ShoppingBagIcon, PlusIcon, UserIcon, HeartIcon} from "@heroicons/vue/24/outline";
import {getUserVO} from "@/services/user.ts";
import {getItemPageVO} from "@/services/item.ts";
import {useUserStore} from "@/stores/user";
import type {UserVO} from "@/types/user";

const router = useRouter();
const userStore = useUserStore();

// 响应式数据
const loading = ref(true);
const updateLoading = ref(false);
const userInfo = ref<UserVO>({} as UserVO);
const profileFormRef = ref<FormInstance>();

// 表单数据
const profileForm = reactive({
  username: '',
  phone: '',
  introduction: '',
  announcement: ''
});

// 统计数据
const stats = reactive({
  totalItems: 0,
  onSaleItems: 0,
  totalOrders: 0
});

// 表单验证规则
const profileRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度为 2 到 20 个字符', trigger: 'blur' }
  ]
};

// 计算属性
const uploadHeaders = computed(() => ({
  Authorization: 'Bearer ' + userStore.token
}));

// 加载用户信息
const loadUserInfo = async () => {
  try {
    userInfo.value = await getUserVO();

    // 填充表单数据
    profileForm.username = userInfo.value.username || '';
    profileForm.phone = userInfo.value.phone || '';
    profileForm.introduction = userInfo.value.introduction || '';
    profileForm.announcement = userInfo.value.announcement || '';
  } catch (error) {
    console.error('加载用户信息失败:', error);
    ElMessage.error('加载用户信息失败');
  }
};

// 加载统计数据
const loadStats = async () => {
  try {
    // 获取所有商品
    const allItems = await getItemVOList(userInfo.value.id, 0);
    stats.totalItems = allItems.length;

    // 获取在售商品
    const onSaleItems = await getItemVOList(userInfo.value.id, 2);
    stats.onSaleItems = onSaleItems.length;

    // TODO: 获取订单统计
    stats.totalOrders = 0;
  } catch (error) {
    console.error('加载统计数据失败:', error);
  }
};

// 加载所有数据
const loadData = async () => {
  try {
    loading.value = true;
    await loadUserInfo();
    await loadStats();
  } finally {
    loading.value = false;
  }
};

// 更新个人资料
const updateProfile = async () => {
  if (!profileFormRef.value) return;

  try {
    await profileFormRef.value.validate();
    updateLoading.value = true;

    // TODO: 调用更新用户信息的API
    ElMessage.success('保存成功！');

    // 更新store中的用户信息
    await userStore.fetchUserInfo();
  } catch (error) {
    console.error('更新失败:', error);
    ElMessage.error('保存失败，请重试');
  } finally {
    updateLoading.value = false;
  }
};

// 取消编辑
const cancelEdit = () => {
  router.back();
};

// 头像上传前检查
const beforeAvatarUpload: UploadProps['beforeUpload'] = (rawFile) => {
  if (!['image/jpeg', 'image/jpg', 'image/png'].includes(rawFile.type)) {
    ElMessage.error('头像只能是 JPG/PNG 格式!');
    return false;
  }
  if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('头像大小不能超过 2MB!');
    return false;
  }
  return true;
};

// 头像上传成功
const handleAvatarSuccess = (response: any) => {
  // TODO: 更新用户头像URL
  ElMessage.success('头像更新成功！');
  userStore.fetchUserInfo();
  loadUserInfo();
};

// 快捷操作
const goToItemManage = () => {
  router.push('/item/manage');
};

const createNewItem = () => {
  router.push('/item/new/edit');
};

const viewMyProfile = () => {
  router.push(`/user/${userInfo.value.id}`);
};

const goToFavorites = () => {
  router.push('/user/favorites');
};

// 安全操作
const showChangePassword = () => {
  ElMessage.info('修改密码功能开发中');
};

const showDeleteAccount = async () => {
  try {
    await ElMessageBox.confirm(
        '确定要注销账户吗？此操作无法撤销，所有数据将被永久删除。',
        '注销账户',
        {
          confirmButtonText: '确定注销',
          cancelButtonText: '取消',
          type: 'warning',
          dangerouslyUseHTMLString: true
        }
    );

    ElMessage.info('账户注销功能开发中');
  } catch {
    // 用户取消
  }
};

// 页面加载时获取数据
onMounted(() => {
  loadData();
});
</script>

<style scoped>
.user-edit-page {
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

.loading-content {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.edit-content {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.section {
  background: white;
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

.section h2 {
  font-size: 20px;
  font-weight: 600;
  color: #2c3e50;
  margin: 0 0 20px 0;
  padding-bottom: 12px;
  border-bottom: 2px solid #f0f0f0;
}

.avatar-section {
  display: flex;
  align-items: center;
  gap: 24px;
}

.avatar-upload {
  flex: 1;
}

.upload-tip {
  margin-top: 8px;
  font-size: 14px;
  color: #7f8c8d;
}

.profile-form {
  max-width: 600px;
}

.form-row {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 16px;
}

.stats-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 20px;
}

.stat-card {
  text-align: center;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e9ecef;
}

.stat-number {
  font-size: 32px;
  font-weight: 700;
  color: #409eff;
  margin-bottom: 8px;
}

.stat-label {
  font-size: 14px;
  color: #7f8c8d;
  font-weight: 500;
}

.quick-actions {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
  gap: 16px;
}

.security-section {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.security-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  background: #f8f9fa;
  border-radius: 8px;
  border: 1px solid #e9ecef;
}

.security-info h4 {
  margin: 0 0 4px 0;
  color: #2c3e50;
}

.security-info p {
  margin: 0;
  color: #7f8c8d;
  font-size: 14px;
}

.button-icon {
  width: 16px;
  height: 16px;
  margin-right: 4px;
}
</style>
