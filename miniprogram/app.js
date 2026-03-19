App({
  onLaunch() {
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    this.globalData.API_BASE_URL = 'http://localhost:8080/api';
    
    const token = wx.getStorageSync('token');
    if (token) {
      this.globalData.token = token;
    }
  },
  
  globalData: {
    userInfo: null,
    token: '',
    API_BASE_URL: 'http://localhost:8080/api'
  }
})
