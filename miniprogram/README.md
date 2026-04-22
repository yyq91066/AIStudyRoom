# 微信小程序项目说明

## 项目结构
```
miniprogram/
├── http.js                    # 小程序入口文件
├── app.json                  # 全局配置文件
├── app.wxss                  # 全局样式文件
├── project.config.json       # 项目配置文件
├── sitemap.json              # 搜索索引配置
├── images/                   # 图标资源文件夹 (需自行准备)
│   ├── logo.png             # Logo 图标 (160x160px)
│   ├── wechat.png           # 微信图标 (48x48px)
│   ├── chat.png             # 聊天图标 - 未选中 (48x48px)
│   ├── chat-active.png      # 聊天图标 - 选中 (48x48px)
│   ├── profile.png          # 个人中心图标 - 未选中 (48x48px)
│   └── profile-active.png   # 个人中心图标 - 选中 (48x48px)
└── pages/                    # 页面文件夹
    ├── login/               # 登录页
    │   ├── login.js
    │   ├── login.json
    │   ├── login.wxml
    │   └── login.wxss
    ├── register/            # 注册页
    │   ├── register.js
    │   ├── register.json
    │   ├── register.wxml
    │   └── register.wxss
    ├── chat/                # 聊天页
    │   ├── chat.js
    │   ├── chat.json
    │   ├── chat.wxml
    │   └── chat.wxss
    └── profile/             # 个人中心页
        ├── profile.js
        ├── profile.json
        ├── profile.wxml
        └── profile.wxss
```

## 快速开始

### 1. 准备工作
1. 在微信公众平台注册小程序账号，获取 AppID
2. 下载并安装微信开发者工具
3. 准备图标资源 (放在 `images/` 目录下)

### 2. 导入项目
1. 打开微信开发者工具
2. 选择"导入项目"
3. 项目目录选择：`aidemo/miniprogram`
4. 填写你的 AppID
5. 点击"确定"

### 3. 配置开发设置
1. 在 `project.config.json` 中修改 `appid` 为你的小程序 AppID
2. 在微信开发者工具中点击"详情"
3. 勾选"不校验合法域名、web-view(业务域名)、TLS 版本以及 HTTPS 证书"(仅开发阶段)

### 4. 运行项目
1. 确保后端服务运行在 http://localhost:8080
2. 在微信开发者工具中点击"编译"即可预览

## 功能模块

### 1. 登录/注册模块 (`pages/login`, `pages/register`)
- 用户名密码登录
- 微信授权登录
- 邮箱注册 (带验证码)

### 2. AI 聊天模块 (`pages/chat`)
- 智能医疗问答
- 流式对话 (受限于小程序能力，使用普通请求方式)
- Markdown 渲染支持

### 3. 个人中心模块 (`pages/profile`)
- 用户信息展示
- 清除缓存
- 退出登录

## API 接口对接

后端接口地址配置在 `http.js` 中:
```javascript
this.globalData.API_BASE_URL = 'http://localhost:8080/api';
```

生产环境请修改为正式服务器地址。

## 注意事项

1. **图标资源**: 需要自行准备 6 个 PNG 图标文件
2. **域名配置**: 上线前需在微信公众平台配置合法的服务器域名
3. **HTTPS**: 生产环境必须使用 HTTPS 协议
4. **跨域**: 开发阶段可在开发者工具中关闭域名校验

## 技术栈
- WXML (类 HTML 标记语言)
- WXSS (类 CSS 样式语言)
- JavaScript (ES6+)
- 微信小程序原生 API

## 常见问题

### Q: 如何替换成我的 AppID?
A: 打开 `project.config.json`，修改 `"appid": "wxXXXXXXXXXXXXXXX"` 为你的小程序 AppID

### Q: 图标在哪里下载？
A: 可以在 iconfont、flaticon 等网站下载免费图标，或使用设计工具自行制作

### Q: 后端接口不通怎么办？
A: 
1. 检查后端服务是否启动
2. 确认端口号是否为 8080
3. 在微信开发者工具中开启"不校验合法域名"

### Q: 如何调试代码？
A: 使用微信开发者工具的调试器，可以查看 Console、Network、Storage 等信息
