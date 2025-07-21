<template>
  <div class="cart-page-layout">
    <Banner />
    <main>
      <div class="content">
        <slot name="content"></slot>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import Banner from '../common/Banner.vue';
</script>

<style scoped>
.cart-page-layout {
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
