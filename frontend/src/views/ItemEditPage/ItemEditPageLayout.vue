<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="content-layout">
        <div class="content-title">
          <div class="title">
            商品编辑
          </div>
        </div>
        <div class="content-wrapper">
          <div class="form-container">
            <slot name="form"/>
          </div>
          <div class="action-container">
            <slot name="action"/>
          </div>
        </div>
      </div>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/layout/BaseLayout.vue";
import Banner from "@/components/business/Banner.vue";
</script>

<style scoped>
.content-layout {
  width: 944px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.content-title {
  width: 100%;
}

.title {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  padding: 32px 0 24px 0;
  text-align: center;
  border-bottom: 1px solid #e4e7ed;
  margin-bottom: 0;
  width: 100%;
}

.content-wrapper {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  margin: 0;
  width: 100%;
}

.form-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 24px;
}

.action-container {
  display: flex;
  justify-content: center;
  gap: 20px;
  padding: 16px;
  background-color: #f5f7fa;
  border-top: 1px solid #e4e7ed;
}
</style>