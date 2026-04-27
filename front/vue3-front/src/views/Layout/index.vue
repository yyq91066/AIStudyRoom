<script setup lang="ts">
import { onMounted } from 'vue'
import LayoutHeader from './components/LayoutHeader.vue'
import LayoutFooter from './components/LayoutFooter.vue'
import { useCategoryStore } from '@/stores/category'

const categoryStore = useCategoryStore()

onMounted(() => {
  categoryStore.getCategory()
})
</script>

<template>
  <div class="layout-shell">
    <LayoutHeader />
    <main class="layout-main">
      <RouterView />
    </main>
    <LayoutFooter />
  </div>
</template>

<style scoped>
.layout-shell {
  min-height: 100vh;
}

.layout-main {
  padding: 24px 0 56px;
}

@media (max-width: 768px) {
  .layout-main {
    padding: 16px 0 36px;
  }
}
</style>
