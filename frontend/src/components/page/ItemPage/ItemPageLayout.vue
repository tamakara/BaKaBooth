<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <ContentLayout class="item-page-layout">
        <ColLayout>
          <template #left>
            <div class="left">
              <div class="item-image">
                <slot name="item-image"/>
              </div>

              <div class="item-text">
                <slot name="item-text"/>
              </div>
            </div>
          </template>
          <template #right>
            <div class="right">
              <div class="item-header">
                <slot name="item-header"/>
              </div>

              <div class="item-variations">
                <slot name="item-variations"/>
              </div>

              <div class="item-terms">
                <slot name="item-terms"/>
              </div>
            </div>
          </template>
        </ColLayout>

        <RowLayout>
          <div class="item-tag-list">
            <slot name="item-tag-list"/>
          </div>
        </RowLayout>

        <el-divider/>

        <RowLayout>
          <div class="shop-items">
            <slot name="shop-items"/>
          </div>
        </RowLayout>
      </ContentLayout>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/layout/BaseLayout.vue";
import Banner from "@/components/common/Banner.vue";
import ContentLayout from "@/components/common/layout/ContentLayout.vue";
import ColLayout from "@/components/common/layout/ColLayout.vue";
import RowLayout from "@/components/common/layout/RowLayout.vue";
</script>

<style scoped>
.item-page-layout {
  margin-top: 50px;
}

.left {
  padding-right: 16px;
}

.right {
  padding-left: 32px;
}


.item-image {
  height: 550px;
  width: 550px;
}

.item-text {
  margin-top: 20px;
}

.item-header {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.item-terms {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

</style>
