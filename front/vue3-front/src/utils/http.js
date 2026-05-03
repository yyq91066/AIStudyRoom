import axios from 'axios'
import router from '@/router/index.js'
import { notifyError } from '@/utils/feedback'

const httpInstance = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 5000,
})

httpInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token1')
    if (token) {
      config.headers.Authorization = token
    }
    return config
  },
  (error) => Promise.reject(error),
)

httpInstance.interceptors.response.use(
  (res) => res.data,
  (err) => {
    if (err.response?.status === 401) {
      notifyError('Login expired, please sign in again')
      localStorage.removeItem('token1')
      localStorage.removeItem('login_user')
      router.push('/login')
    }

    return Promise.reject(err)
  },
)

export default httpInstance
