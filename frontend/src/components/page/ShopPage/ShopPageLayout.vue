<template>
  <BaseLayout>
    <div class="shop-page-layout">
      <main>
        <div class="left-sidebar">
          <slot name="left-sidebar"></slot>
        </div>
        <div class="content">
          <slot name="content"></slot>
        </div>
      </main>
    </div>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/BaseLayout.vue";
</script>

<style scoped>
.shop-page-layout {
  display: flex;
  flex-direction: column;
}

main {
  display: flex;
  justify-content: space-between;
  padding: 20px;
}

.left-sidebar {
  width: 20%;
}

.content {
  width: 80%;
}
</style>
