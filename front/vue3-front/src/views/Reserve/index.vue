<script setup lang="ts">
import { onMounted, ref, computed } from 'vue'
import { useReserveStore } from '@/stores/reserve.ts'
import { useLoginStore } from '@/stores/login.ts'
import { useStoreStore } from '@/stores/store.ts'
import { useRoomTypesStore } from '@/stores/roomTypes.ts'
import httpInstance from '@/utils/http.js'
import { ElMessage } from 'element-plus'

const storeStore = useStoreStore()
const loginStore = useLoginStore()
const reserveStore = useReserveStore()
const RoomTypesStore = useRoomTypesStore()

// 当前激活的Tab
const activeTab = ref('reserve')

// 预约表单数据
const reserveInfo = ref({
  userId: '',
  storeId: '',
  roomId: '',
  startTime: '',
  endTime: '',
})

// 房间列表
const roomList = ref([])
const loading = ref(false)

// 预约记录
const reservationList = ref([])
const userReservationList = ref([])

// 判断是否为管理员（后期可根据实际权限调整）
const isAdmin = computed(() => {
  return loginStore.userInfo?.user?.id === 1 // 示例：ID为1的是管理员
})

// 获取可用房间
const getAvailableRooms = async () => {
  if (!reserveInfo.value.storeId) {
    roomList.value = []
    return
  }
  
  try {
    const res = await httpInstance.get('/api/room/getByStoreOrTypeAvailable', {
      params: {
        storeId: reserveInfo.value.storeId,
        roomTypeId: reserveInfo.value.roomId // 这里roomId实际是roomTypeId
      }
    })
    if (res.code === 200) {
      roomList.value = res.data || []
    }
  } catch (error) {
    console.error('获取房间失败:', error)
  }
}

// 提交预约
const handleReserve = async () => {
  // 验证必填项
  if (!reserveInfo.value.storeId || !reserveInfo.value.roomId || 
      !reserveInfo.value.startTime || !reserveInfo.value.endTime) {
    ElMessage.warning('请填写完整的预约信息')
    return
  }

  // 验证时间
  const start = new Date(reserveInfo.value.startTime)
  const end = new Date(reserveInfo.value.endTime)
  if (start >= end) {
    ElMessage.warning('结束时间必须晚于开始时间')
    return
  }

  try {
    loading.value = true
    const data = {
      userId: loginStore.userInfo.user.id,
      storeId: reserveInfo.value.storeId,
      roomId: reserveInfo.value.roomId, // 这里应该是具体的房间ID
      startTime: reserveInfo.value.startTime,
      endTime: reserveInfo.value.endTime,
    }
    
    const res = await httpInstance.post('/api/reservation/create', data)
    if (res.code === 200 || res.message === '预约成功') {
      ElMessage.success('预约成功')
      // 重置表单
      resetForm()
      // 刷新预约记录
      getUserReservations()
      if (isAdmin.value) {
        getAllReservations()
      }
    } else {
      ElMessage.error(res.message || '预约失败')
    }
  } catch (error) {
    console.error('预约失败:', error)
    ElMessage.error('预约失败')
  } finally {
    loading.value = false
  }
}

// 重置表单
const resetForm = () => {
  reserveInfo.value = {
    userId: '',
    storeId: '',
    roomId: '',
    startTime: '',
    endTime: '',
  }
  roomList.value = []
}

// 获取用户预约记录
const getUserReservations = async () => {
  try {
    const userId = loginStore.userInfo.user.id
    const res = await httpInstance.get(`/api/reservation/user/${userId}`)
    console.log('预约记录原始响应:', res)
    
    // HTTP拦截器已返回res.data，这里res就是数组
    const dataList = Array.isArray(res) ? res : []
    console.log('提取的数据列表:', dataList)
    
    // 后端返回的已经是驼峰命名，直接使用
    userReservationList.value = dataList.map(item => ({
      id: item.id,
      userId: item.userId || item.user_id,
      storeId: item.storeId || item.store_id,
      roomId: item.roomId || item.room_id,
      startTime: item.startTime || item.start_time,
      endTime: item.endTime || item.end_time,
      status: item.status,
      createTime: item.createTime || item.create_time,
      remark1: item.remark1,
      remark2: item.remark2,
    }))
    console.log('转换后的数据:', userReservationList.value)
  } catch (error) {
    console.error('获取预约记录失败:', error)
  }
}

// 获取所有预约记录（管理员）
const getAllReservations = async () => {
  try {
    // TODO: 待后端提供获取所有预约的接口后取消注释
    // const res = await httpInstance.get('/api/reservation/all')
    // const dataList = Array.isArray(res) ? res : []
    // 
    // reservationList.value = dataList.map(item => ({
    //   id: item.id,
    //   userId: item.userId || item.user_id,
    //   storeId: item.storeId || item.store_id,
    //   roomId: item.roomId || item.room_id,
    //   startTime: item.startTime || item.start_time,
    //   endTime: item.endTime || item.end_time,
    //   status: item.status,
    //   createTime: item.createTime || item.create_time,
    //   remark1: item.remark1,
    //   remark2: item.remark2,
    // }))
    
    // 暂时使用空数组，后端接口就绪后替换
    reservationList.value = []
  } catch (error) {
    console.error('获取所有预约记录失败:', error)
  }
}

// 监听门店选择变化
const handleStoreChange = () => {
  reserveInfo.value.roomId = ''
  getAvailableRooms()
}

// 监听开始时间变化，自动计算结束时间
const handleStartTimeChange = (value: string) => {
  if (value) {
    const startTime = new Date(value)
    const endTime = new Date(startTime.getTime() + 2 * 60 * 60 * 1000) // +2小时
    // 使用本地时间格式化，避免时区问题
    const year = endTime.getFullYear()
    const month = String(endTime.getMonth() + 1).padStart(2, '0')
    const day = String(endTime.getDate()).padStart(2, '0')
    const hours = String(endTime.getHours()).padStart(2, '0')
    const minutes = String(endTime.getMinutes()).padStart(2, '0')
    const seconds = String(endTime.getSeconds()).padStart(2, '0')
    reserveInfo.value.endTime = `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`
  }
}

// 格式化时间
const formatTime = (time: string) => {
  if (!time) return '-'
  return new Date(time).toLocaleString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
  })
}

// 获取状态文本
const getStatusText = (status: number) => {
  const statusMap = {
    0: '已预约',
    1: '已完成',
    2: '已取消',
  }
  return statusMap[status] || '未知'
}

onMounted(async () => {
  storeStore.getAllStores()
  RoomTypesStore.getRoomTypes()
  getUserReservations()
  if (isAdmin.value) {
    getAllReservations()
  }
})
</script>

<template>
  <div class="page-shell">
    <div class="reserve-container">
      <!-- 头部标题 -->
      <div class="reserve-header section-card">
        <h2 class="page-title">自习室预约</h2>
        <p class="page-desc">选择心仪的自习空间，开启高效学习之旅</p>
      </div>

      <!-- Tab切换 -->
      <el-tabs v-model="activeTab" class="reserve-tabs">
        <!-- 预约Tab -->
        <el-tab-pane label="立即预约" name="reserve">
          <div class="reserve-form section-card">
            <el-form :model="reserveInfo" label-width="100px" class="form-content">
              <el-form-item label="选择门店">
                <el-select 
                  v-model="reserveInfo.storeId" 
                  placeholder="请选择门店"
                  @change="handleStoreChange"
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in storeStore.stores"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>

              <el-form-item label="房间类型">
                <el-select 
                  v-model="reserveInfo.roomId" 
                  placeholder="请选择房间类型"
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in RoomTypesStore.roomTypesList"
                    :key="item.id"
                    :label="`${item.name} (${item.capacity}人)`"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>

              <el-form-item label="可用房间" v-if="roomList.length > 0">
                <el-select 
                  v-model="reserveInfo.roomId" 
                  placeholder="请选择具体房间"
                  style="width: 100%"
                >
                  <el-option
                    v-for="room in roomList"
                    :key="room.id"
                    :label="room.roomName"
                    :value="room.id"
                  />
                </el-select>
              </el-form-item>

              <el-form-item label="开始时间">
                <el-date-picker
                  v-model="reserveInfo.startTime"
                  type="datetime"
                  placeholder="请选择开始时间"
                  format="YYYY-MM-DD HH:mm:ss"
                  value-format="YYYY-MM-DDTHH:mm:ss"
                  @change="handleStartTimeChange"
                  style="width: 100%"
                />
              </el-form-item>

              <el-form-item label="结束时间">
                <el-date-picker
                  v-model="reserveInfo.endTime"
                  type="datetime"
                  placeholder="请选择结束时间"
                  format="YYYY-MM-DD HH:mm:ss"
                  value-format="YYYY-MM-DDTHH:mm:ss"
                  style="width: 100%"
                />
              </el-form-item>

              <el-form-item>
                <el-button 
                  type="primary" 
                  @click="handleReserve" 
                  :loading="loading"
                  class="submit-btn"
                >
                  确认预约
                </el-button>
                <el-button @click="resetForm">重置</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-tab-pane>

        <!-- 我的预约Tab -->
        <el-tab-pane label="我的预约" name="my-reservations">
          <div class="reservation-list section-card">
            <div class="list-header">
              <h3>预约记录</h3>
              <el-button @click="getUserReservations" size="small">刷新</el-button>
            </div>
            
            <el-table 
              :data="userReservationList" 
              style="width: 100%"
              empty-text="暂无预约记录"
            >
              <el-table-column prop="remark1" label="房间名称" width="150" />
              <el-table-column label="开始时间" width="180">
                <template #default="{ row }">
                  {{ formatTime(row.startTime) }}
                </template>
              </el-table-column>
              <el-table-column label="结束时间" width="180">
                <template #default="{ row }">
                  {{ formatTime(row.endTime) }}
                </template>
              </el-table-column>
              <el-table-column label="状态" width="100">
                <template #default="{ row }">
                  <el-tag :type="row.status === 0 ? 'success' : row.status === 1 ? 'info' : 'danger'">
                    {{ getStatusText(row.status) }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column label="创建时间" width="180">
                <template #default="{ row }">
                  {{ formatTime(row.createTime) }}
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>

        <!-- 全部预约Tab（仅管理员可见） -->
        <el-tab-pane v-if="isAdmin" label="全部预约" name="all-reservations">
          <div class="reservation-list section-card">
            <div class="list-header">
              <h3>全部预约记录</h3>
              <el-button @click="getAllReservations" size="small">刷新</el-button>
            </div>
            
            <el-empty v-if="reservationList.length === 0" description="暂无数据" />
            <el-table v-else :data="reservationList" style="width: 100%">
              <el-table-column prop="id" label="订单ID" width="100" />
              <el-table-column prop="userId" label="用户ID" width="100" />
              <el-table-column prop="remark1" label="房间名称" width="150" />
              <el-table-column label="开始时间" width="180">
                <template #default="{ row }">
                  {{ formatTime(row.startTime) }}
                </template>
              </el-table-column>
              <el-table-column label="结束时间" width="180">
                <template #default="{ row }">
                  {{ formatTime(row.endTime) }}
                </template>
              </el-table-column>
              <el-table-column label="状态" width="100">
                <template #default="{ row }">
                  <el-tag :type="row.status === 0 ? 'success' : row.status === 1 ? 'info' : 'danger'">
                    {{ getStatusText(row.status) }}
                  </el-tag>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<style scoped>
.reserve-container {
  display: grid;
  gap: 24px;
  padding: 24px 0;
}

.reserve-header {
  padding: 32px;
  text-align: center;
}

.page-title {
  margin: 0 0 8px;
  font-size: 32px;
  font-weight: 700;
  color: var(--text-main);
}

.page-desc {
  margin: 0;
  font-size: 16px;
  color: var(--text-soft);
}

.reserve-tabs {
  background: transparent;
}

.reserve-tabs :deep(.el-tabs__header) {
  margin-bottom: 24px;
}

.reserve-tabs :deep(.el-tabs__nav-wrap::after) {
  height: 1px;
  background-color: var(--line);
}

.reserve-form {
  padding: 32px;
}

.form-content {
  max-width: 600px;
  margin: 0 auto;
}

.submit-btn {
  background: linear-gradient(135deg, var(--brand), var(--brand-deep));
  border: none;
  min-width: 120px;
}

.reservation-list {
  padding: 32px;
}

.list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 24px;
}

.list-header h3 {
  margin: 0;
  font-size: 20px;
  font-weight: 700;
  color: var(--text-main);
}

@media (max-width: 768px) {
  .reserve-container {
    padding: 16px 0;
  }

  .reserve-header,
  .reserve-form,
  .reservation-list {
    padding: 20px;
  }

  .page-title {
    font-size: 24px;
  }

  .form-content {
    max-width: 100%;
  }

  .list-header {
    flex-direction: column;
    gap: 12px;
    align-items: flex-start;
  }
}
</style>
