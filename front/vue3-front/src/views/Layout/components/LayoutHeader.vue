<script setup>
import {onMounted} from "vue";
import  {ref} from "vue";
import {useScroll} from '@vueuse/core'
import { useCategoryStore } from "@/stores/category";
const {y}=useScroll(window)
//使用pinia中的数据
const categoryStore=useCategoryStore()
onMounted(() => {
  console.log(categoryStore.categoryLIst,"categoryStore")
})
</script>

<template>
  <div class="page">
    <!-- 顶部导航栏 -->
    <header class="top-nav">
      <div class="container nav-content">
        <!-- 左侧 logo -->
        <div class="logo">
          <RouterLink to="/">智学空间</RouterLink>
          {{y}}
        </div>

        <!-- 中间导航 -->
        <ul class="nav-list">
          <li v-for="item in categoryStore.categoryLIst" :key="item.id">
            <RouterLink to="/" active-class="active">
              {{ item.name }}
            </RouterLink>
          </li>
        </ul>

        <!-- 右侧功能区 -->
        <div class="nav-right">
          <div class="search-box">
            <input type="text" placeholder="搜索" />
          </div>
          <span class="icon">⤴</span>
          <span class="icon">🛒</span>
          <span class="icon">🔔</span>
          <span class="avatar">Y</span>
        </div>
      </div>
    </header>


  </div>

</template>


<style scoped >
.page {
  background: #f5f5f5;
  min-height: 100vh;
}

.container {
  width: 1200px;
  margin: 0 auto;
}

/* 顶部导航 */
.top-nav {
  height: 72px;
  background: #050505;
  color: #fff;
}

.nav-content {
  height: 100%;
  display: flex;
  align-items: center;
}

/* logo */
.logo {
  margin-right: 40px;
  flex-shrink: 0;
}

.logo a {
  color: #fff;
  font-size: 32px;
  font-weight: 700;
  text-decoration: none;
  letter-spacing: 1px;
}

/* 中间导航 */
.nav-list {
  display: flex;
  align-items: center;
  gap: 14px;
  list-style: none;
  margin: 0;
  padding: 0;
  flex: 1;
}

.nav-list li a {
  display: block;
  padding: 10px 18px;
  color: #fff;
  text-decoration: none;
  font-size: 18px;
  border-radius: 18px;
  transition: all 0.3s ease;
}

.nav-list li a:hover {
  background: rgba(63, 209, 255, 0.18);
  color: #42d7ff;
}

.nav-list li a.active {
  background: rgba(63, 209, 255, 0.2);
  color: #42d7ff;
  font-weight: 600;
}

/* 右侧 */
.nav-right {
  display: flex;
  align-items: center;
  gap: 16px;
  flex-shrink: 0;
}

.search-box {
  width: 180px;
  height: 38px;
  border: 1px solid #3d3d3d;
  border-radius: 20px;
  background: #111;
  overflow: hidden;
}

.search-box input {
  width: 100%;
  height: 100%;
  border: none;
  outline: none;
  background: transparent;
  color: #fff;
  padding: 0 14px;
}

.search-box input::placeholder {
  color: #888;
}

.icon {
  font-size: 22px;
  cursor: pointer;
  transition: 0.3s;
}

.icon:hover {
  transform: translateY(-1px);
  color: #42d7ff;
}

.avatar {
  width: 36px;
  height: 36px;
  background: linear-gradient(135deg, #42d7ff, #6a5cff);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: bold;
  cursor: pointer;
}

/* 用户横幅区域 */
.user-banner {
  background: linear-gradient(to right, #1c1c1f, #161619);
  color: #fff;
  padding: 36px 0 24px;
}

.banner-content {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
}

.user-left {
  display: flex;
  align-items: center;
  gap: 24px;
}

.user-avatar {
  width: 120px;
  height: 120px;
  border-radius: 50%;
  background: linear-gradient(135deg, #63f3ff, #5773ff);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 42px;
  font-weight: bold;
  box-shadow: 0 0 20px rgba(76, 185, 255, 0.35);
}

.user-info h2 {
  margin: 0 0 26px;
  font-size: 40px;
  font-weight: 700;
}

.user-tabs {
  display: flex;
  gap: 36px;
}

.tab-item {
  font-size: 18px;
  color: #d8d8d8;
  cursor: pointer;
  padding-bottom: 10px;
  border-bottom: 3px solid transparent;
  transition: 0.3s;
}

.tab-item:hover {
  color: #42d7ff;
}

.active-tab {
  color: #fff;
  border-bottom-color: #5b63ff;
}

/* 右下角圆按钮 */
.user-actions {
  display: flex;
  gap: 18px;
}

.circle-btn {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  border: none;
  background: linear-gradient(135deg, #34d6ff, #6384ff);
  color: #fff;
  font-size: 24px;
  cursor: pointer;
  box-shadow: 0 8px 20px rgba(68, 154, 255, 0.28);
  transition: 0.3s;
}

.circle-btn:hover {
  transform: translateY(-3px);
}
</style>
<!--//把智学空间标题和遍历出来的Category放到一行，智学空间是主logo  Category的内容是导航栏 帮我美化一下-->