<script setup lang="ts">
import HomeBanner from "@/views/Home/components/HomeBanner.vue";
import HomeCategory from "@/views/Home/components/HomeCategory.vue";
import HomeNew from "@/views/Home/components/HomeNew.vue";
import HomeProducts from "@/views/Home/components/HomeProducts.vue";
// 触发获取房型列表的 action
import {useRoomTypesStore} from "@/stores/roomTypes.js"
import {onMounted} from "vue";
const roomTypesStore = useRoomTypesStore()
onMounted(() => {
  roomTypesStore.getRoomTypes()
  console.log(roomTypesStore.roomTypesList,"home-index-roomTypesStore")
})
</script>

<template>
<a>我是home页</a>
<div>
<!--  <HomeBanner />-->
  <HomeCategory />
</div>

  <HomeNew />
  <HomeProducts />
</template>

<style scoped>

</style>