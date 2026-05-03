<template>
  <div class="auth-page">
    <section class="auth-hero">
      <div class="auth-copy">
        <span class="eyebrow">Create Account</span>
        <h1>注册新账号</h1>
        <p>
          注册页面沿用同一套视觉骨架，减少页面切换时的割裂感，也方便后续继续加校验说明与帮助文案。
        </p>
        <div class="auth-points">
          <span>邮箱验证</span>
          <span>昵称设置</span>
          <span>快速返回登录</span>
        </div>
      </div>

      <el-card class="auth-card">
        <h2>创建账户</h2>
        <p class="subtitle">填写完整信息后即可完成注册。</p>

        <el-form :model="form" class="auth-form">
          <el-form-item>
            <el-input v-model="form.nickname" placeholder="请输入昵称" />
          </el-form-item>
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
          <el-form-item>
            <el-input v-model="form.email" placeholder="请输入邮箱" />
          </el-form-item>
          <el-form-item>
            <div class="code-row">
              <el-input v-model="form.code" placeholder="请输入邮箱验证码" />
              <el-button type="primary" :disabled="countdown > 0" class="code-btn" @click="sendCode">
                {{ countdown > 0 ? `${countdown}s 后重发` : '发送验证码' }}
              </el-button>
            </div>
          </el-form-item>

          <el-button type="primary" class="btn primary-btn" @click="handleRegister">注册</el-button>
          <el-button class="btn ghost-btn" @click="goLogin">返回登录</el-button>
        </el-form>
      </el-card>
    </section>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { getRegisterCodeAPI, UserRegisterAPI } from '@/apis/login.js'
import { notifySuccess, notifyWarning } from '@/utils/feedback'

const router = useRouter()

const form = reactive({
  username: '',
  password: '',
  email: '',
  code: '',
  nickname: '',
})

const countdown = ref(0)
let timer = null

const checkEmail = (email) => /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)

const sendCode = () => {
  if (!form.email) {
    notifyWarning('请输入邮箱')
    return
  }

  if (!checkEmail(form.email)) {
    notifyWarning('邮箱格式不正确')
    return
  }

  getRegisterCodeAPI(form.email)
  notifySuccess('验证码已发送')

  countdown.value = 60
  timer = setInterval(() => {
    countdown.value--
    if (countdown.value <= 0) {
      clearInterval(timer)
      timer = null
    }
  }, 1000)
}

const handleRegister = () => {
  if (!form.username || !form.password || !form.email || !form.code) {
    notifyWarning('请填写完整信息')
    return
  }

  if (!checkEmail(form.email)) {
    notifyWarning('邮箱格式不正确')
    return
  }

  UserRegisterAPI(form)
  notifySuccess('注册成功')
  setTimeout(() => {
    router.push('/login')
  }, 800)
}

const goLogin = () => {
  router.push('/login')
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
  grid-template-columns: minmax(0, 1.1fr) minmax(360px, 460px);
  gap: 24px;
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

.auth-form :deep(.el-form-item) {
  margin-bottom: 18px;
}

.auth-form :deep(.el-input__wrapper) {
  min-height: 50px;
  border-radius: 16px;
  box-shadow: none;
  background: rgba(255, 255, 255, 0.82);
}

.code-row {
  width: 100%;
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto;
  gap: 10px;
}

.code-btn {
  min-width: 120px;
  height: 50px;
  border-radius: 16px;
  border: none;
  background: linear-gradient(135deg, #2f7f73, #6fb8ac);
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

  .code-row {
    grid-template-columns: 1fr;
  }
}
</style>
