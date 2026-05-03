<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useReserveStore } from '@/stores/reserve.ts'
import { useLoginStore } from '@/stores/login.ts'
import { useStoreStore } from '@/stores/store.ts'
import { useRoomTypesStore } from '@/stores/roomTypes.ts'
const storeStore = useStoreStore()
const loginStore = useLoginStore()
const reserveStore = useReserveStore()
const RoomTypesStore = useRoomTypesStore()
const reserveInfo = ref({
  userId: '',
  storeId: '',
  roomId: '',
  startTime: '',
  endTime: '',
  status: '',
  createTime: '',
  remark1: '',
  remark2: '',
})
const reserve = async () => {
  const res = await reserveStore.reserve(reserveInfo.value)
  console.log('预约成功', res)
  alert(res)
}
onMounted(async () => {
  reserveInfo.value.userId = loginStore.userInfo.user.id
  console.log('reserve的reserveInfo', loginStore.userInfo.user.id)
  storeStore.getAllStores()
  console.log('门店列表', storeStore.stores)
  RoomTypesStore.getRoomTypes()
})
</script>

<template>
  <el-form class="reserve-elform">
    <el-tag>用户id</el-tag>
    <el-form-item>
      <el-input v-model="reserveInfo.userId" placeholder="用户id" />
    </el-form-item>
    <el-form-item>
      <el-input v-model="reserveInfo.storeId" placeholder="门店id" />
    </el-form-item>
    <el-form-item>
      <el-select v-model="reserveInfo.storeId" placeholder="请选择门店">
        <el-option
          v-for="item in storeStore.stores"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-input v-model="reserveInfo.roomId" placeholder="房间id" />
    </el-form-item>
    <el-form-item>
      <el-select v-model="reserveInfo.roomId" placeholder="请选择房间类型">
        <el-option
          v-for="item in RoomTypesStore.roomTypesList"
          :key="item.id"
          :label="item.name"
          :value="item.id"
        />
      </el-select>
    </el-form-item>
    <el-form-item>
      <el-date-picker
        v-model="reserveInfo.startTime"
        type="datetime"
        placeholder="请选择开始时间"
        format="YYYY-MM-DDTHH:mm:ss"
        value-format="YYYY-MM-DDTHH:mm:ss"
      />
    </el-form-item>
    <el-form-item>
      <!--      <el-input v-model="reserveInfo.endTime" placeholder="请输入结束时间" />-->
      <el-date-picker
        v-model="reserveInfo.endTime"
        placeholder="请选择结束时间"
        type="datetime"
        value-format="YYYY-MM-DDTHH:mm:ss"
      />
    </el-form-item>
  </el-form>
  <div class="button-el">
    <el-button @click="reserve" type="primary">预约</el-button>
  </div>
</template>

<style scoped>
.reserve-elform {
  width: 300px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  background-color: #f5f5f5;
}
.button-el {
  display: flex;
  justify-content: center; /* 水平居中 */
  margin: 20px 0; /* 上下留一点间距，更好看 */
}
</style>
