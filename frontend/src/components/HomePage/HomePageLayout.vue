<template>
  <BaseLayout>
    <div class="home-page-layout">
      <div
          class="section"
          v-for="i in 2"
          :key="i"
      >
        <div class="section-content">
          <slot :name="i"/>
        </div>
        <el-divider/>
      </div>
    </div>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/BaseLayout.vue";

</script>

<style scoped>
.home-page-layout {
  display: flex;
  flex-direction: column;
}

.section {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  gap: 20px;
}

.section-content {
  width: 944px;
  display: flex;
  flex-direction: column;
}
</style>
