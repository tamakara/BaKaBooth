<template>
  <ItemInfoPageLayout>
    <template #item-image>
      <div class="item-image-wrapper">
        <ImageSlider/>
      </div>
    </template>

    <template #item-text>
      <div class="item-text">
        {{ itemData.description }}
      </div>
    </template>

    <template #item-header>
      <div class="shop-avatar-name" @click="console.log('fuck')">
        <el-avatar :size="24">Y</el-avatar>
        <div class="shop-name">{{ sellerUserData.name }}</div>
        <el-button
            text
            @click="console.log('star')"
            style="width: 20px"
        >
          <el-icon :size="20">
            <StarIcon/>
          </el-icon>
        </el-button>
      </div>
      <div class="item-title">{{ itemData.name }}</div>
      <div class="item-buttons">
        <div class="item-buttons-left">
          <el-button class="item-button-favorite">
            <el-icon size="24" style="margin-right: 3px">
              <HeartIcon/>
            </el-icon>
            {{ itemData.favorites }}
          </el-button>
        </div>
        <div class="item-buttons-right">
          <el-button class="item-button-chat" text>
            <el-icon size="24">
              <ChatBubbleOvalLeftEllipsisIcon/>
            </el-icon>
          </el-button>

          <el-button class="item-button-share" text>
            <el-icon size="24">
              <ShareIcon/>
            </el-icon>
          </el-button>

          <el-button class="item-button-report" text>
            <el-icon size="24">
              <ExclamationTriangleIcon/>
            </el-icon>
          </el-button>
        </div>
      </div>
    </template>

    <template #item-variations>
      <el-divider/>
      <div
          class="item-variations"
          v-for="(variation, index) in itemData.variations"
          :key="index"
      >
        <ItemVariation
            :name="variation.name"
            :type="variation.type"
            :price="`${variation.price}`"
        />
        <el-divider/>
      </div>
    </template>

    <template #item-terms>

      <div class="item-info">
        <div class="item-terms-title">
          <el-icon>
            <InformationCircleIcon/>
          </el-icon>
          商品信息
        </div>
        <div class="item-terms-content">
          发售日期
          <br/>
          更新日期
          <br/>
          商品类型
          <br/>
          商品参数
        </div>
      </div>

      <div class="item-delivery">
        <div class="item-terms-title">
          <el-icon>
            <TruckIcon/>
          </el-icon>
          发货方式
        </div>
        <div class="item-terms-content">
          人工发货
          <br/>
          自动发货
          <br/>
          快递物流
        </div>
      </div>

      <div class="item-service">
        <div class="item-terms-title">
          <el-icon>
            <ShieldCheckIcon/>
          </el-icon>
          售后服务
        </div>
        <div class="item-terms-content">
          不支持退换货
          <br/>
          七天无理由退换货
          <br/>
          质保一个月
        </div>
      </div>
    </template>

  </ItemInfoPageLayout>
</template>

<script setup lang="ts">
import ItemInfoPageLayout from './ItemInfoPageLayout.vue';
import {useRoute} from 'vue-router';
import ImageSlider from "@/components/common/ImageSlider.vue";
import ItemVariation from "@/components/common/ItemVariation.vue";
import {
  StarIcon,
  HeartIcon,
  ShareIcon,
  ExclamationTriangleIcon,
  ChatBubbleOvalLeftEllipsisIcon,
  InformationCircleIcon,
  TruckIcon,
  ShieldCheckIcon
} from '@heroicons/vue/24/outline'
import {onMounted, ref} from "vue";
import {getItemVO} from "@/api/item.ts";
import type {ItemVO} from "@/types/ItemTypes";
import {getSellerUserVO} from "@/api/user.ts";
import type {SellerUserVO} from "@/types/UserTypes";

const route = useRoute();

const itemData = ref<ItemVO>({})
const sellerUserData = ref<SellerUserVO>({})

onMounted(async () => {
  const itemId = route.params.id as string;
  itemData.value = await getItemVO(itemId)
  const sellerUserId = itemData.value.userId as string
  sellerUserData.value = await getSellerUserVO(sellerUserId)
})


</script>

<style scoped>

.item-image-wrapper {
  width: 550px;
  height: 550px;
  background-color: #ebedf2;
}

.item-text {
  font-size: 16px;
  white-space: pre-wrap;
  word-wrap: break-word;
}

.shop-avatar-name {
  height: 30px;
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
}

.shop-name {
  flex: 1;
  font-size: 16px;
  font-weight: 300;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.item-title {
  font-size: 24px;
  font-weight: 700;
}

.item-buttons {
  display: flex;
  justify-content: space-between;
}

.item-buttons-left {

}


.item-buttons-right {
  display: flex;
}

.item-button-favorite {
  width: 80px;
  height: 50px;
  border: 2px solid #e4e7ed;
  border-radius: 8px;
  display: flex;
  font-size: 18px;
}

.item-button-chat,
.item-button-share,
.item-button-report {
  width: 50px;
  height: 50px;

}

.item-terms-title {
  font-size: 16px;
  font-weight: 600;
  margin-bottom: 5px;
}

.item-terms-content {
  font-size: 14px;
  font-weight: 320;
}
</style>
