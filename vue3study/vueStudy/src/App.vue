<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
import Edit from "@/components/Edit.vue";
import PiniaTest from "@/components/piniaTest.vue";
import {useRoomsStore} from "../stores/rooms.ts";
const roomsStore = useRoomsStore()
// const tableData = ref([])
// const getTableDate = async ()=>{
//   const response = await axios.get("http://localhost:8080/api/room/getAllRooms")
//   tableData.value = response.data.data
// }
// const getTableDate =  ()=>{
//   const res=roomsStore.getRooms()
//   console.log(res,"res")
//   tableData.value = roomsStore.rooms
//   console.log(
//       roomsStore.rooms,
//       "rommsget"
//   )
// }
const deleteRoom = async (id:number) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/room/DeleteRoomById`,{
      params:{
        id
      }
    })
    // getTableDate()
    roomsStore.getRooms()
    alert('删除成功')
  } catch (error) {
    console.error('请求失败:', error)
  }
}
// onMounted(()=>getTableDate())
onMounted(() => {
  roomsStore.getRooms()
})

const EditRef=ref()
const onEdit = (row) => {
  EditRef.value.open(row)
}
</script>
<template>
  <div class="page-header">
    <h1>房间列表</h1>
  </div>
 <div class="app">
   <el-table :data="roomsStore.rooms">

     <el-table-column label="id" prop="id" width="100px"> </el-table-column>
     <el-table-column label="storeId" prop="storeId" width="100px"></el-table-column>
      <el-table-column label="capacity" prop="capacity" width="100px"></el-table-column>
     <el-table-column label="roomName" prop="roomName" width="120px"></el-table-column>
     <el-table-column label="status" prop="status" width="100px"></el-table-column>
     <el-table-column label="description" prop="description" width="300px "></el-table-column>
     <el-table-column label="remark" prop="remark" width="100px"></el-table-column>
<!--      <el-table-column label="pic" prop="pic" width="100px"></el-table-column>-->
     <!-- 图片列：使用作用域插槽 -->
     <el-table-column label="pic" width="100">
       <template #default="scope">
         <el-image
             v-if="scope.row.pic"
             :src="scope.row.pic"
             style="width: 50px; height: 50px"
             fit="cover"
             :preview-src-list="[scope.row.pic]"
         />
         <span v-else>无图片</span>
       </template>
     </el-table-column>
     <el-table-column>
       <template #default="scope" >
       <el-button  @click="onEdit(scope.row)" type="primary" >编辑</el-button>
       <el-button  @click="deleteRoom(scope.row.id)" type="danger">删除</el-button>
        </template>
     </el-table-column>
   </el-table>

 </div>
<Edit  ref="EditRef" @on-update="roomsStore.getRooms" />
  <PiniaTest />
</template>

<style scoped>
.page-header {
  padding: 15px;
  border-bottom: 5px solid #e4e7ed;
  text-align: center;
}
.page-header h1 {
  margin: 1px;
  font-size: 24px;
  color: #303133;
}
</style>
