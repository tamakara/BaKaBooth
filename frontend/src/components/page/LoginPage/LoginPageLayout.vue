<template>
  <BaseLayout>
    <template #main>
      <div class="login-page-layout">
        <div class="login-container">
          <div class="login-header">
            <slot name="header"/>
          </div>
          <div class="login-main">
            <slot name="main"/>
          </div>
          <div class="login-footer">
            <slot name="footer"/>
          </div>
        </div>
      </div>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/layout/BaseLayout.vue";
</script>

<style scoped>
.login-page-layout {
  width: 100%;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 20px;
}

.login-container {
  width: 100%;
  max-width: 480px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  backdrop-filter: blur(10px);
}

.login-header {
  padding: 32px 32px 16px 32px;
  text-align: center;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-bottom: 1px solid #e4e7ed;
}

.login-main {
  padding: 0;
}

.login-footer {
  padding: 16px 32px 32px 32px;
  text-align: center;
  background: #f8f9fa;
  border-top: 1px solid #e4e7ed;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-page-layout {
    padding: 16px;
  }

  .login-container {
    max-width: 100%;
  }

  .login-header,
  .login-footer {
    padding: 24px 16px;
  }
}
</style>