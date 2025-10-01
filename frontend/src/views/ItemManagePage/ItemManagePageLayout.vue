<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="content-layout">
        <div class="content-title">
          <div class="title">
            商品管理
          </div>
        </div>
        <div class="actions">
          <div class="actions-left">
            <slot name="actions-left"/>
          </div>
          <div class="actions-right">
            <slot name="actions-right"/>
          </div>
        </div>
        <el-divider/>
        <div class="items">
          <slot name="items"/>
        </div>
      </div>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/layout/BaseLayout.vue";
import Banner from "@/components/business/Banner.vue";
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
  width: 100%;
}

.title {
  font-size: 25px;
  padding: 25px 0;
  width: 100%;
}

.actions {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.items {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

</style>