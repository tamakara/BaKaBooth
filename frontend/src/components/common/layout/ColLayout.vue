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
}

.left {
  flex: 3;
}

.right {
  flex: 2;
}
</style>