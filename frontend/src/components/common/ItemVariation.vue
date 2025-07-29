<template>
  <div class="item-variation">
    <div class="item-variation-text">
      <div class="item-variation-info">
        <div class="item-variation-name">
          {{ name }}
        </div>
        <div class="item-variation-type">
          {{ type }}
        </div>
      </div>
      <div class="item-variation-price">
        {{ price }}
      </div>
    </div>

    <div class="item-variation-action">
      <el-button
          class="item-variation-button"
          color="#ff5c67"
          style="color: white"
      >
        <el-icon :size="24" style="margin-right: 10px;">
          <BanknotesIcon/>
        </el-icon>
        立即购买
      </el-button>

      <el-button
          class="item-variation-button"
      >
        <el-icon :size="24" style="margin-right: 10px">
          <ShoppingCartIcon/>
        </el-icon>
        加入购物车
      </el-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import {ShoppingCartIcon,BanknotesIcon} from '@heroicons/vue/24/outline'

const props = defineProps<{
  name: string
  type: string
  price: string
}>();

</script>

<style scoped>
.item-variation {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: 10px;
}

.item-variation-text {
  display: flex;
  justify-content: space-between;
}

.item-variation-info {


}

.item-variation-name {
  font-size: 16px;
}

.item-variation-type {
  font-size: 13px;
  color: #888888;
}

.item-variation-price {
  font-size: 20px;
}

.item-variation-action {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.item-variation-button {
  width: 100%;
  height: 60px;
  border-width: 2px;
  border-radius: 100px;
  font-size: 16px;
  font-weight: 600;
}


</style>