<template>
  <div class="shop-page-layout">
    <HeaderBar />
    <main>
      <div class="left-sidebar">
        <slot name="left-sidebar"></slot>
      </div>
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
.shop-page-layout {
  display: flex;
  flex-direction: column;
}
main {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}
.left-sidebar {
  width: 20%;
}
.content {
  width: 80%;
}
</style>
