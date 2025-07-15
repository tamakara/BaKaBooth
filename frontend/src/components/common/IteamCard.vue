<template>
  <el-card>
    <el-image
        :src="item.coverUrl"
        lazy
    />

  </el-card>
</template>

<script setup lang="ts">
import type {Item} from "@/types/ItemTypes.ts";

const props = defineProps<{
  item: Item
}>()

</script>

<style scoped>

</style>