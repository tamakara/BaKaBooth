<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="home-content">
        <ContentLayout class="section-wrapper">
          <template #title>
            <div class="section-title">
              <slot name="section1-title"/>
            </div>
          </template>
          <RowLayout class="section-content">
            <slot name="section1"/>
          </RowLayout>
        </ContentLayout>

        <el-divider class="section-divider"/>

        <ContentLayout class="section-wrapper">
          <template #title>
            <div class="section-title">
              <slot name="section2-title"/>
            </div>
          </template>
          <RowLayout class="section-content">
            <slot name="section2"/>
          </RowLayout>
        </ContentLayout>
      </div>
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
.home-content {
  max-width: 1200px;
  margin: 0 auto;
  padding: 24px;
}

.section-wrapper {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  margin-bottom: 24px;
}

.section-title {
  font-size: 28px;
  font-weight: 600;
  color: #303133;
  padding: 32px 32px 24px 32px;
  text-align: center;
 margin: 0;
}

.section-content {
  padding: 24px;
  margin: 0;
}

.section-divider {
  margin: 32px 0;
  border-color: #e4e7ed;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .home-content {
    padding: 16px;
  }

  .section-title {
    font-size: 24px;
    padding: 24px 16px 20px 16px;
  }

  .section-content {
    padding: 16px;
  }

  .section-divider {
    margin: 24px 0;
  }
}
</style>
