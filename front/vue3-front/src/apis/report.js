import httpInstance from '@/utils/http.js'

async function getReportAPI({ startDate, endDate }) {
  try {
    const res = httpInstance({
      url: '/report/count',
      params: {
        startDate: startDate,
        endDate: endDate,
      },
    })
    return res
  } catch (error) {
    console.error('数据请求失败:', error)
  }
}
export { getReportAPI }