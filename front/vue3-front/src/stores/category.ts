import { ref} from 'vue'
import { defineStore } from 'pinia'
import {getCategoryAPI} from '@/apis/layout.js'

export const useCategoryStore
    = defineStore('category', () => {
    //state
    const categoryLIst = ref([])
    //action
    const getCategory = async ()=>{
        const res=await getCategoryAPI()
        categoryLIst.value = res.data
    }

    return { categoryLIst ,getCategory}

})
