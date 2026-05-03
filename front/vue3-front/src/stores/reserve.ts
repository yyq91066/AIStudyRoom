import {ref} from 'vue'
import {defineStore} from 'pinia'
import {reserveAPI} from '@/apis/reserve'
export const useReserveStore =defineStore("reserve", (
  () => {
    const reserveInfo = ref(null)
    async function reserve(reserve1:any){
      const res = await reserveAPI(reserve1)
      console.log("reserve请求",res)
      return res
    }

    return {reserveInfo,reserve}
  }
))