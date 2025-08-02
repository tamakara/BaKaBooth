<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <ContentLayout>
        <template #title>
          <slot name="section1-title"/>
        </template>
        <RowLayout>
          <slot name="section1"/>
        </RowLayout>
      </ContentLayout>

      <el-divider/>

      <ContentLayout>
        <template #title>
          <slot name="section2-title"/>
        </template>
        <RowLayout>
          <slot name="section2"/>
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

</style>
