<script setup lang="ts">
import { useRoomTypesStore } from '@/stores/roomTypes.ts'
const roomTypesStore = useRoomTypesStore()
</script>

<template>
  <div>

    <el-carousel height="500px">
      <el-carousel-item v-for="item in roomTypesStore.roomTypesList" :key="item.id">
        <img :src="item.picture" alt="首页轮播图 ">
      </el-carousel-item>
    </el-carousel>
  </div>

</template>

<style scoped></style>
