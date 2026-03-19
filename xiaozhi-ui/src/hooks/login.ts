import {reactive,onMounted} from 'vue'
import axios,{AxiosError} from 'axios'
import { lo } from 'element-plus/es/locales.mjs'

export default function(){
//   let dogList = reactive<string[]>([])

  // 方法
  async function getRegisterCode(email:string){
    try {
      // 发请求
      let data = await axios.get('http://localhost:8080/api/user/send-register-code',{
        params:{
            email:email
        }
      })
      // 维护数据
    //   dogList.push(data.message)
        return data.data
    } catch (error) {
      // 处理错误
      const err = <AxiosError>error
      console.log(err.message)
    }
  }
  //requestbody格式
//   {      
//                 username:registerDTO.username,
//                 email:registerDTO.email,
//                 code:registerDTO.code,
//                 password:registerDTO.password
//   }
  async function UserRegister(registerDTO:any){
    try {
        let data=await axios.post('http://localhost:8080/api/user/register',registerDTO)
        return data.data
    } catch (error) {
        const err = <AxiosError>error
        console.log(err.message)
    }


  }


  //向外部暴露数据
  return {getRegisterCode,UserRegister}
}