<template>
  <div class="auth-page">
    <section class="auth-hero">
      <div class="auth-copy">
        <span class="eyebrow">Welcome Back</span>
        <h1>登录智学空间</h1>
        <p>统一登录页的结构、色彩和留白，让身份验证流程也保持产品感，而不是单独一块“默认表单”。</p>
        <div class="auth-points">
          <span>预约管理</span>
          <span>AI 咨询</span>
          <span>报表查看</span>
        </div>
      </div>

      <el-card class="auth-card">
        <h2>账号登录</h2>
        <p class="subtitle">输入账号信息后即可进入平台首页。</p>

        <el-form :model="form" class="auth-form">
          <el-form-item>
            <el-input v-model="form.username" placeholder="请输入用户名" />
          </el-form-item>

          <el-form-item>
            <el-input
              v-model="form.password"
              type="password"
              placeholder="请输入密码"
              show-password
            />
          </el-form-item>

          <el-button type="primary" class="btn primary-btn" @click="handleLogin">
            登录
          </el-button>

          <el-button class="btn ghost-btn" @click="goRegister">注册新账号</el-button>
        </el-form>
      </el-card>
    </section>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { useLoginStore } from '@/stores/login'

const loginStore = useLoginStore()
const router = useRouter()

const form = reactive({
  username: '',
  password: '',
})

const handleLogin = async () => {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }

  await loginStore.login(form)
  const res = loginStore.userInfo
  localStorage.setItem('token1', res.token)
  ElMessage.success('登录成功')
  await router.push('/')
}

const goRegister = () => {
  router.push('/register')
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  padding: 24px;
}

.auth-hero {
  width: min(1120px, 100%);
  display: grid;
  grid-template-columns: minmax(0, 1.1fr) minmax(360px, 420px);
  gap: 24px;
  align-items: stretch;
}

.auth-copy,
.auth-card {
  border-radius: 30px;
  border: 1px solid rgba(143, 77, 22, 0.12);
  box-shadow: var(--shadow-lg);
}

.auth-copy {
  padding: 40px;
  background:
    radial-gradient(circle at top right, rgba(47, 127, 115, 0.2), transparent 30%),
    linear-gradient(145deg, rgba(255, 250, 242, 0.95), rgba(244, 232, 211, 0.94));
  display: grid;
  align-content: center;
  gap: 18px;
}

.auth-copy h1 {
  margin: 0;
  font-size: clamp(38px, 5vw, 64px);
  line-height: 1.02;
  letter-spacing: -0.04em;
}

.auth-copy p {
  margin: 0;
  max-width: 520px;
  color: var(--text-soft);
  font-size: 16px;
  line-height: 1.8;
}

.auth-points {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.auth-points span {
  padding: 10px 16px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.76);
  border: 1px solid rgba(143, 77, 22, 0.12);
  font-weight: 700;
}

.auth-card {
  padding: 28px;
  background: rgba(255, 252, 247, 0.88);
  backdrop-filter: blur(16px);
}

.auth-card h2 {
  margin: 0;
  font-size: 30px;
}

.subtitle {
  margin: 10px 0 24px;
  color: var(--text-soft);
}

.auth-form {
  display: grid;
}

.auth-form :deep(.el-form-item) {
  margin-bottom: 18px;
}

.auth-form :deep(.el-input__wrapper) {
  min-height: 50px;
  border-radius: 16px;
  box-shadow: none;
  background: rgba(255, 255, 255, 0.82);
}

.btn {
  width: 100%;
  height: 48px;
  margin: 0;
  border-radius: 16px;
  font-weight: 700;
}

.primary-btn {
  border: none;
  background: linear-gradient(135deg, #bc6c25, #f6bd60);
  color: #1f2529;
}

.ghost-btn {
  margin-top: 12px;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(143, 77, 22, 0.16);
  color: var(--text-main);
}

@media (max-width: 900px) {
  .auth-hero {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .auth-page {
    padding: 14px;
  }

  .auth-copy,
  .auth-card {
    padding: 22px;
    border-radius: 24px;
  }
}
</style>
