<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { useCategoryStore } from '@/stores/category'
import { useLoginStore } from '@/stores/login'

const categoryStore = useCategoryStore()
const userStore = useLoginStore()
const router = useRouter()

const isLogin = computed(() => userStore.isLogin)
const displayName = computed(() => {
  const info = userStore.userInfo
  if (!info) return '访客'
  return info.user?.nickname || info.user?.username || info.nickname || info.username || '用户'
})

const navItems = computed(() => {
  if (categoryStore.categoryLIst?.length) {
    return categoryStore.categoryLIst
  }

  return [
    { id: 'home', name: '首页', path: '/' },
    { id: 'category', name: '空间类型', path: '/category' },
    { id: 'aiservice', name: 'AI 服务', path: '/aiservice' },
    { id: 'report', name: '报表中心', path: '/report' },
  ]
})
const logout = () => {
  userStore.logout()
  router.push('/login').catch(err => {
    console.error('Navigation failed:', err)
    // Fallback: force redirect
    // window.location.href = '/login'
  })
}
</script>

<template>
  <header class="header-shell">
    <div class="page-shell">
      <div class="header-card">
        <RouterLink to="/" class="brand">
          <span class="brand-mark">智</span>
          <div>
            <strong>智学空间</strong>
            <span>自习室预约与智能服务平台</span>
          </div>
        </RouterLink>

        <nav class="nav-list">
          <RouterLink
            v-for="item in navItems"
            :key="item.id"
            :to="item.path"
            class="nav-link"
            active-class="active"
          >
            {{ item.name }}
          </RouterLink>
        </nav>

        <div class="header-actions">
          <RouterLink to="/aiservice" class="quick-entry">智能客服</RouterLink>
          <template v-if="isLogin">
            <RouterLink to="/user" class="quick-entry">个人中心</RouterLink>
            <span class="welcome">你好，{{ displayName }}</span>
            <button class="logout-btn" type="button" @click="logout">退出登录</button>
          </template>
          <RouterLink v-else to="/login" class="login-btn">登录 / 注册</RouterLink>
        </div>
      </div>
    </div>
  </header>
</template>

<style scoped>
.header-shell {
  position: sticky;
  top: 0;
  z-index: 20;
  padding: 16px 0 0;
}

.header-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 18px;
  padding: 16px 20px;
  background: rgba(22, 39, 42, 0.88);
  color: #fff9f1;
  border-radius: 26px;
  border: 1px solid rgba(255, 255, 255, 0.08);
  box-shadow: 0 18px 40px rgba(17, 25, 29, 0.18);
  backdrop-filter: blur(16px);
}

.brand {
  display: flex;
  align-items: center;
  gap: 14px;
  min-width: 0;
}

.brand-mark {
  display: grid;
  place-items: center;
  width: 48px;
  height: 48px;
  border-radius: 16px;
  background: linear-gradient(135deg, #f6bd60, #bc6c25);
  color: #1f2529;
  font-size: 22px;
  font-weight: 800;
}

.brand strong {
  display: block;
  font-size: 19px;
  letter-spacing: 0.02em;
}

.brand span:last-child {
  display: block;
  margin-top: 2px;
  color: rgba(255, 249, 241, 0.72);
  font-size: 12px;
}

.nav-list {
  display: flex;
  flex: 1;
  flex-wrap: wrap;
  justify-content: center;
  gap: 10px;
}

.nav-link {
  padding: 10px 16px;
  border-radius: 999px;
  color: rgba(255, 249, 241, 0.82);
  transition: 0.25s ease;
}

.nav-link:hover,
.nav-link.active {
  color: #fff;
  background: rgba(255, 255, 255, 0.12);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.quick-entry,
.login-btn,
.logout-btn {
  height: 42px;
  padding: 0 16px;
  border: none;
  border-radius: 999px;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 14px;
  font-weight: 700;
}

.quick-entry {
  background: rgba(255, 255, 255, 0.1);
  color: #fff;
}

.login-btn,
.logout-btn {
  background: linear-gradient(135deg, #f6bd60, #bc6c25);
  color: #1f2529;
  cursor: pointer;
}

.welcome {
  max-width: 150px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  color: rgba(255, 249, 241, 0.82);
}

@media (max-width: 960px) {
  .header-card {
    flex-wrap: wrap;
    justify-content: center;
  }

  .nav-list {
    order: 3;
    width: 100%;
    justify-content: flex-start;
  }
}

@media (max-width: 768px) {
  .header-shell {
    padding-top: 10px;
  }

  .header-card {
    padding: 14px;
    border-radius: 22px;
  }

  .brand {
    width: 100%;
  }

  .header-actions {
    width: 100%;
    justify-content: space-between;
  }

  .quick-entry,
  .login-btn,
  .logout-btn {
    flex: 1;
  }
}
</style>
