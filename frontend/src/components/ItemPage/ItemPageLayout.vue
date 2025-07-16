<template>
  <BaseLayout>
    <div class="product-page-layout">
      <div class="content">
        <slot name="content"></slot>
      </div>
    </div>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/BaseLayout.vue";
</script>

<style scoped>
.product-page-layout {
  padding: 20px;
}

.content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
}
</style>
