<template>
  <BaseLayout>
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

          <el-divider/>

          <div class="item-variations">
            <slot name="item-variations"/>
          </div>

          <el-divider/>

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
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/BaseLayout.vue";
</script>

<style scoped>
.item-page-layout {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.item-content {
  width: 944px;
  display: flex;
  justify-content: center;
  gap: 48px;
}

.left {
  flex: 3;
  width: 550px;

  display: flex;
  flex-direction: column;
  gap: 20px;
}

.item-image {
  height: 550px;
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

}

</style>
