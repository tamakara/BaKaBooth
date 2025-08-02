<template>
  <ShopManagePageLayout>
    <template #dashboard-header-left>
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

    <template #dashboard-header-right>
      <div class="goto">
        <el-button class="goto-setting">
          店铺设置
        </el-button>
        <el-button class="change-status">
          公开店铺
        </el-button>
      </div>
    </template>

    <template #dashboard-pane-left>
      <div>收益信息</div>
      <div>xxx</div>
    </template>
    <template #dashboard-pane-right>

      <div class="nav">
        <div class="nav-item" @click="goToRoute('shop-manage-item')">
          <el-icon class="nav-item-icon">
            <ShoppingBagIcon/>
          </el-icon>
          商品管理
        </div>

        <div class="nav-item">
          <el-icon class="nav-item-icon">
            <ShoppingBagIcon/>
          </el-icon>
          订单列表
        </div>

        <div class="nav-item">
          <el-icon class="nav-item-icon">
            <ShoppingBagIcon/>
          </el-icon>
          收益管理
        </div>
      </div>


    </template>


  </ShopManagePageLayout>
</template>

<script setup lang="ts">
import ShopManagePageLayout from "@/components/page/ShopManagePage/ShopManagePageLayout.vue";
import {UserPlusIcon, ShoppingBagIcon} from "@heroicons/vue/24/outline";
import {useRouter} from "vue-router";

const router = useRouter();
const goToRoute = (name: string) => router.push({name});

</script>

<style scoped>

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

.nav {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.nav-item {
  height: 24px;
  display: flex;
  gap: 10px;
  cursor: pointer;
}

.nav-item-icon {
  padding: 4px;
  background-color: #42b5bd;
  color: white;
  border-radius: 4px;
}
</style>