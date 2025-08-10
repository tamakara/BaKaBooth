<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <ContentLayout>
        <template #title>
          <RowLayout class="title">
            店铺管理
          </RowLayout>
        </template>
        <RowLayout class="dashboard">
          <RowLayout class="dashboard-header">
            <div class="header-left">
              <slot name="dashboard-header-left"/>
            </div>
            <div class="header-right">
              <slot name="dashboard-header-right"/>
            </div>
          </RowLayout>
          <ColLayout class="dashboard-pane">
            <template #left>
              <RowLayout class="dashboard-pane-left">
                <slot name="dashboard-pane-left"/>
              </RowLayout>
            </template>
            <template #right>
              <RowLayout class="dashboard-pane-right">
                <slot name="dashboard-pane-right"/>
              </RowLayout>
            </template>
          </ColLayout>
        </RowLayout>
      </ContentLayout>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/layout/BaseLayout.vue";
import Banner from "@/components/common/Banner.vue";
import RowLayout from "@/components/common/layout/RowLayout.vue";
import ColLayout from "@/components/common/layout/ColLayout.vue";
import ContentLayout from "@/components/common/layout/ContentLayout.vue";
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

.dashboard {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  margin: 0;
}

.dashboard-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px 32px;
  background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
  border-bottom: 1px solid #e4e7ed;
  margin: 0;
}

.header-left {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.header-right {
  display: flex;
  align-items: center;
}

.dashboard-pane {
  min-height: 400px;
}

.dashboard-pane-left {
  padding: 24px;
  border-right: 1px solid #e4e7ed;
  background: #fafafa;
}

.dashboard-pane-right {
  padding: 24px;
  background: white;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .title {
    font-size: 24px;
    padding: 24px 16px 20px 16px;
  }

  .dashboard-header {
    flex-direction: column;
    gap: 16px;
    align-items: stretch;
    padding: 20px 16px;
  }

  .dashboard-pane-left,
  .dashboard-pane-right {
    padding: 16px;
  }
}
</style>