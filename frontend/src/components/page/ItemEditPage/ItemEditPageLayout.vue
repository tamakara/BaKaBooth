<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <ContentLayout>
        <template #title>
          <RowLayout class="title">
            商品编辑
          </RowLayout>
        </template>
        <RowLayout class="content-wrapper">
          <RowLayout class="form-section">
            <slot name="form"/>
          </RowLayout>
          <RowLayout class="action-section">
            <slot name="action"/>
          </RowLayout>
        </RowLayout>
      </ContentLayout>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/layout/BaseLayout.vue";
import Banner from "@/components/common/Banner.vue";
import ContentLayout from "@/components/common/layout/ContentLayout.vue";
import RowLayout from "@/components/common/layout/RowLayout.vue";
</script>

<style scoped>
.title {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  padding: 32px 0 24px 0;
  text-align: center;
  border-bottom: 1px solid #e4e7ed;
  margin-bottom: 0;
}

.content-wrapper {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  margin: 0;
}

.form-section {
  padding: 0;
  min-height: 600px;
}

.action-section {
  padding: 0;
  margin-top: 0;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .title {
    font-size: 24px;
    padding: 24px 16px 20px 16px;
  }

  .content-wrapper {
    margin: 0 16px;
    border-radius: 8px;
  }
}
</style>