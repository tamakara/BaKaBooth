<template>
  <BaseLayout>
    <div class="checkout-page-layout">
      <main>
        <div class="content">
          <slot name="content"></slot>
        </div>
      </main>
    </div>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/layout/BaseLayout.vue";
</script>

<style scoped>
.checkout-page-layout {
  display: flex;
  flex-direction: column;
}

main {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.content {
  max-width: 800px;
  width: 100%;
}
</style>
