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
  height: auto;
}

.main {
  height: auto;
}

.footer {
  height: auto;
}

</style>
