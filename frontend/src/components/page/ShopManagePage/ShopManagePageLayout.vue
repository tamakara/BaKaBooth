<template>
  <BaseLayout>
    <template #header>
      <Banner/>
    </template>
    <template #main>
      <ContentLayout>
        <template #title>
          <RowLayout class="title">
            店铺管理
          </RowLayout>
        </template>
        <RowLayout class="dashboard">
          <RowLayout class="dashboard-header">
            <div>
              <slot name="dashboard-header-left"/>
            </div>
            <div>
              <slot name="dashboard-header-right"/>
            </div>
          </RowLayout>
          <ColLayout class="dashboard-pane">
            <template #left>
              <RowLayout class="dashboard-pane-left">
                <slot name="dashboard-pane-left"/>
              </RowLayout>
            </template>
            <template #right>
              <RowLayout class="dashboard-pane-right">
                <slot name="dashboard-pane-right"/>
              </RowLayout>
            </template>
          </ColLayout>
        </RowLayout>
      </ContentLayout>
    </template>
  </BaseLayout>
</template>

<script setup lang="ts">
import BaseLayout from "@/components/common/layout/BaseLayout.vue";
import Banner from "@/components/common/Banner.vue";
import RowLayout from "@/components/common/layout/RowLayout.vue";
import ColLayout from "@/components/common/layout/ColLayout.vue";
import ContentLayout from "@/components/common/layout/ContentLayout.vue";
</script>

<style scoped>
.title {
  font-size: 20px;
  margin: 26px 0;
}

.dashboard {
  border: 2px solid #e6e8eb;
  background-color: white;
}

.dashboard-header {
  padding: 26px;
  display: flex;
  justify-content: space-between;
  border-bottom: 2px solid #e6e8eb;
}

.dashboard-pane-left {
  height: 100%;
  padding: 26px;
  border-right: 2px solid #e6e8eb;
}

.dashboard-pane-right {
  height: 100%;
  padding: 26px;
}

</style>