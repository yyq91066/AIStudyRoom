import { defineStore } from 'pinia'
import { getAllStoresAPI } from '@/apis/store'
import {ref} from 'vue'
export const useStoreStore=defineStore('store',() => {
  const stores = ref(null)
  async function getAllStores(){
    const res = await getAllStoresAPI()
    stores.value = res.data
  }
  return {stores,getAllStores}
})