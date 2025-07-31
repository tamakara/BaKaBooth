<template>
  <ShopManagePageLayout>

    <RowLayout class="shop-manage-title">
      店铺管理页面
    </RowLayout>
    <ContentLayout>
      <div class="shop-manage-content">
        <RowLayout>
          <div class="dashboard-header">
            <ColLayout>
              <template #left>
                <div class="shop-status">
                  店铺隐藏中
                </div>

                <div class="shop-name">
                  tamakara
                  <el-text tag="a">[编辑]</el-text>
                </div>

                <div class="shop-url">
                  <el-text tag="a" style="font-size: 13px"> xxx.tamakara.com</el-text>
                </div>

                <div class="shop-followers">
                  <el-icon style="margin-right: 5px">
                    <UserPlusIcon/>
                  </el-icon>
                  666 关注者
                </div>
              </template>
              <template #right>
                <div class="goto">
                  <el-button
                      class="goto-setting"
                  >
                    店铺设置
                  </el-button>
                  <el-button
                      class="change-status"
                  >
                    切换状态
                  </el-button>
                </div>
              </template>
            </ColLayout>
          </div>
        </RowLayout>
        <ColLayout class="dashboard-pane">
          <template #left>
            <div class="dashboard-pane-left">
              xxx
            </div>
          </template>
          <template #right>
            yyy
          </template>
        </ColLayout>
      </div>
    </ContentLayout>
  </ShopManagePageLayout>
</template>

<script setup lang="ts">
import ShopManagePageLayout from "@/components/page/ShopManagePage/ShopManagePageLayout.vue";
import ColLayout from "@/components/common/layout/ColLayout.vue";
import ContentLayout from "@/components/common/layout/ContentLayout.vue";
import RowLayout from "@/components/common/layout/RowLayout.vue";
import {UserPlusIcon} from "@heroicons/vue/24/outline";
</script>

<style scoped>
.shop-manage-content {
  width: 100%;
  height: 100%;
  border: 1px solid #e6e8eb;
}

.shop-manage-title {
  display: flex;
  justify-content: center;
  font-size: 20px;
  margin: 20px 0;
}

.dashboard-header {
  background-color: white;
  height: 100px;
  padding: 25px;
  border-bottom: 1px solid #e6e8eb;
}

.shop-status {
  width: 80px;
  height: 20px;
  font-size: 12px;
  background-color: #7f7aed;
  color: white;
  padding: 2px 8px;
  text-align: center;
}

.shop-name {
  font-size: 21px;
  font-weight: 500;
}

.shop-followers {
  font-size: 13px;
  display: flex;
  align-items: center;
}

.goto {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
}

.goto-setting,
.change-status {
  width: 135px;
  height: 30px;
  background-color: #5f697a3d;
  color: #3d4651;
  border-radius: 2px;
}

.dashboard-pane {
  background-color: white;
}

.dashboard-pane-left {
  padding: 26px;
  border-right: 1px solid #e6e8eb;
}

</style>