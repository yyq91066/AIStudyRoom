import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router/index.js";
//axios基础封装
const httpInstance=axios.create({
    baseURL:"http://localhost:8080",
    timeout:5000
})
//配置拦截器

//请求拦截器
httpInstance.interceptors.request.use(
    config=>{
        //添加token
        const token = localStorage.getItem('token1')
        console.log("请求拦截器拦截到的token:",token)
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }
        return config
    },
        e => Promise.reject(e)

)
//响应拦截器
httpInstance.interceptors.response.use(
    res=>res.data,
        err =>{
        // return Promise.reject(e)

            //响应拦截器，若发现401，则跳转到登录页面
            if (err.response?.status === 401) {
                ElMessage.error('登录已过期，请重新登录')

                localStorage.removeItem('token1')

                router.push('/login')
            }

            return Promise.reject(err)
    }
)
export default httpInstance