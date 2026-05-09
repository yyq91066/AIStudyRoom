import { ElNotification } from 'element-plus'

const notify = (type: 'success' | 'warning' | 'error' | 'info', message: string, title: string) =>
  ElNotification({
    title,
    message,
    type,
    position: 'top-right',
    duration: type === 'error' ? 3200 : 2400,
  })

export const notifySuccess = (message: string, title = '成功') =>
  notify('success', message, title)

export const notifyWarning = (message: string, title = '提示') =>
  notify('warning', message, title)

export const notifyError = (message: string, title = '错误') =>
  notify('error', message, title)

export const notifyInfo = (message: string, title = '通知') =>
  notify('info', message, title)
