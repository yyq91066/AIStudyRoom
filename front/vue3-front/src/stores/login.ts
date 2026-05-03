import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import { UserLoginAPI } from '@/apis/login'

export const useLoginStore = defineStore('login', () => {
  const storedUserInfo = localStorage.getItem('login_user')
  const userInfo = ref(storedUserInfo ? JSON.parse(storedUserInfo) : null)

  const isLogin = computed(() => !!userInfo.value?.token)

  async function login(loginDTO: any) {
    const res: any = await UserLoginAPI(loginDTO)
    const loginData = res?.data

    if (!loginData?.token) {
      throw new Error(res?.message || 'Login failed')
    }

    userInfo.value = loginData
    localStorage.setItem('token1', loginData.token)
    localStorage.setItem('login_user', JSON.stringify(loginData))

    return loginData
  }

  function logout() {
    userInfo.value = null
    localStorage.removeItem('token1')
    localStorage.removeItem('login_user')
  }

  return { userInfo, isLogin, login, logout }
})
