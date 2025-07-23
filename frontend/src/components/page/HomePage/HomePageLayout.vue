<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
   <template #main>
     <div class="home-page-layout">
       <div
           class="section"
           v-for="i in size"
           :key="i"
       >
         <div class="section-content">
           <slot :name="i"/>
         </div>
         <el-divider/>
       </div>
     </div>
   </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/BaseLayout.vue";
import Banner from "@/components/common/Banner.vue";

defineProps<{
  size: number;
}>();

</script>

<style scoped>
.home-page-layout {
  display: flex;
  flex-direction: column;
}

.section {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  gap: 20px;
}

.section-content {
  width: 944px;
  display: flex;
  flex-direction: column;
}
</style>
