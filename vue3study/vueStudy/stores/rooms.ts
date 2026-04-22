import {defineStore} from "pinia";
import {computed, ref} from "vue";
import axios from "axios";
export const useRoomsStore =defineStore("rooms", ()=>{
    const rooms=ref([])
    const getRooms = async ()=>{
        const response = await axios.get("http://localhost:8080/api/room/getAllRooms")
        rooms.value = response.data.data
        console.log(response.data.data,"store中的")
    }
    const roomCount = computed(() => rooms.value.length)
    return{
        rooms,
        getRooms,
        roomCount
    }
})