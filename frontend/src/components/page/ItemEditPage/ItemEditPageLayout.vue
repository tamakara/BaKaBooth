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
          <div class="form-container">
            <slot name="form"/>
          </div>
          <div class="action-container">
            <slot name="action"/>
          </div>
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

.form-container {
  max-width: 800px;
  margin: 0 auto;
  padding: 24px;
}

.action-container {
  display: flex;
  justify-content: center;
  gap: 20px;
  padding: 32px 24px;
  background: white;
  border-top: 1px solid #e4e7ed;
  margin-top: 24px;
}

/* 文件名截断样式 - 主要作用于图片上传 */
.form-container :deep(.el-upload-list__item-name) {
  max-width: 450px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.form-container :deep(.el-upload-list__item) {
  max-width: 100%;
}

/* 响应式调整文件名宽度 */
@media (max-width: 1200px) {
  .form-container :deep(.el-upload-list__item-name) {
    max-width: 350px;
  }
}

@media (max-width: 768px) {
  .title {
    font-size: 24px;
    padding: 24px 16px 20px 16px;
  }

  .content-wrapper {
    margin: 0 16px;
    border-radius: 8px;
  }

  .form-container {
    padding: 16px;
  }

  .form-container :deep(.el-upload-list__item-name) {
    max-width: 250px;
  }

  .action-container {
    flex-direction: column;
    gap: 12px;
    padding: 24px 16px;
  }
}
</style>