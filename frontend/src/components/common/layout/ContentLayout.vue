<template>
  <div class="content-layout">
    <div class="content-title">
      <slot name="title"/>
    </div>
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

.content-title {

}
</style>