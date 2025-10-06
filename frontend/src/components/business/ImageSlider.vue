<template>
  <div class="image-slider">
    <el-carousel
        v-model="activeIndex"
        arrow="always"
        height="100%"
        trigger="click"
        style="width: 100%;height: 100%;"
    >
      <el-carousel-item
          v-for="(image, index) in images"
          :key="index"
          :label="(index+1).toString()"
          style="display: flex;justify-content: center;align-items: center"
      >
        <el-image
            fit="contain"
            :src="image"
            style="height: 100%;width:100%"
        />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue';

// 接收从父组件传递的图片数组
interface Props {
  images: string[]
}

const props = withDefaults(defineProps<Props>(), {
  images: () => []
});

// 当前显示的图片索引
const activeIndex = ref(0);
</script>

<style scoped>
.image-slider { width:100%; height:420px; border-radius:12px; overflow:hidden; background:#f5f7fa; }
.image-slider :deep(.el-carousel__container){ height:100%; }
</style>
