<script setup lang="ts">
import { computed, onMounted } from 'vue'
import HomeCategory from '@/views/Home/components/HomeCategory.vue'
import HomeNew from '@/views/Home/components/HomeNew.vue'
import HomeProducts from '@/views/Home/components/HomeProducts.vue'
import { useRoomTypesStore } from '@/stores/roomTypes.js'

const roomTypesStore = useRoomTypesStore()

const roomTypeCount = computed(() => roomTypesStore.roomTypesList?.length || 0)

onMounted(() => {
  roomTypesStore.getRoomTypes()
})
</script>

<template>
  <div class="page-shell home-page">
    <section class="page-hero hero-grid">
      <div class="hero-copy">
        <span class="eyebrow">Smart Study Space</span>
        <h1 class="section-title">自习室预约、导览与智能服务前台。</h1>
        <p class="section-desc">一隅智学天地，不负每段耕耘，智慧自习室诚邀开启静心学习时光。</p>
        <div class="chip-list">
          <span class="chip">在线预约</span>
          <span class="chip">房型切换</span>
          <span class="chip">AI 助手</span>
          <span class="chip">数据报表</span>
        </div>
      </div>

      <div class="hero-side">
        <div class="stat-grid">
          <article class="stat-card">
            <span>空间类型</span>
            <strong>{{ roomTypeCount }}</strong>
          </article>
          <article class="stat-card">
            <span>服务模块</span>
            <strong>04</strong>
          </article>
          <article class="stat-card">
            <span>体验目标</span>
            <strong>统一、清晰、好看</strong>
          </article>
        </div>
      </div>
    </section>

    <HomeCategory />
    <!--    <HomeNew />-->
    <HomeProducts />
  </div>
</template>

<style scoped>
.home-page {
  display: grid;
  gap: 24px;
}

.hero-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.4fr) minmax(320px, 0.9fr);
  gap: 28px;
  align-items: center;
}

.hero-copy {
  display: grid;
  gap: 18px;
}

.hero-side {
  display: grid;
  align-items: end;
}

@media (max-width: 900px) {
  .hero-grid {
    grid-template-columns: 1fr;
  }
}
</style>
