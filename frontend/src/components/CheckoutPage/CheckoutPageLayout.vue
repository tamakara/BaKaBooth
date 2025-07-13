<template>
  <div class="checkout-page-layout">
    <HeaderBar />
    <main>
      <div class="content">
        <slot name="content"></slot>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import HeaderBar from '../common/HeaderBar.vue';
</script>

<style scoped>
.checkout-page-layout {
  display: flex;
  flex-direction: column;
}
main {
  display: flex;
  justify-content: center;
  padding: 20px;
}
.content {
  max-width: 800px;
  width: 100%;
}
</style>
