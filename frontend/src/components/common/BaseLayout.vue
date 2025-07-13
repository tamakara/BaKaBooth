<template>
  <el-container>
    <el-header>
      <HeaderBar/>
    </el-header>
    <el-main>
      <slot/>
    </el-main>
    <el-footer>
    xxx
    </el-footer>
  </el-container>
</template>

<script setup lang="ts">
import HeaderBar from '../common/HeaderBar.vue';
</script>

<style scoped>
.el-container {
  height: 100vh;
}

</style>
