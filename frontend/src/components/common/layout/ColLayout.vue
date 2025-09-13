<template>
  <div class="col-layout">
    <div class="left">
      <slot name="left"/>
    </div>
    <div class="right">
      <slot name="right"/>
    </div>
  </div>
</template>

<script setup lang="ts">

</script>

<style scoped>
.col-layout {
  width: 100%;
  display: flex;
  gap: 24px;
}

.left {
  flex: 0 0 550px; /* 固定左侧宽度，不收缩不放大 */
  min-width: 550px;
}

.right {
  flex: 1; /* 右侧占剩余所有空间 */
  min-width: 0; /* 允许内容收缩 */
}

/* 响应式处理 */
@media (max-width: 1200px) {
  .col-layout {
    flex-direction: column;
  }

  .left {
    flex: none;
    width: 100%;
    min-width: 0;
  }

  .right {
    flex: none;
    width: 100%;
  }
}
</style>