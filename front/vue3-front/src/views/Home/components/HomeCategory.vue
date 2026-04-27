<script setup>
import {useRoomTypesStore} from "@/stores/roomTypes.js"
import {onMounted, ref} from "vue";

const roomTypesStore = useRoomTypesStore()
const activeIndex = ref(0)

onMounted(() => {
  roomTypesStore.getRoomTypes()
})

const selectRoomType = (index) => {
  activeIndex.value = index
}
</script>

<template>
  <h3>自习室房型</h3>
  <div class="category-container">
    <div class="room-types-list">
      <div
          v-for="(item, index) in roomTypesStore.roomTypesList"
          :key="item.id"
          class="room-type-item"
          :class="{ active: activeIndex === index }"
          @click="selectRoomType(index)"
      >
        {{ item.name }}
      </div>
    </div>

    <div class="room-image-container">
      <el-image
          v-if="roomTypesStore.roomTypesList[activeIndex]?.picture"
          :src="roomTypesStore.roomTypesList[activeIndex].picture"
          fit="contain"
          class="room-image">
        <template #error>
          <div class="image-error">图片加载失败</div>
        </template>
      </el-image>
      <div v-else class="no-image">暂无图片</div>
    </div>
  </div>
</template>

<style scoped>.category-container {
  display: flex;
  gap: 24px;
  max-width: 1200px;
  margin: 0 auto;
  height: 450px;
  padding: 24px;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e9f2 100%);
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
}

.room-types-list {
  width: 240px;
  min-width: 240px;
  background: #ffffff;
  border-radius: 12px;
  padding: 16px;
  overflow-y: auto;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
}

.room-type-item {
  padding: 16px 20px;
  margin-bottom: 8px;
  cursor: pointer;
  font-size: 16px;
  color: #333;
  background: #f8f9fa;
  border-radius: 8px;
  border-left: 3px solid transparent;
  transition: all 0.3s ease;
}

.room-type-item:last-child {
  margin-bottom: 0;
}

.room-type-item:hover {
  background: #e8f4f8;
  border-left-color: #409eff;
  transform: translateX(4px);
}

.room-type-item.active {
  background: linear-gradient(90deg, #e8f4f8 0%, #ffffff 100%);
  border-left-color: #409eff;
  color: #409eff;
  font-weight: 600;
  box-shadow: 0 2px 8px rgba(64, 158, 255, 0.15);
}

.room-image-container {
  flex: 1;
  background: #ffffff;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 20px;
  box-shadow: 0 2px 12px rgba(0, 0, 0, 0.06);
  overflow: hidden;
}

.room-image {
  width: 100%;
  height: 100%;
  border-radius: 8px;
  object-fit: contain;
}

.image-error,
.no-image {
  color: #909399;
  font-size: 16px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.image-error::before,
.no-image::before {
  content: "📷";
  font-size: 48px;
  opacity: 0.5;
}
</style>
