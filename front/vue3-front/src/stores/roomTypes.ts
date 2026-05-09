import {defineStore} from "pinia";
import {getRoomTypesAPI} from "@/apis/roomTypes.js"
import {ref} from "vue"
export const useRoomTypesStore =defineStore('roomTypes',() => {
    const roomTypesList = ref(null)
    //防止短期内重复加载
    //设置上次fetch时间
    const lastFetchTime=ref(0)
    //缓存时间 30min
    const cacheDuration=30*60*1000
    const getRoomTypes = async () => {
        const now =Date.now()
      if (roomTypesList.value&&lastFetchTime.value&&(now-lastFetchTime.value)<cacheDuration)
        {return}
        const res = await getRoomTypesAPI()
        roomTypesList.value = res.data
        lastFetchTime.value = now
    }
    return {roomTypesList,getRoomTypes}

})