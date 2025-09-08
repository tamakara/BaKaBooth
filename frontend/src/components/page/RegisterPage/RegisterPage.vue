<template>
  <RegisterPageLayout>
    <template #header>
      <div class="register-title">
        <h1>用户注册</h1>
        <p class="register-subtitle">加入 BaKaBooth 数字商品交易平台</p>
      </div>
    </template>
    <template #main>
      <div class="register-form">
        <el-form
            ref="ruleFormRef"
            :model="registerForm"
            :rules="rules"
            label-position="top"
            size="large"
        >
          <el-form-item prop="phone" class="form-item">
            <el-input
                v-model="registerForm.phone"
                type="text"
                :prefix-icon="DevicePhoneMobileIcon"
                placeholder="请输入手机号"
                clearable
                maxlength="11"
                class="register-input"
            />
          </el-form-item>

          <el-form-item prop="password" class="form-item">
            <el-input
                v-model="registerForm.password"
                type="password"
                :prefix-icon="LockClosedIcon"
                placeholder="请输入密码"
                clearable
                show-password
                class="register-input"
            />
          </el-form-item>

          <el-form-item prop="confirmPassword" class="form-item">
            <el-input
                v-model="registerForm.confirmPassword"
                type="password"
                :prefix-icon="LockClosedIcon"
                placeholder="请确认密码"
                clearable
                show-password
                class="register-input"
            />
          </el-form-item>

          <el-form-item class="button-item">
            <el-button
                type="primary"
                size="large"
                class="register-button"
                @click="handleRegisterClick(ruleFormRef)"
                :loading="registerLoading"
            >
              {{ registerLoading ? '注册中...' : '注册' }}
            </el-button>
          </el-form-item>

          <el-form-item class="button-item">
            <el-button
                plain
                size="large"
                class="login-button"
                @click="handleLoginClick"
            >
              已有账号？立即登录
            </el-button>
          </el-form-item>

          <div class="back-link">
            <el-button
                text
                @click="handleBackToHomeClick"
                class="back-button"
            >
              <el-icon class="mr-1">
                <ArrowLeftIcon/>
              </el-icon>
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
  </RegisterPageLayout>
</template>

<script setup lang="ts">
import RegisterPageLayout from "@/components/page/RegisterPage/RegisterPageLayout.vue";
import {reactive, ref} from "vue";
import {useRouter} from "vue-router";
import type {FormInstance, FormRules} from "element-plus";
import {ElMessage} from "element-plus";
import {
  DevicePhoneMobileIcon,
  LockClosedIcon,
  ArrowLeftIcon,
} from "@heroicons/vue/24/outline";
import {useUserStore} from "@/stores/user.ts";
import type {RegisterFormDTO} from "@/types/UserTypes";

const router = useRouter()
const userStore = useUserStore()

const ruleFormRef = ref<FormInstance>()
const registerLoading = ref(false)
const registerForm = ref<RegisterFormDTO>({
  phone: '',
  password: '',
  confirmPassword: ''
});

// 确认密码验证器
const validateConfirmPassword = (rule: any, value: any, callback: any) => {
  if (value === '') {
    callback(new Error('请确认密码'))
  } else if (value !== registerForm.value.password) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = reactive<FormRules<RegisterFormDTO>>({
  phone: [
    {required: true, message: '请输入手机号', trigger: 'blur'},
    {pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur'}
  ],
  password: [
    {required: true, message: '请输入密码', trigger: 'blur'},
    {min: 6, max: 20, message: '密码长度为6-20位', trigger: 'blur'},
    {pattern: /^(?=.*[a-zA-Z])(?=.*\d)/, message: '密码必须包含字母和数字', trigger: 'blur'}
  ],
  confirmPassword: [
    {required: true, message: '请确认密码', trigger: 'blur'},
    {validator: validateConfirmPassword, trigger: 'blur'}
  ],
})

async function handleRegisterClick(formEl: FormInstance | undefined) {
  if (!formEl) return
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      try {
        registerLoading.value = true
        await userStore.register(registerForm.value)

        ElMessage.success('注册成功！')
        router.push({name: 'home'})
      } catch (error) {
        console.error('注册失败', error)
        ElMessage.error('注册失败，请重试')
      } finally {
        registerLoading.value = false
      }
    } else {
      console.log('注册表单校验失败', fields)
    }
  })


}

function handleLoginClick() {
  router.push({name: 'login'})
}

function handleBackToHomeClick() {
  router.push({name: 'home'})
}
</script>

<style scoped>
.register-title h1 {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  margin: 0 0 8px 0;
}

.register-subtitle {
  font-size: 14px;
  color: #909399;
  margin: 0;
}

.register-form {
  padding: 32px;
}

.form-item {
  margin-bottom: 20px;
}

.button-item {
  margin-bottom: 16px;
}

.register-input {
  height: 48px;
}

.register-button {
  width: 100%;
  height: 48px;
  font-size: 16px;
  font-weight: 500;
  background: linear-gradient(135deg, #409eff 0%, #67c23a 100%);
  border: none;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.register-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.3);
}

.login-button {
  width: 100%;
  height: 40px;
  font-size: 14px;
  color: #606266;
  border: 1px solid #dcdfe6;
  border-radius: 8px;
  transition: all 0.3s ease;
}

.login-button:hover {
  color: #409eff;
  border-color: #c6e2ff;
  background-color: #ecf5ff;
}

.back-link {
  margin-top: 16px;
  text-align: center;
}

.back-button {
  color: #909399;
  font-size: 14px;
  padding: 0;
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
</style>