<template>
  <div class="product-page-layout">
    <HeaderBar />
    <div class="content">
      <slot name="content"></slot>
    </div>
  </div>
</template>

<script setup lang="ts">
import HeaderBar from '../common/HeaderBar.vue';
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
