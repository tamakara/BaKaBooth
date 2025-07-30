<template>
  <BaseLayout>
    <template #main>
      <div class="login-page-layout">
        <div class="box">
          <div class="header">
            <slot name="header"/>
          </div>
          <div class="main">
            <slot name="main"/>
          </div>
        </div>
        <div class="footer">
          <slot name="footer"/>
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
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.box {
  width: 512px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.header {
  width: 100%;
  height: 60px;
  background-color: #ebedf2;
  display: flex;
  justify-content: center;
  align-items: center;
}

.main {
  width: 100%;
  background-color: #ffffff;
  display: flex;
  justify-content: center;
}

.footer {
  width: 100%;
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>