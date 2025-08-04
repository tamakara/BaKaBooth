<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <ContentLayout>
        <template #title>
          <RowLayout class="title">
            商品管理
          </RowLayout>
        </template>
        <RowLayout class="actions">
          <div class="actions-left">
            <slot name="actions-left"/>
          </div>
          <div class="actions-right">
            <slot name="actions-right"/>
          </div>
        </RowLayout>
        <el-divider/>
        <RowLayout class="items">
          <slot name="items"/>
        </RowLayout>
      </ContentLayout>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/layout/BaseLayout.vue";
import Banner from "@/components/common/Banner.vue";
import ContentLayout from "@/components/common/layout/ContentLayout.vue";
import RowLayout from "@/components/common/layout/RowLayout.vue";
</script>

<style scoped>
.title {
  font-size: 25px;
  padding: 25px 0;
}

.actions {
  display: flex;
  justify-content: space-between;
}

.items {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
}

</style>