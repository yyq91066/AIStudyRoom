<script setup lang="ts">
import {ref} from 'vue'
import axios from "axios";
const dialogVisible = ref(false)
const form =ref( {
  id: '',
  storeId: '',
  capacity: '',
  roomName: '',
  status: '',
  description: '',
  remark: '',
  pic: ''
})
const emits=defineEmits(["on-update"])
const open = (row) => {
  console.log(row)
  form.value.id = row.id
  form.value.storeId = row.storeId
  form.value.capacity = row.capacity
  form.value.roomName = row.roomName
  form.value.status = row.status
  form.value.description = row.description
  form.value.remark = row.remark
  form.value.pic = row.pic
  dialogVisible.value = true
}
const onSubmit = async () => {

  const res= await axios.post('http://localhost:8080/api/room/updateRoom', form.value)

    dialogVisible.value = false

  emits("on-update")

}
defineExpose( {
  open
})
</script>
<template>
 <el-dialog  v-model="dialogVisible" title="编辑" width="500px">
   <el-form label-width="100px">
     <el-form-item label="storeId">
       <el-input v-model="form.storeId"></el-input>
     </el-form-item>

     <el-form-item label="capacity">
       <el-input v-model="form.capacity"></el-input>
     </el-form-item>

     <el-form-item label="roomName">
       <el-input v-model="form.roomName"></el-input>
     </el-form-item>

     <el-form-item label="status">
       <el-input v-model="form.status"></el-input>
     </el-form-item>

     <el-form-item label="description">
       <el-input v-model="form.description"></el-input>
     </el-form-item>

      <el-form-item label="remark">
       <el-input v-model="form.remark"></el-input>
     </el-form-item>

       <el-form-item label="pic">
       <el-input v-model="form.pic"></el-input>
     </el-form-item>
   </el-form>
   <template #footer>
     <el-button @click="dialogVisible = false">取消</el-button>
     <el-button type="primary" @click="onSubmit">确定</el-button>
   </template>
 </el-dialog>
</template>

<style scoped></style>
