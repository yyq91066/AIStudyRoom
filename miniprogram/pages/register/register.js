const app = getApp();

Page({
  data: {
    username: '',
    email: '',
    password: '',
    code: '',
    codeDisabled: false,
    codeButtonText: '获取验证码',
    countdown: 60
  },

  onUsernameInput(e) {
    this.setData({
      username: e.detail.value
    });
  },

  onEmailInput(e) {
    this.setData({
      email: e.detail.value
    });
  },

  onPasswordInput(e) {
    this.setData({
      password: e.detail.value
    });
  },

  onCodeInput(e) {
    this.setData({
      code: e.detail.value
    });
  },

  sendCode() {
    const { email } = this.data;
    
    if (!email || !this.isValidEmail(email)) {
      wx.showToast({
        title: '请输入正确的邮箱',
        icon: 'none'
      });
      return;
    }

    wx.request({
      url: `${app.globalData.API_BASE_URL}/user/send-register-code`,
      method: 'GET',
      data: {
        email: email
      },
      success: (res) => {
        if (res.data.code === 200) {
          wx.showToast({
            title: '验证码已发送',
            icon: 'success'
          });
          this.startCountdown();
        } else {
          wx.showToast({
            title: res.data.message || '发送失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        console.error('发送验证码失败:', err);
        wx.showToast({
          title: '网络请求失败',
          icon: 'none'
        });
      }
    });
  },

  startCountdown() {
    this.setData({
      codeDisabled: true,
      codeButtonText: '60s 后重获'
    });

    const timer = setInterval(() => {
      let { countdown } = this.data;
      countdown--;
      
      if (countdown <= 0) {
        clearInterval(timer);
        this.setData({
          codeDisabled: false,
          codeButtonText: '获取验证码',
          countdown: 60
        });
      } else {
        this.setData({
          codeButtonText: `${countdown}s 后重获`,
          countdown: countdown
        });
      }
    }, 1000);
  },

  isValidEmail(email) {
    const regex = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    return regex.test(email);
  },

  onRegister() {
    const { username, email, password, code } = this.data;
    
    if (!username || !email || !password || !code) {
      wx.showToast({
        title: '请填写所有字段',
        icon: 'none'
      });
      return;
    }

    if (!this.isValidEmail(email)) {
      wx.showToast({
        title: '邮箱格式不正确',
        icon: 'none'
      });
      return;
    }

    wx.request({
      url: `${app.globalData.API_BASE_URL}/user/register`,
      method: 'POST',
      data: {
        username: username,
        email: email,
        password: password,
        code: code
      },
      success: (res) => {
        if (res.data.code === 200) {
          wx.showToast({
            title: '注册成功',
            icon: 'success'
          });
          setTimeout(() => {
            wx.navigateBack();
          }, 1500);
        } else {
          wx.showToast({
            title: res.data.message || '注册失败',
            icon: 'none'
          });
        }
      },
      fail: (err) => {
        console.error('注册失败:', err);
        wx.showToast({
          title: '网络请求失败',
          icon: 'none'
        });
      }
    });
  }
});
