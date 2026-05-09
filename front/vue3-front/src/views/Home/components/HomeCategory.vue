<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRoomTypesStore } from '@/stores/roomTypes.js'

const roomTypesStore = useRoomTypesStore()
const activeIndex = ref(0)

const activeRoom = computed(() => roomTypesStore.roomTypesList?.[activeIndex.value])

onMounted(() => {
  roomTypesStore.getRoomTypes()
})

const selectRoomType = (index) => {
  activeIndex.value = index
}
</script>

<template>
  <section class="section-card category-section">
    <div class="section-head">
      <div>
        <span class="eyebrow">Room Showcase</span>
        <h2>空间类型展示</h2>
      </div>
      <p>落座即静心，入馆便求知，智慧自习室・智学空间欢迎你。</p>
    </div>

    <div class="category-layout">
      <div class="room-types-list">
        <button
          v-for="(item, index) in roomTypesStore.roomTypesList"
          :key="item.id"
          class="room-type-item"
          :class="{ active: activeIndex === index }"
          type="button"
          @click="selectRoomType(index)"
        >
          <span class="room-index">0{{ index + 1 }}</span>
          <span class="room-name">{{ item.name }}</span>
        </button>
      </div>

      <div class="room-image-container">
        <template v-if="activeRoom?.picture">
          <el-image :src="activeRoom.picture" fit="cover" class="room-image">
            <template #error>
              <div class="empty-state">图片加载失败，请检查资源地址。</div>
            </template>
          </el-image>
          <div class="image-mask">
            <strong>{{ activeRoom.name }}</strong>
            <span>适合安静学习、短时停留与高专注场景展示。</span>
          </div>
        </template>
        <div v-else class="empty-state">
          <div>
            <strong>暂无空间图片</strong>
            <p>当前房型还没有关联图片资源。</p>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.category-section {
  padding: 28px;
}

.section-head {
  display: flex;
  align-items: end;
  justify-content: space-between;
  gap: 20px;
  margin-bottom: 24px;
}

.section-head h2 {
  margin: 14px 0 0;
  font-size: 32px;
}

.section-head p {
  max-width: 420px;
  margin: 0;
  color: var(--text-soft);
  line-height: 1.7;
}

.category-layout {
  display: grid;
  grid-template-columns: minmax(220px, 280px) minmax(0, 1fr);
  gap: 20px;
}

.room-types-list {
  display: grid;
  gap: 12px;
}

.room-type-item {
  padding: 18px 18px 18px 16px;
  border: 1px solid rgba(143, 77, 22, 0.12);
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.66);
  display: grid;
  gap: 8px;
  text-align: left;
  cursor: pointer;
  transition: 0.25s ease;
}

.room-type-item:hover,
.room-type-item.active {
  transform: translateX(4px);
  border-color: rgba(188, 108, 37, 0.36);
  box-shadow: var(--shadow-sm);
}

.room-index {
  color: var(--text-faint);
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.08em;
}

.room-name {
  font-size: 18px;
  font-weight: 700;
  color: var(--text-main);
}

.room-image-container {
  position: relative;
  min-height: 420px;
  overflow: hidden;
  border-radius: 26px;
  background: linear-gradient(135deg, #efe6d9, #fff8ee);
}

.room-image {
  width: 100%;
  height: 100%;
}

.image-mask {
  position: absolute;
  left: 20px;
  right: 20px;
  bottom: 20px;
  padding: 18px;
  border-radius: 18px;
  background: rgba(24, 41, 45, 0.72);
  color: #fff8f0;
  backdrop-filter: blur(12px);
}

.image-mask strong {
  display: block;
  margin-bottom: 6px;
  font-size: 20px;
}

.image-mask span,
.empty-state p {
  margin: 0;
  color: rgba(255, 248, 240, 0.8);
}

@media (max-width: 900px) {
  .section-head,
  .category-layout {
    grid-template-columns: 1fr;
    display: grid;
  }
}

@media (max-width: 768px) {
  .category-section {
    padding: 20px;
  }

  .room-image-container {
    min-height: 300px;
  }
}
</style>
