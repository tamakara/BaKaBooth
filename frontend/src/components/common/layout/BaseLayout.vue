<template>
  <el-container class="container">
    <el-header class="header">
      <slot name="header"/>
    </el-header>
    <el-main class="main">
      <slot name="main"/>
    </el-main>
    <el-footer class="footer">
      <slot name="footer"/>
    </el-footer>
  </el-container>
</template>

<script setup lang="ts">

</script>

<style scoped>
.container {
  height: 100vh;
  width: 100vw;
  background-color: #f1f5f8;
}

.header {
  height: auto !important;
  padding: 0 !important;
  line-height: normal !important;
  overflow: hidden !important;
}

.header :deep(.banner) {
  height: 60px !important;
  min-height: 60px !important;
  max-height: 60px !important;
}

.main {
  display: flex;
  flex-direction: column;
}

.footer {
  height: auto;
}

</style>
