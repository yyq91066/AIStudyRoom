<!-- views/Login.vue -->
<template>
  <div class="page">
    <el-card class="box">
      <h2>登录</h2>

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

        <el-button type="primary" class="btn" @click="handleLogin">
          登录
        </el-button>

        <el-button class="btn" @click="goRegister">
          注册
        </el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import {UserRegisterAPI} from "@/apis/login.js";

const router = useRouter()

const form = reactive({
  username: '',
  password: ''
})

const handleLogin = () => {
  if (!form.username || !form.password) {
    ElMessage.warning('请输入用户名和密码')
    return
  }

  // : 调登录接口
  UserRegisterAPI(form)
  ElMessage.success('登录成功')
  router.push('/')
}

const goRegister = () => {
  router.push('/register')
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
  width: 360px;
}

.btn {
  width: 100%;
  margin-top: 12px;
  margin-left: 0;
}
</style>