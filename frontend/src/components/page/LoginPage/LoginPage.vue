<template>
  <LoginPageLayout>
    <template #header>
      <el-text style="font-size: 26px">用户登录</el-text>
    </template>
    <template #main>
      <div class="login-form">
        <el-form
            ref="ruleFormRef"
            :model="loginForm"
            :rules="rules"
            status-icon
        >
          <el-form-item prop="phone">
            <el-input
                size="large"
                type="text"
                :prefix-icon="DevicePhoneMobileIcon"
                placeholder="手机号"
                v-model="loginForm.phone"
                clearable
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
                size="large"
                type="password"
                :prefix-icon="LockClosedIcon"
                placeholder="密码"
                v-model="loginForm.password"
                clearable
                show-password
            />
          </el-form-item>
          <el-form-item>
            <el-button
                color="#2c9ba6"
                type="primary"
                @click="handleLoginClick(ruleFormRef)"
                style="width: 100%;height: 40px"
            >
              登录
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button
                color="#28a745"
                type="primary"
                @click="handleRegisterClick"
                style="width: 100%;height: 40px"
            >
              注册
            </el-button>
          </el-form-item>
          <el-text
              tag="a"
              style="cursor:pointer"
              @click="handleBackToHomeClick"
          >
            ← 回到首页
          </el-text>
        </el-form>
      </div>

    </template>
    <template #footer>
      <el-text style="font-size: 18px">BaKaBooth</el-text>
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
import {DevicePhoneMobileIcon, LockClosedIcon} from "@heroicons/vue/24/outline";

const router = useRouter()
const userStore = useUserStore()

const ruleFormRef = ref<FormInstance>()
const loginForm = ref<LoginForm>({
  phone: '',
  password: ''
});

const rules = reactive<FormRules<LoginForm>>({
  phone: [{required: true, message: '请输入手机号', trigger: 'blur'},],
  password: [{required: true, message: '请输入密码', trigger: 'blur'},],
})

async function handleLoginClick(formEl: FormInstance | undefined) {
  if (!formEl) return
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      await userStore.login(loginForm.value)
      handleBackToHomeClick()
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
.login-form {
  padding: 40px;
  width: 100%;
}
</style>