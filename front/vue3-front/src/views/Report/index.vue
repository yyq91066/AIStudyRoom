<template>
  <div class="page-shell report-page">
    <section class="page-hero report-hero">
      <div>
        <span class="eyebrow">Report Center</span>
        <h1 class="section-title">把图表页改造成真正的运营驾驶舱。</h1>
      </div>
      <p class="section-desc">
        日期筛选、图表展示与导出操作被重新组织为三段式布局，避免控件零散堆在页面顶部。
      </p>
    </section>

    <section class="report-grid">
      <div class="section-card control-card">
        <div class="control-head">
          <h2>时间筛选</h2>
          <span>支持查询与导出 Excel</span>
        </div>

        <div class="date-row">
          <el-date-picker
            v-model="startDate"
            type="date"
            placeholder="选择开始日期"
            value-format="YYYY-MM-DD"
          />
          <el-date-picker
            v-model="endDate"
            type="date"
            placeholder="选择结束日期"
            value-format="YYYY-MM-DD"
          />
        </div>

        <div class="btn-row">
          <el-button type="primary" @click="fetchData">查询数据</el-button>
          <el-button @click="exportReport">导出 Excel</el-button>
        </div>
      </div>

      <div class="section-card summary-card">
        <span class="eyebrow">Data Summary</span>
        <strong>{{ startDate }} 至 {{ endDate }}</strong>
        <p>这里适合作为后续补充总订单数、营收、峰值日期等摘要信息的位置。</p>
      </div>
    </section>

    <section class="section-card chart-card">
      <div class="chart-head">
        <div>
          <h2>订单趋势图</h2>
          <p>柱状图区域加上卡片容器和标题说明，视觉上更完整。</p>
        </div>
      </div>
      <div ref="chartRef" class="chart-canvas"></div>
    </section>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import * as echarts from 'echarts'
import axios from 'axios'

const chartRef = ref(null)
let myChart = null
const startDate = ref('2026-03-01')
const endDate = ref('2026-04-01')

const initChart = () => {
  myChart = echarts.init(chartRef.value)
  myChart.setOption({
    title: { text: '自习室订单统计', left: 8, top: 10, textStyle: { color: '#1f2529' } },
    tooltip: { trigger: 'axis' },
    grid: { left: 42, right: 24, top: 64, bottom: 40 },
    xAxis: {
      type: 'category',
      data: [],
      axisLine: { lineStyle: { color: '#b8a28a' } },
      axisLabel: { color: '#5d6570' },
    },
    yAxis: {
      type: 'value',
      axisLine: { show: false },
      splitLine: { lineStyle: { color: 'rgba(143, 77, 22, 0.08)' } },
      axisLabel: { color: '#5d6570' },
    },
    series: [
      {
        name: '订单量',
        type: 'bar',
        data: [],
        itemStyle: {
          borderRadius: [10, 10, 0, 0],
          color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
            { offset: 0, color: '#2f7f73' },
            { offset: 1, color: '#bc6c25' },
          ]),
        },
      },
    ],
  })
}

const fetchData = async () => {
  try {
    const response = await axios.get('http://localhost:8080/report/count', {
      params: {
        startDate: startDate.value,
        endDate: endDate.value,
      },
    })

    const resultData = response.data.data
    const dates = resultData.orderDate.split(',')
    const counts = resultData.orderCount.split(',').map(Number)

    myChart.setOption({
      xAxis: { data: dates },
      series: [{ data: counts }],
    })
  } catch (error) {
    console.error('数据请求失败:', error)
  }
}

const exportReport = async () => {
  if (!startDate.value || !endDate.value) {
    alert('请先选择开始和结束日期')
    return
  }

  try {
    const response = await axios.get('http://localhost:8080/report/export/download', {
      params: {
        startDate: startDate.value,
        endDate: endDate.value,
      },
      responseType: 'blob',
      timeout: 30000,
    })

    const blob = new Blob([response.data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
    })

    const url = window.URL.createObjectURL(blob)
    const link = document.createElement('a')
    link.href = url

    const disposition = response.headers['content-disposition']
    let filename = '订单报表.xlsx'
    if (disposition && disposition.includes('filename=')) {
      const filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/
      const matches = filenameRegex.exec(disposition)
      if (matches?.[1]) {
        filename = matches[1].replace(/['"]/g, '')
      }
    } else {
      filename = `订单报表_${new Date().getTime()}.xlsx`
    }

    link.download = filename
    document.body.appendChild(link)
    link.click()
    document.body.removeChild(link)
    window.URL.revokeObjectURL(url)
  } catch (error) {
    console.error('导出失败:', error)
    alert('导出失败，请检查网络或稍后重试')
  }
}

onMounted(() => {
  initChart()
  fetchData()
  window.addEventListener('resize', () => myChart?.resize())
})
</script>

<style scoped>
.report-page {
  display: grid;
  gap: 24px;
}

.report-grid {
  display: grid;
  grid-template-columns: minmax(0, 1.2fr) minmax(280px, 0.8fr);
  gap: 20px;
}

.control-card,
.summary-card,
.chart-card {
  padding: 28px;
}

.control-head {
  display: flex;
  align-items: end;
  justify-content: space-between;
  gap: 12px;
  margin-bottom: 18px;
}

.control-head h2,
.chart-head h2 {
  margin: 0;
  font-size: 28px;
}

.control-head span,
.chart-head p,
.summary-card p {
  color: var(--text-soft);
}

.date-row,
.btn-row {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 12px;
}

.btn-row {
  margin-top: 14px;
}

.btn-row :deep(.el-button),
.date-row :deep(.el-input__wrapper) {
  min-height: 48px;
  border-radius: 16px;
  box-shadow: none;
}

.btn-row :deep(.el-button--primary) {
  border: none;
  background: linear-gradient(135deg, #bc6c25, #f6bd60);
  color: #1f2529;
}

.summary-card {
  display: grid;
  align-content: center;
  gap: 12px;
}

.summary-card strong {
  font-size: 26px;
  line-height: 1.3;
}

.summary-card p {
  margin: 0;
  line-height: 1.8;
}

.chart-head {
  margin-bottom: 16px;
}

.chart-head p {
  margin: 8px 0 0;
}

.chart-canvas {
  width: 100%;
  height: 460px;
}

@media (max-width: 900px) {
  .report-grid,
  .date-row,
  .btn-row {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .control-card,
  .summary-card,
  .chart-card {
    padding: 20px;
  }

  .chart-canvas {
    height: 340px;
  }
}
</style>
