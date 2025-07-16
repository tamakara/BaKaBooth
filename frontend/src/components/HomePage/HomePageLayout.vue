<template>
  <BaseLayout>
    <div class="home-page-layout">
      <slot/>
    </div>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/BaseLayout.vue";

</script>

<style scoped>
.home-page-layout {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
