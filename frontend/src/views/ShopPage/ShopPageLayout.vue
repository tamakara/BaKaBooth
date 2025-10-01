<template>
  <BaseLayout>
    <template #header>
      <Banner />
    </template>
    <template #main>
      <div class="shop-page-layout">
        <div class="shop-container">
          <div class="shop-sidebar">
            <slot name="left-sidebar" />
          </div>
          <div class="shop-content">
            <slot name="content" />
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
.shop-page-layout {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
}

.shop-container {
  display: flex;
  gap: 24px;
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.shop-sidebar {
  width: 300px;
  flex-shrink: 0;
  background: #f8f9fa;
  padding: 24px;
  border-right: 1px solid #e4e7ed;
}

.shop-content {
  flex: 1;
  padding: 24px;
  background: white;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .shop-page-layout {
    padding: 16px;
  }

  .shop-container {
    flex-direction: column;
    gap: 0;
  }

  .shop-sidebar {
    width: 100%;
    border-right: none;
    border-bottom: 1px solid #e4e7ed;
    padding: 16px;
  }

  .shop-content {
    padding: 16px;
  }
}
</style>
