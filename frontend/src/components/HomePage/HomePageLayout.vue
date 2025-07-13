<template>
  <div class="home-page-layout">
    <HeaderBar />
    <main>
      <div class="left-sidebar">
        <slot name="left-sidebar"></slot>
      </div>
      <div class="content">
        <slot name="content"></slot>
      </div>
      <div class="right-sidebar">
        <slot name="right-sidebar"></slot>
      </div>
    </main>
  </div>
</template>

<script setup lang="ts">
import HeaderBar from '../common/HeaderBar.vue';
</script>

<style scoped>
.home-page-layout {
  display: flex;
  flex-direction: column;
}
main {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}
.left-sidebar, .right-sidebar {
  width: 20%;
}
.content {
  width: 60%;
}
</style>
