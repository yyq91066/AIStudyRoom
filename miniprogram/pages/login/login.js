const app = getApp();

Page({
  data: {
    username: '',
    password: ''
  },

  onUsernameInput(e) {
    this.setData({
      username: e.detail.value
    });
  },

  onPasswordInput(e) {
    this.setData({
      password: e.detail.value
    });
  },

  onLogin() {
    const { username, password } = this.data;
    
    if (!username || !password) {
      wx.showToast({
        title: '用户名和密码不能为空',
        icon: 'none'
      });
      return;
    }

    wx.request({
      url: `${app.globalData.API_BASE_URL}/user/login`,
      method: 'POST',
      data: {
        username: username,
        password: password
      },
      success: (res) => {
        if (res.data.code === 200) {
          const { token, user } = res.data.data;
          wx.setStorageSync('token', token);
          wx.setStorageSync('userInfo', user);
          app.globalData.token = token;
          app.globalData.userInfo = user;
          
          wx.switchTab({
            url: '/pages/chat/chat'
          });
        } else {
          wx.showToast({
            title: res.data.message || '登录失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        console.error('登录失败:', err);
        wx.showToast({
          title: '网络请求失败',
          icon: 'none'
        });
      }
    });
  },

  goToRegister() {
    wx.navigateTo({
      url: '/pages/register/register'
    });
  },

  onWechatLogin() {
    wx.getUserProfile({
      desc: '用于完善会员资料',
      success: (res) => {
        const userInfo = res.userInfo;
        console.log('获取到微信用户信息:', userInfo);
        
        wx.login({
          success: (loginRes) => {
            if (loginRes.code) {
              this.wechatLogin(loginRes.code, userInfo);
            } else {
              wx.showToast({
                title: '微信登录失败',
                icon: 'none'
              });
            }
          },
          fail: (err) => {
            console.error('微信登录失败:', err);
            wx.showToast({
              title: '获取 code 失败',
              icon: 'none'
            });
          }
        });
      },
      fail: (err) => {
        console.error('获取用户信息失败:', err);
        wx.showToast({
          title: '请授权用户信息',
          icon: 'none'
        });
      }
    });
  },

  wechatLogin(code, userInfo) {
    wx.request({
      url: `${app.globalData.API_BASE_URL}/wechat/user/login`,
      method: 'POST',
      data: {
        code: code,
        encryptedData: '',
        iv: ''
      },
      success: (res) => {
        if (res.data.code === 200) {
          const { token, openid, id } = res.data.data;
          wx.setStorageSync('token', token);
          wx.setStorageSync('openid', openid);
          app.globalData.token = token;
          
          wx.switchTab({
            url: '/pages/chat/chat'
          });
        } else {
          wx.showToast({
            title: res.data.message || '微信登录失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        console.error('微信登录失败:', err);
        wx.showToast({
          title: '网络请求失败',
          icon: 'none'
        });
      }
    });
  }
});
