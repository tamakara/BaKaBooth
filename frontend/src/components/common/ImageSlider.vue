<template>
  <div class="image-slider">
    <!-- 图片滑动容器 -->
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
          :label="index+1"
          style="display: flex;justify-content: center;align-items: center"
      >
        <el-image
            fit="contain"
            :src="image"
            style="height: 100%"
        />
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup lang="ts">
import {ref} from 'vue';
import {ElCarousel, ElCarouselItem} from 'element-plus';
// 图片数据
const images = [
  'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
  'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
  'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
  'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
  'https://cube.elemecdn.com/6/94/4d3ea53c084bad6931a56d5158a48jpeg.jpeg',
];

// 当前显示的图片索引
const activeIndex = ref(0);


</script>

<style scoped>
.image-slider {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
}

</style>
