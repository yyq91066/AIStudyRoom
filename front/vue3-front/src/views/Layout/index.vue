<script setup lang="ts">
import LayoutHeader from "./components/LayoutHeader.vue";
import LayoutFooter from "./components/LayoutFooter.vue";
// import LayoutNav from "./components/LayoutNav.vue";
//触发获取导航列表的 action
import { useCategoryStore } from "@/stores/category";
import {onMounted} from "vue";
const categoryStore = useCategoryStore();
onMounted(() => {
  console.log("父组件挂载")
  categoryStore.getCategory()
  console.log(categoryStore.categoryLIst,"categoryStoreFather")
})
</script>
<template>
  <LayoutHeader/>
<!--  <LayoutNav/>-->
    <RouterView/>
<!--  <LayoutFooter/>-->
</template>
