<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <slot/>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">

import BaseLayout from "@/components/common/layout/BaseLayout.vue";
import Banner from "@/components/common/Banner.vue";
</script>

<style scoped>

</style>