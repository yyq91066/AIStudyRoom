import {defineStore} from "pinia";
import {ref,computed} from 'vue'
import {UserLoginAPI} from "@/apis/login";

export const useLoginStore =defineStore(
    "login",
    () =>{
        const userInfo = ref(null)

        const isLogin = computed(() => !!userInfo.value)

        async function login(loginDTO: any) {
            const res = await UserLoginAPI(loginDTO)
            userInfo.value = res
          console.log(res)
        }
        function logout() {
            userInfo.value = null
          localStorage.removeItem('token1')
        }
        return {userInfo, isLogin, login, logout}
    }

)