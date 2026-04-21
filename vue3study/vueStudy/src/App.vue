<script setup lang="ts">
import {onMounted, ref} from "vue";
import axios from "axios";
const tableData = ref([])
const getTableDate = async ()=>{
  const response = await axios.get("http://localhost:8080/api/room/getAllRooms")
  tableData.value = response.data.data
}
onMounted(()=>getTableDate())
</script>
<template>
 <div class="app">
   <el-table :data="tableData">

     <el-table-column label="id" prop="id" width="100px"> </el-table-column>
     <el-table-column label="storeId" prop="storeId" width="100px"></el-table-column>
      <el-table-column label="capacity" prop="capacity" width="100px"></el-table-column>
     <el-table-column label="roomName" prop="roomName" width="120px"></el-table-column>
     <el-table-column label="status" prop="status" width="100px"></el-table-column>
     <el-table-column label="description" prop="description" width="300px "></el-table-column>
     <el-table-column label="remark" prop="remark" width="100px"></el-table-column>
      <el-table-column label="pic" prop="pic" width="100px"></el-table-column>
     <el-table-column>
       <template #default="scope" >
       <el-button type="primary" >编辑</el-button>
       <el-button type="danger">删除</el-button>
        </template>
     </el-table-column>
   </el-table>

 </div>

</template>

<style scoped></style>
