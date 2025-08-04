<template>
  <div class="item-grid">
    <el-space
        direction="vertical"
        alignment="normal"
    >
      <el-row
          v-for="r in rows"
          :gutter="gap"
      >
        <el-col
            v-for="(item, index) in items.slice((r-1)* columns,r* columns)"
            :key="index"
            :span="span"
        >
          <ItemCard :item="item"/>
        </el-col>
      </el-row>
    </el-space>
  </div>
</template>

<script setup lang="ts">
import {defineProps, computed} from 'vue';
import type {ItemDisplayDTO} from "@/types/ItemTypes.ts";
import ItemCard from "@/components/common/ItemCard.vue";

const props = defineProps<{
  maxRows: number;
  maxColumns: number;
  gap: number;
  items: ItemDisplayDTO[];
}>();

const columns = computed(() => Math.min(props.items.length, props.maxColumns));
const rows = computed(() => Math.min(Math.ceil(props.items.length / props.maxColumns), props.maxRows));
const span = computed(() => (24 / props.maxColumns));

</script>

<style scoped>
</style>
