<template>
  <div ref="chartRef" style="width: 600px; height: 400px;"></div>
  <div>
    <el-button @click="exportReport">导出excel</el-button>
  </div>
  <div>
    <el-date-picker
      v-model="startDate"
      type="date"
      placeholder="选择开始日期"
      value-format="YYYY-MM-DD"
      style="width: 200px;"
    ></el-date-picker>
    <el-date-picker
      v-model="endDate"
      type="date"
      placeholder="选择结束日期"
      value-format="YYYY-MM-DD"
      style="width: 200px;"
    ></el-date-picker>
    <el-button @click="fetchData">查询</el-button>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'
import axios from 'axios'

const chartRef = ref(null)
let myChart = null
let startDate = ref('2026-01-01')
let endDate = ref('2026-01-01')
// 初始化图表
const initChart = () => {
  myChart = echarts.init(chartRef.value)
  myChart.setOption({
    title: { text: '自习室订单统计' },
    tooltip: {},
    xAxis: { type: 'category', data: [] },
    yAxis: { type: 'value' },
    series: [{ name: '订单量', type: 'bar', data: [] }]
  })
}

// 请求数据
const fetchData = async () => {
  try {
    const response = await axios.get('http://localhost:8080/report/count', {
      params: {
        startDate: startDate.value,
        endDate: endDate.value
      }
    })

    // 1. 获取嵌套在 data 字段里的实际数据
    const resultData = response.data.data

    // 2. 处理字符串转数组
    // 后端返回的是 "2026-03-15,2026-03-16..."
    // 我们使用 split(',') 将其切割成数组
    const dates = resultData.orderDate.split(',')
    const counts = resultData.orderCount.split(',').map(Number) // map(Number) 把字符串数字转为真正的数字

    // 3. 更新图表
    myChart.setOption({
      xAxis: { data: dates },
      series: [{ data: counts }]
    })

  } catch (error) {
    console.error('数据请求失败:', error)
  }
}
// 导出报表
const exportReport1 = async () => {
  try {
    const response = await axios.get('http://localhost:8080/report/export/download', {
      params: {
        startDate: startDate.value,
        endDate: endDate.value
      }
    })
    alert('导出成功 文件路径:'+"“"+response.data+"”")
  } catch (error) {
    console.error('请求失败:', error)
  }
}
const exportReport = async () => {
  if (!startDate.value || !endDate.value) {
    alert('请先选择开始和结束日期')
    return
  }
  let exportLoading = ref(false)
  exportLoading.value = true

  try {
    const response = await axios.get('http://localhost:8080/report/export/download', {
      params: {
        // 拼接成 LocalDateTime 格式
        startDate: startDate.value,
        endDate: endDate.value
      },
      responseType: 'blob',  // ✅ 关键：接收二进制文件流
      timeout: 30000
    })

    // ✅ 创建 Blob 并下载
    const blob = new Blob([response.data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    })

    // 创建下载链接
    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url

    // 从响应头获取文件名，或使用默认名
    const disposition = response.headers['content-disposition']
    let filename = '订单报表.xlsx'
    if (disposition && disposition.includes('filename=')) {
      const filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/
      const matches = filenameRegex.exec(disposition)
      if (matches != null && matches[1]) {
        filename = matches[1].replace(/['"]/g, '')
      }
    } else {
      // 默认文件名带时间戳
      filename = `订单报表_${new Date().getTime()}.xlsx`
    }

    link.download = filename
    document.body.appendChild(link)
    link.click()  // 触发下载

    // 清理
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)

  } catch (error) {
    console.error('导出失败:', error)
    alert('导出失败，请检查网络或联系管理员')
  } finally {
    exportLoading.value = false
  }
}

onMounted(() => {
  initChart()
  fetchData()
})
</script>