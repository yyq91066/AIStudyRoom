import {defineStore} from "pinia";
import {getRoomTypesAPI} from "@/apis/roomTypes.js"
import {ref} from "vue"
export const useRoomTypesStore =defineStore('roomTypes',() => {
    const roomTypesList = ref([])
    const getRoomTypes = async () => {
        const res = await getRoomTypesAPI()
        roomTypesList.value = res.data
    }
    return {roomTypesList,getRoomTypes}

})