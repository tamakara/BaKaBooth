<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="content-layout">
        <div class="content-title">
          <div class="page-title">店铺管理</div>
        </div>

        <!-- 店铺信息头部区域 -->
        <div class="header-section">
          <div class="header-card">
            <slot name="dashboard-header-left"/>
          </div>
        </div>

        <!-- 主要内容区域 -->
        <div class="content-section">
          <div class="content-left">
            <slot name="dashboard-pane-left"/>
          </div>
          <div class="content-right">
            <slot name="dashboard-pane-right"/>
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

/* 页面标题 */
.page-title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  padding: 20px 0;
  margin: 0;
}

/* 头部区域 */
.header-section {
  width: 1200px;
  margin-bottom: 24px;
}

.header-card {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  padding: 24px;
}

/* 内容区域 */
.content-section {
  width: 1200px;
  display: flex;
  gap: 24px;
}

.content-left {
  flex: 1;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  padding: 24px;
}

.content-right {
  width: 400px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  padding: 24px;
}
</style>