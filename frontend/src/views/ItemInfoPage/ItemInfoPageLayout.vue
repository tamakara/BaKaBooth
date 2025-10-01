<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="item-page-layout">
        <div class="main-content">
          <div class="left-column">
            <div class="left-panel">
              <div class="item-image-section">
                <slot name="item-image"/>
              </div>
              <div class="item-description-section">
                <slot name="item-text"/>
              </div>
            </div>
          </div>
          <div class="right-column">
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
          </div>
        </div>

        <div class="seller-info-section">
          <div class="seller-info-wrapper">
            <slot name="seller-info"/>
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
.item-page-layout {
  width: 944px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.main-content {
  display: flex;
  gap: 32px;
  width: 100%;
  margin-bottom: 32px;
}

.left-column {
  flex: 1;
}

.right-column {
  width: 400px;
}

.left-panel,
.right-panel {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  overflow: hidden;
}

.item-image-section,
.item-description-section,
.item-header-section,
.item-variations-section,
.item-info-section {
  padding: 24px;
}

.item-description-section {
  border-top: 1px solid #e4e7ed;
}

.item-variations-section,
.item-info-section {
  border-top: 1px solid #e4e7ed;
}

.seller-info-section {
  width: 100%;
}

.seller-info-wrapper {
  background: white;
  border-radius: 8px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.05);
  padding: 24px;
}
</style>
