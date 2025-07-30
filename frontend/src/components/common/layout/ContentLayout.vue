<template>
  <div class="content-layout">
    <slot/>
  </div>
</template>

<script setup lang="ts">

</script>

<style scoped>
.content-layout {
  width: 944px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>