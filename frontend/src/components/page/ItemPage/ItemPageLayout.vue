<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <div class="item-page-layout">
        <div class="item-content">
          <div class="left">
            <div class="item-image">
              <slot name="item-image"/>
            </div>

            <div class="item-text">
              <slot name="item-text"/>
            </div>
          </div>

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
        </div>

        <div class="item-tag-list">
          <slot name="item-tag-list"/>
        </div>

        <el-divider/>

        <div class="shop-items">
          <slot name="shop-items"/>
        </div>

      </div>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/BaseLayout.vue";
import Banner from "@/components/common/Banner.vue";
</script>

<style scoped>
.item-page-layout {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  background-color: #ffffff;
}

.item-content {
  width: 944px;
  display: flex;
  justify-content: center;
  gap: 40px;
  margin-top: 52px;
}

.left {
  flex: 3;

  display: flex;
  flex-direction: column;
  gap: 20px;

}

.item-image {
  height: 550px;
  width: 550px;
}

.item-text {


}

.right {
  flex: 2;
  width: 346px;
  display: flex;
  flex-direction: column;
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
