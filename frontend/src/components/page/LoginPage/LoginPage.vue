<template>
  <LoginPageLayout>
    <template #header>
      <el-text style="font-size: 26px">用户登录</el-text>
    </template>
    <template #main>
      <div class="login-form">
        <el-form>
          <el-form-item>
            <el-input
                size="large"
                type="text"
                v-model="username"
                placeholder="用户名或电子邮箱"
                :prefix-icon="User"
            />
          </el-form-item>
          <el-form-item>
            <el-input
                size="large"
                type="password"
                v-model="password"
                placeholder="密码"
                :prefix-icon="Lock"
            />
          </el-form-item>
          <el-form-item>
            <el-button
                color="#2c9ba6"
                type="primary"
                @click="handleLoginClick"
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
import {ref} from "vue";
import {User, Lock} from "@element-plus/icons-vue"
import {useRouter} from "vue-router";

const router = useRouter()

const username = ref('')
const password = ref('')

function handleLoginClick() {

}

function handleRegisterClick() {

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

.login-input {

}
</style>