import httpInstance from '@/utils/http.js'
async function getAllStoresAPI(){
  try {
    const res=await httpInstance({
      url:'/api/store/getAllStores',
      method:'GET'
    })
    return res
  }catch ( error)
    {
      console.log(error.message)
      throw error
    }
}
export {getAllStoresAPI}