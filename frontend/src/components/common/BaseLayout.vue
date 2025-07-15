<template>
  <el-container>
    <el-header>
      <HeaderBar/>
    </el-header>
    <el-main>
      <slot/>
    </el-main>
    <el-footer></el-footer>
  </el-container>
</template>

<script setup lang="ts">
import HeaderBar from '../common/HeaderBar.vue';
</script>

<style scoped>
.el-container {
  height: 100vh;
}

.el-header {
  height: 60px;
  width: 100vw;
}

.el-footer {
  height: 0px
}

</style>
