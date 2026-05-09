<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useLoginStore } from '@/stores/login'
import httpInstance from '@/utils/http.js'
import { ElMessage } from 'element-plus'

const loginStore = useLoginStore()
const isEditing = ref(false)
const formRef = ref()
const loading = ref(false)
const systemFile = ref({
  name: 'touxiang',
  type: '',
  url: '',
  description: '',
  reamrk: '',
})
// 表单数据
const formData = ref({
  id: '',
  username: '',
  nickname: '',
  email: '',
  phone: '',
  userPic: '',
})

// 表单验证规则
const rules = {
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 1, max: 10, message: '昵称长度在 1 到 10 个字符', trigger: 'blur' },
  ],
  email: [
    { required: true, message: '请输入邮箱地址', trigger: 'blur' },
    { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] },
  ],
  phone: [{ pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }],
}

// 获取用户信息
const getUserInfo = async () => {
  try {
    loading.value = true
    const res = await httpInstance.get('/api/user/UserInfo')
    if (res.code === 200) {
      const user = res.data
      formData.value = {
        id: user.id || '',
        username: user.username || '',
        nickname: user.nickname || '',
        email: user.email || '',
        phone: user.phone || '',
        userPic: user.userPic || '',
      }
      // 更新store中的用户信息
      loginStore.userInfo = { ...loginStore.userInfo, user }
      localStorage.setItem('login_user', JSON.stringify(loginStore.userInfo))
    } else {
      ElMessage.error(res.message || '获取用户信息失败')
    }
  } catch (error) {
    console.error('获取用户信息失败:', error)
    ElMessage.error('获取用户信息失败')
  } finally {
    loading.value = false
  }
}

// 编辑模式
const handleEdit = () => {
  isEditing.value = true
}

// 取消编辑
const handleCancel = () => {
  isEditing.value = false
  getUserInfo() // 重新获取原始数据
}

// 保存修改
const handleSubmit = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid: boolean) => {
    if (valid) {
      try {
        loading.value = true
        const res = await httpInstance.put('/api/user/update', formData.value)
        if (res.code === 200) {
          ElMessage.success('修改成功')
          isEditing.value = false
          getUserInfo() // 重新获取最新数据
        } else {
          ElMessage.error(res.message || '修改失败')
        }
      } catch (error) {
        console.error('修改失败:', error)
        ElMessage.error('修改失败')
      } finally {
        loading.value = false
      }
    }
  })
}

// 上传头像
const handleAvatarUpload = async (event: Event) => {
  const target = event.target as HTMLInputElement
  const file = target.files?.[0]
  if (!file) return

  // 验证文件类型
  if (!file.type.startsWith('image/')) {
    ElMessage.error('请选择图片文件')
    return
  }

  // 验证文件大小（限制5MB）
  if (file.size > 5 * 1024 * 1024) {
    ElMessage.error('图片大小不能超过5MB')
    return
  }

  try {
    loading.value = true
    const formData = new FormData()

    formData.append('file', file)
    formData.append('systemFIle', systemFile.value)

    // 这里假设有上传接口，实际需要根据项目情况调整
    const res = await httpInstance.post('/api/user/updateAvatar', formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })

    if (res.code === 200) {
      ElMessage.success('头像上传成功')
      getUserInfo()
    } else {
      ElMessage.error(res.message || '上传失败1')
    }
  } catch (error) {
    console.error('上传失败11:', error)
    ElMessage.error('上传失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  getUserInfo()
})
</script>

<template>
  <div class="page-shell">
    <div class="profile-container">
      <!-- 头部卡片 -->
      <div class="profile-header section-card">
        <div class="header-content">
          <div class="avatar-section">
            <div class="avatar-wrapper">
              <img v-if="formData.userPic" :src="formData.userPic" alt="头像" class="avatar-img" />
              <div v-else class="avatar-placeholder">
                {{ formData.nickname?.charAt(0) || formData.username?.charAt(0) || '用' }}
              </div>
              <label v-if="isEditing" class="avatar-upload-btn" title="点击更换头像">
                <input
                  type="file"
                  accept="image/*"
                  @change="handleAvatarUpload"
                  style="display: none"
                />
                <span class="upload-icon">📷</span>
              </label>
            </div>
            <div class="user-basic-info">
              <h2 class="username">{{ formData.nickname || formData.username }}</h2>
              <p class="user-id">ID: {{ loginStore.userInfo?.user?.id || '-' }}</p>
            </div>
          </div>
        </div>
      </div>

      <!-- 信息表单卡片 -->
      <div class="profile-form section-card">
        <div class="form-header">
          <h3 class="form-title">个人信息</h3>
          <div class="form-actions">
            <el-button v-if="!isEditing" type="primary" @click="handleEdit" class="edit-btn">
              编辑资料
            </el-button>
            <template v-else>
              <el-button @click="handleCancel">取消</el-button>
              <el-button type="primary" @click="handleSubmit" :loading="loading"> 保存 </el-button>
            </template>
          </div>
        </div>

        <el-form
          ref="formRef"
          :model="formData"
          :rules="rules"
          label-width="100px"
          class="info-form"
          :disabled="!isEditing"
        >
          <el-form-item label="用户名" prop="username">
            <el-input v-model="formData.username" disabled />
          </el-form-item>

          <el-form-item label="昵称" prop="nickname">
            <el-input v-model="formData.nickname" placeholder="请输入昵称" />
          </el-form-item>

          <el-form-item label="邮箱" prop="email">
            <el-input v-model="formData.email" placeholder="请输入邮箱" />
          </el-form-item>

          <el-form-item label="手机号" prop="phone">
            <el-input v-model="formData.phone" placeholder="请输入手机号" />
          </el-form-item>

          <el-form-item label="头像URL" v-if="isEditing">
            <el-input v-model="formData.userPic" placeholder="头像URL" readonly />
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.profile-container {
  display: grid;
  gap: 24px;
  padding: 24px 0;
}

/* 头部卡片 */
.profile-header {
  padding: 32px;
}

.header-content {
  display: flex;
  align-items: center;
}

.avatar-section {
  display: flex;
  align-items: center;
  gap: 24px;
}

.avatar-wrapper {
  position: relative;
  width: 100px;
  height: 100px;
}

.avatar-img,
.avatar-placeholder {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
  border: 3px solid var(--brand);
  box-shadow: var(--shadow-sm);
}

.avatar-placeholder {
  display: grid;
  place-items: center;
  background: linear-gradient(135deg, var(--brand), var(--brand-deep));
  color: #fff;
  font-size: 36px;
  font-weight: 700;
}

.avatar-upload-btn {
  position: absolute;
  bottom: 0;
  right: 0;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: var(--accent);
  color: #fff;
  display: grid;
  place-items: center;
  cursor: pointer;
  box-shadow: var(--shadow-sm);
  transition: transform 0.2s;
}

.avatar-upload-btn:hover {
  transform: scale(1.1);
}

.upload-icon {
  font-size: 16px;
}

.user-basic-info {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.username {
  margin: 0;
  font-size: 28px;
  font-weight: 700;
  color: var(--text-main);
}

.user-id {
  margin: 0;
  font-size: 14px;
  color: var(--text-faint);
}

/* 表单卡片 */
.profile-form {
  padding: 32px;
}

.form-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
  padding-bottom: 16px;
  border-bottom: 1px solid var(--line);
}

.form-title {
  margin: 0;
  font-size: 20px;
  font-weight: 700;
  color: var(--text-main);
}

.form-actions {
  display: flex;
  gap: 12px;
}

.edit-btn {
  background: linear-gradient(135deg, var(--brand), var(--brand-deep));
  border: none;
  color: #fff;
}

.info-form {
  max-width: 600px;
}

.info-form :deep(.el-form-item__label) {
  color: var(--text-soft);
  font-weight: 600;
}

.info-form :deep(.el-input.is-disabled .el-input__wrapper) {
  background-color: var(--bg-soft);
}

@media (max-width: 768px) {
  .profile-container {
    padding: 16px 0;
  }

  .profile-header,
  .profile-form {
    padding: 20px;
  }

  .avatar-section {
    flex-direction: column;
    text-align: center;
  }

  .form-header {
    flex-direction: column;
    gap: 16px;
    align-items: flex-start;
  }

  .form-actions {
    width: 100%;
    justify-content: flex-end;
  }
}
</style>
