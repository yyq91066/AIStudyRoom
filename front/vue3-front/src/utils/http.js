import axios from "axios";
//axios基础封装
const httpInstance=axios.create({
    baseURL:"http://localhost:8080",
    timeout:5000
})
//配置拦截器

//请求拦截器
httpInstance.interceptors.request.use(
    config=>{return config},
        e => Promise.reject(e)

)
//响应拦截器
httpInstance.interceptors.response.use(
    res=>res.data,
        e =>{return Promise.reject(e)}
)
export default httpInstance