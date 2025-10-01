<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="home-content">
        <div class="section-wrapper">
          <div class="content-layout">
            <div class="content-title">
              <div class="section-title">
                <slot name="section1-title"/>
              </div>
            </div>
            <div class="section-content">
              <slot name="section1"/>
            </div>
          </div>
        </div>

        <el-divider class="section-divider"/>

        <div class="section-wrapper">
          <div class="content-layout">
            <div class="content-title">
              <div class="section-title">
                <slot name="section2-title"/>
              </div>
            </div>
            <div class="section-content">
              <slot name="section2"/>
            </div>
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
  margin: 16px 0;
}

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

.section-content {
  width: 100%;
}

.section-title {
  font-size: 24px;
  font-weight: 600;
  color: #303133;
  padding: 20px 0;
  text-align: center;
}

.section-divider {
  margin: 32px 0;
}
</style>
