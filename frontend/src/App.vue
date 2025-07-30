<template>
  <RouterView/>
</template>

<script setup lang="ts">
import {useUserStore} from "@/stores/user.ts";
import {onMounted} from "vue";

const userStore = useUserStore();
onMounted(() => {
  userStore.checkToken();
});

</script>

<style scoped>

</style>
