<template>
  <LoginPageLayout>
    <template #header>
      <div class="login-title">
        <h1>用户登录</h1>
        <p class="login-subtitle">欢迎回到 BaKaBooth</p>
      </div>
    </template>
    <template #main>
      <div class="login-form">
        <el-form
          ref="ruleFormRef"
          :model="loginForm"
          :rules="rules"
          label-position="top"
          size="large"
        >
          <el-form-item prop="phone" class="form-item">
            <el-input
              v-model="loginForm.phone"
              type="text"
              :prefix-icon="DevicePhoneMobileIcon"
              placeholder="请输入手机号"
              clearable
              maxlength="11"
              class="login-input"
            />
          </el-form-item>

          <el-form-item prop="password" class="form-item">
            <el-input
              v-model="loginForm.password"
              type="password"
              :prefix-icon="LockClosedIcon"
              placeholder="请输入密码"
              clearable
              show-password
              class="login-input"
            />
          </el-form-item>

          <el-form-item class="button-item">
            <el-button
              type="primary"
              size="large"
              class="login-button"
              @click="handleLoginClick(ruleFormRef)"
              :loading="loginLoading"
            >
              {{ loginLoading ? '登录中...' : '登录' }}
            </el-button>
          </el-form-item>

          <el-form-item class="button-item">
            <el-button
              plain
              size="large"
              class="register-button"
              @click="handleRegisterClick"
            >
              还没有账号？立即注册
            </el-button>
          </el-form-item>

          <div class="back-link">
            <el-button
              text
              @click="handleBackToHomeClick"
              class="back-button"
            >
              <el-icon class="mr-1"><ArrowLeftIcon /></el-icon>
              返回首页
            </el-button>
          </div>
        </el-form>
      </div>
    </template>
    <template #footer>
      <div class="brand-footer">
        <h3>BaKaBooth</h3>
        <p>数字商品交易平台</p>
      </div>
    </template>
  </LoginPageLayout>
</template>

<script setup lang="ts">
import LoginPageLayout from "@/components/page/LoginPage/LoginPageLayout.vue";
import {reactive, ref} from "vue";
import {useRouter} from "vue-router";
import type {LoginForm} from "@/types/UserTypes";
import type {FormInstance, FormRules} from "element-plus";
import {useUserStore} from "@/stores/user.ts";
import {DevicePhoneMobileIcon, LockClosedIcon, ArrowLeftIcon} from "@heroicons/vue/24/outline";

const router = useRouter()
const userStore = useUserStore()

const ruleFormRef = ref<FormInstance>()
const loginLoading = ref(false)
const loginForm = ref<LoginForm>({
  phone: '',
  password: ''
});

const rules = reactive<FormRules<LoginForm>>({
  phone: [
    {required: true, message: '请输入手机号', trigger: 'blur'},
    {pattern: /^1[3-9]\d{8}$/, message: '请输入正确的手机号', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, message: '密码长度至少6位', trigger: 'blur'}
  ],
})

async function handleLoginClick(formEl: FormInstance | undefined) {
  if (!formEl) return
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      try {
        loginLoading.value = true
        await userStore.login(loginForm.value)
        handleBackToHomeClick()
      } catch (error) {
        console.error('登录失败', error)
      } finally {
        loginLoading.value = false
      }
    } else {
      console.log('登录表单校验失败', fields)
    }
  })
}

function handleRegisterClick() {
  router.push({name: 'register'})
}

function handleBackToHomeClick() {
  router.push({name: 'home'})
}
</script>

<style scoped>
.login-title h1 {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 8px 0;
}

.login-subtitle {
  font-size: 14px;
  color: #909399;
  margin: 0;
}

.login-form {
  padding: 32px;
}

.form-item {
  margin-bottom: 20px;
}

.button-item {
  margin-bottom: 16px;
}

.login-input {
  height: 48px;
}

.login-button {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 500;
  background: linear-gradient(135deg, #409eff 0%, #67c23a 100%);
  border: none;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.login-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.register-button {
  width: 100%;
  height: 40px;
  font-size: 14px;
  color: #606266;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.register-button:hover {
  color: #409eff;
  border-color: #409eff;
  background: #ecf5ff;
}

.back-link {
  text-align: center;
  margin-top: 24px;
  padding-top: 16px;
  border-top: 1px solid #f0f0f0;
}

.back-button {
  font-size: 14px;
  color: #909399;
  transition: color 0.3s ease;
}

.back-button:hover {
  color: #409eff;
}

.brand-footer h3 {
  font-size: 20px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 4px 0;
}

.brand-footer p {
  font-size: 12px;
  color: #909399;
  margin: 0;
}

.mr-1 {
  margin-right: 4px;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-form {
    padding: 24px 16px;
  }

  .login-title h1 {
    font-size: 24px;
  }
}
</style>