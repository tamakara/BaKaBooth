<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <ContentLayout class="item-page-layout">
        <ColLayout class="main-content">
          <template #left>
            <div class="left-panel">
              <div class="item-image-section">
                <slot name="item-image"/>
              </div>

              <div class="item-description-section">
                <slot name="item-text"/>
              </div>
            </div>
          </template>
          <template #right>
            <div class="right-panel">
              <div class="item-header-section">
                <slot name="item-header"/>
              </div>

              <div class="item-variations-section">
                <slot name="item-variations"/>
              </div>

              <div class="item-info-section">
                <slot name="item-terms"/>
              </div>
            </div>
          </template>
        </ColLayout>
      </ContentLayout>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/layout/BaseLayout.vue";
import Banner from "@/components/common/Banner.vue";
import ContentLayout from "@/components/common/layout/ContentLayout.vue";
import ColLayout from "@/components/common/layout/ColLayout.vue";
import RowLayout from "@/components/common/layout/RowLayout.vue";
</script>

<style scoped>
.item-page-layout {
  padding: 0;
}

.main-content {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
  margin-bottom: 24px;
}

.left-panel {
  padding: 24px;
  background: #fafafa;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.right-panel {
  padding: 24px;
  background: white;
  border-left: 1px solid #e4e7ed;
}

.item-image-section {
  margin-bottom: 24px;
}

.item-description-section {
  background: white;
  border-radius: 8px;
  padding: 20px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
  flex: 1;
  display: flex;
  flex-direction: column;
}

.item-header-section {
  margin-bottom: 24px;
  padding-bottom: 20px;
  border-bottom: 1px solid #e4e7ed;
}

.item-variations-section {
  margin-bottom: 24px;
}

.item-info-section {
  background: #f8f9fa;
  border-radius: 8px;
  padding: 20px;
}

.additional-content {
  margin: 24px 0;
}

.item-tags-section {
  width: 100%;
  padding: 16px 0;
}

.section-divider {
  margin: 32px 0;
}

.shop-section {
  margin-top: 24px;
}

.shop-items-section {
  width: 100%;
  background: white;
  border-radius: 8px;
  padding: 24px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .left-panel,
  .right-panel {
    padding: 16px;
  }

  .item-description-section,
  .item-info-section,
  .shop-items-section {
    padding: 16px;
  }

  .section-divider {
    margin: 24px 0;
  }
}
</style>
