const app = getApp();

Page({
  data: {
    userInfo: null,
    cacheSize: 0
  },

  onShow() {
    this.loadUserInfo();
    this.calculateCacheSize();
  },

  loadUserInfo() {
    const userInfo = wx.getStorageSync('userInfo');
    const token = wx.getStorageSync('token');
    
    if (token) {
      this.setData({
        userInfo: userInfo || {}
      });
      
      wx.request({
        url: `${app.globalData.API_BASE_URL}/user/UserInfo`,
        method: 'GET',
        header: {
          'Authorization': token
        },
        success: (res) => {
          if (res.data.code === 200) {
            wx.setStorageSync('userInfo', res.data.data);
            this.setData({
              userInfo: res.data.data
            });
          }
        },
        fail: (err) => {
          console.error('获取用户信息失败:', err);
        }
      });
    } else {
      this.setData({
        userInfo: null
      });
    }
  },

  calculateCacheSize() {
    wx.getStorageInfo({
      success: (res) => {
        const size = Math.round(res.currentSize / 1024);
        this.setData({
          cacheSize: size
        });
      }
    });
  },

  clearCache() {
    wx.showModal({
      title: '提示',
      content: '确定要清除缓存吗？',
      success: (res) => {
        if (res.confirm) {
          wx.clearStorageSync();
          wx.showToast({
            title: '清除成功',
            icon: 'success'
          });
          this.setData({
            cacheSize: 0
          });
        }
      }
    });
  },

  goToUpdateProfile() {
    wx.showToast({
      title: '功能开发中...',
      icon: 'none'
    });
  },

  goToUpdatePassword() {
    wx.showToast({
      title: '功能开发中...',
      icon: 'none'
    });
  },

  onLogout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          wx.clearStorageSync();
          app.globalData.token = '';
          app.globalData.userInfo = null;
          wx.reLaunch({
            url: '/pages/login/login'
          });
        }
      }
    });
  }
});
