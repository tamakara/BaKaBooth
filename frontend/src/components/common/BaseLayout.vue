<template>
  <el-container>
    <el-header>
      <Banner/>
    </el-header>
    <el-main>
      <slot/>
    </el-main>
    <el-footer></el-footer>
  </el-container>
</template>

<script setup lang="ts">
import Banner from './Banner.vue';
</script>

<style scoped>
.el-container {
  height: 100vh;
  width: 100vw;
  background-color: #f1f5f8;
}

.el-header {
  border-bottom: 2px solid rgba(0, 0, 0, 0.05);
}

.el-main {

}

.el-footer {
  height: 0;
}

</style>
