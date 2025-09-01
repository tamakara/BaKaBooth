<template>
  <ShopManageItemPageLayout>
    <template #actions-left>
      <el-radio-group v-model="currentState" size="small">
        <el-radio-button label="All" value="all"/>
        <el-radio-button label="Draft" value="draft"/>
        <el-radio-button label="Public" value="public"/>
        <el-radio-button label="Private" value="private"/>
      </el-radio-group>
    </template>
    <template #actions-right>
      <div class="add-item">
        <el-button
            class="add-item-button"
            color="#2c9ba6"
            @click="handleAddItem"
        >
          <el-icon style="margin-right: 5px">
            <PlusIcon/>
          </el-icon>
          添加商品
        </el-button>
      </div>
    </template>
    <template #items>
      <div
          class="item"
          v-for="item in items"
          :key="item.id"
      >
        <div style="display: flex;gap: 15px;height:80px;">
          <el-image
              lazy
              :src="item.coverUrl"
              fit="contain"
              style="width: 80px;  "
          />
          <div style="display: flex;flex-direction: column;justify-content: center;">
            <div class="item-state">{{ item.state }}</div>
            <div class="item-name">{{ item.name }}</div>
            <div class="item-url">http://localhost:5173/item/{{ item.id }}</div>
          </div>
        </div>
        <div class="variations">
          <div
              class="variation"
              v-for="(variation, index) in item.variations"
              :key="index"
          >
            <div class="variation-name">
              <el-icon>
                <CloudArrowDownIcon/>
              </el-icon>
              {{ variation.name }}
            </div>
            <div class="variation-data">
              <div class="variation-data-heading"></div>
            </div>
            <div class="variation-data">
              <div class="variation-data-heading"></div>
            </div>
            <div class="variation-data">
              <div class="variation-data-heading">售价</div>
              {{ variation.price }}
            </div>
            <div class="variation-data">
              <div class="variation-data-heading">存库</div>
              {{ variation.stock }}
            </div>
          </div>
        </div>
      </div>
    </template>
  </ShopManageItemPageLayout>
</template>

<script setup lang="ts">
import ShopManageItemPageLayout from "@/components/page/ShopManageItemPage/ShopManageItemPageLayout.vue";
import {onMounted, ref} from "vue";
import type {ItemManageVO} from "@/types/ItemTypes.d.ts";
import {CloudArrowDownIcon, PlusIcon} from "@heroicons/vue/24/outline";
import {createItem, getItemManageVO} from "@/api/item.ts";
import {useRouter} from "vue-router";

const router = useRouter()

async function handleAddItem() {
  const itemId = await createItem()
  await router.push({name: 'item-edit', params: {id: itemId}})
}

const currentState = ref('all');

const items = ref<Array<ItemManageVO>>()

onMounted(async () => {
  items.value = await getItemManageVO()
})


</script>

<style scoped>
.add-item-button {
  width: 137px;
  height: 42px;
  border-radius: 2px;
  font-size: 16px;
}


.item {
  width: 100%;
  padding: 26px;
  border: 1px solid #dcdfe6;
  background-color: white;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.item-state {
  width: 50px;
  height: 20px;
  font-size: 11px;
  font-weight: 600;
  text-align: center;
  border: 1px solid gray;
  border-radius: 3px;
  color: #7f8d9f;
  background-color: white;
}

.item-name {
  font-size: 16px;
  color: #96a2b0;
}

.item-url {
  font-size: 13px;
  color: #afb2bd;
}

.variations {
  border-top: 1px dashed #d5d9de;
}

.variation {
  height: 40px;
  border-bottom: 1px dashed #d5d9de;
  display: flex;
  align-items: center;
  font-size: 13px;
  color: #5e656f;
}

.variation-name {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 15px;
}

.variation-data {
  width: 80px;
  height: 100%;
  text-align: right;
  font-weight: 600;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.variation-data-heading {
  color: #5e656f;
  font-weight: 400;
}
</style>