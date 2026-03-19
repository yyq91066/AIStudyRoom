const app = getApp();

Page({
  data: {
    messages: [],
    inputMessage: '',
    isSending: false,
    memoryId: null,
    scrollToView: ''
  },

  onLoad() {
    this.initMemoryId();
  },

  initMemoryId() {
    let storedMemoryId = wx.getStorageSync('memoryId');
    if (!storedMemoryId) {
      storedMemoryId = this.generateUUID();
      wx.setStorageSync('memoryId', storedMemoryId);
    }
    this.setData({
      memoryId: storedMemoryId
    });
    
    this.addBotMessage('您好！我是智慧医疗助手，请问有什么可以帮助您的？');
  },

  generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
      const r = Math.random() * 16 | 0;
      const v = c === 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
  },

  onInputMessage(e) {
    this.setData({
      inputMessage: e.detail.value
    });
  },

  sendMessage() {
    const { inputMessage, isSending } = this.data;
    
    if (!inputMessage.trim() || isSending) {
      return;
    }

    this.addUserMessage(inputMessage.trim());
    this.setData({
      inputMessage: ''
    });

    this.sendRequest(inputMessage.trim());
  },

  addUserMessage(content) {
    const { messages } = this.data;
    messages.push({
      isUser: true,
      content: content,
      contentHtml: this.escapeHtml(content),
      isTyping: false,
      isThinking: false
    });
    this.setData({
      messages: messages
    });
    this.scrollToBottom();
  },

  addBotMessage(content) {
    const { messages } = this.data;
    messages.push({
      isUser: false,
      content: content,
      contentHtml: this.renderMarkdown(content),
      isTyping: false,
      isThinking: false
    });
    this.setData({
      messages: messages
    });
    this.scrollToBottom();
  },

  sendRequest(message) {
    const { memoryId } = this.data;
    
    this.setData({
      isSending: true
    });

    const botMsgIndex = this.data.messages.length;
    this.setData({
      messages: [
        ...this.data.messages,
        {
          isUser: false,
          content: '',
          contentHtml: '',
          isTyping: true,
          isThinking: false
        }
      ]
    });

    const requestTask = wx.request({
      url: 'http://localhost:8080/xiaozhi/chat',
      method: 'POST',
      header: {
        'content-type': 'application/json'
      },
      data: {
        memoryId: parseInt(memoryId),
        message: message
      },
      responseType: 'text',
      success: (res) => {
        console.log('聊天响应:', res);
        const messages = this.data.messages;
        if (messages[botMsgIndex]) {
          // 处理后端返回的响应数据
          let responseText = '';
          if (typeof res.data === 'string') {
            responseText = res.data;
          } else if (res.data && typeof res.data === 'object') {
            // 如果是 JSON 对象，检查是否有 data 字段
            if (res.data.data !== undefined) {
              responseText = res.data.data;
            } else if (res.data.message !== undefined) {
              responseText = res.data.message;
            } else {
              responseText = JSON.stringify(res.data);
            }
          } else {
            responseText = '请求失败，请重试';
          }
          
          messages[botMsgIndex].content = responseText || '请求失败，请重试';
          messages[botMsgIndex].contentHtml = this.renderMarkdown(messages[botMsgIndex].content);
          messages[botMsgIndex].isTyping = false;
          this.setData({
            messages: messages
          });
        }
        this.scrollToBottom();
      },
      fail: (err) => {
        console.error('聊天请求失败:', err);
        const messages = this.data.messages;
        if (messages[botMsgIndex]) {
          messages[botMsgIndex].content = '请求失败，请重试';
          messages[botMsgIndex].contentHtml = '请求失败，请重试';
          messages[botMsgIndex].isTyping = false;
          this.setData({
            messages: messages
          });
        }
        this.scrollToBottom();
      },
      complete: () => {
        this.setData({
          isSending: false
        });
      }
    });

    this.requestTask = requestTask;
  },

  scrollToBottom() {
    wx.createSelectorQuery().in(this).select('.message-list').boundingClientRect((rect) => {
      if (rect) {
        const scrollHeight = rect.scrollHeight;
        const lastIndex = this.data.messages.length - 1;
        this.setData({
          scrollToView: `msg-${lastIndex}`
        });
      }
    }).exec();
  },

  escapeHtml(text = "") {
    if (typeof text !== 'string') {
      text = String(text);
    }
    return text
      .replace(/&/g, '&amp;')
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
      .replace(/"/g, '&quot;')
      .replace(/'/g, '&#39;');
  },

  renderMarkdown(text = "") {
    if (typeof text !== 'string') {
      text = String(text);
    }
    let html = this.escapeHtml(text);
    
    html = html.replace(/\n/g, '<br>');
    html = html.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
    html = html.replace(/\*(.*?)\*/g, '<em>$1</em>');
    html = html.replace(/`(.*?)`/g, '<code style="background: rgba(0,0,0,.04); padding: 2rpx 6rpx; border-radius: 6rpx;">$1</code>');
    
    html = html.replace(/^- (.*$)/gm, '<li>$1</li>');
    html = html.replace(/^(\d+)\. (.*$)/gm, '<li>$2</li>');
    
    return html;
  },

  onUnload() {
    if (this.requestTask) {
      this.requestTask.abort();
    }
  }
});
