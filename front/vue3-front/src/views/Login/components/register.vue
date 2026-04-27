<!-- views/Register.vue -->
<template>
  <div class="page">
    <el-card class="box">
      <h2>注册</h2>

      <el-form :model="form">
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

            <el-button
                type="primary"
                :disabled="countdown > 0"
                @click="sendCode"
            >
              {{ countdown > 0 ? `${countdown}s后重发` : '发送验证码' }}
            </el-button>
          </div>
        </el-form-item>

        <el-button type="primary" class="btn" @click="handleRegister">
          注册
        </el-button>

        <el-button class="btn" @click="goLogin">
          返回登录
        </el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {getRegisterCodeAPI,UserRegisterAPI} from '@/apis/login.js'

const router = useRouter()

const form = reactive({
  username: '',
  password: '',
  email: '',
  code: ''
})

const countdown = ref(0)
let timer = null

const checkEmail = (email) => {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
}

const sendCode =  () => {
  if (!form.email) {
    ElMessage.warning('请输入邮箱')
    return
  }

  if (!checkEmail(form.email)) {
    ElMessage.warning('邮箱格式不正确')
    return
  }

  // TODO: 调后端发送验证码接口
  // await api.sendEmailCode(form.email)
  getRegisterCodeAPI(form.email)
  ElMessage.success('验证码已发送')

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
    ElMessage.warning('请填写完整信息')
    return
  }

  if (!checkEmail(form.email)) {
    ElMessage.warning('邮箱格式不正确')
    return
  }

  // TODO: 调注册接口，后端校验验证码是否正确
  // await api.register(form)
  UserRegisterAPI(form)
  ElMessage.success('注册成功')
  setTimeout(() => {
    router.push('/login')

  }, 800)
}

const goLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
.page {
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: #f5f7fa;
}

.box {
  width: 400px;
}

.code-row {
  display: flex;
  gap: 10px;
  width: 100%;
}

.btn {
  width: 100%;
  margin-top: 12px;
  margin-left: 0;
}
</style>