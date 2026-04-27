<template>
  <div class="page-shell ai-page">
    <section class="page-hero ai-hero">
      <div>
        <span class="eyebrow">AI Service</span>
        <h1 class="section-title">让智能问答页更像产品工作台，而不是一块裸聊天框。</h1>
      </div>
      <p class="section-desc">
        保留现有流式对话能力，同时统一侧栏、消息区和输入区的视觉语言，提升阅读效率与页面完成度。
      </p>
    </section>

    <div class="app-layout">
      <aside class="sidebar">
        <div class="logo-section">
          <img src="@/assets/logo.png" alt="智学空间" width="96" height="96" />
          <div>
            <span class="logo-text">智学空间 AI 助手</span>
            <p>用于规则咨询、学习建议与平台说明。</p>
          </div>
        </div>

        <div class="sidebar-tips">
          <div>
            <strong>推荐提问</strong>
            <span>自习室规则、预约说明、空间推荐</span>
          </div>
          <div>
            <strong>输出形式</strong>
            <span>支持 Markdown 渲染与流式回复展示</span>
          </div>
        </div>

        <el-button class="new-chat-button" @click="newChat">开启新会话</el-button>
      </aside>

      <section class="main-content">
        <div class="chat-container">
          <div class="message-list" ref="messaggListRef">
            <div
              v-for="(message, index) in messages"
              :key="index"
              :class="message.isUser ? 'message user-message' : 'message bot-message'"
            >
              <div class="message-badge">{{ message.isUser ? '我' : 'AI' }}</div>
              <div class="message-body">
                <div
                  class="msg-content"
                  :class="{ 'markdown-body': !message.isUser }"
                  v-html="message.isUser ? escapeHtml(message.content) : message.contentHtml"
                ></div>

                <span v-if="message.isThinking || message.isTyping" class="loading-dots">
                  <span class="dot"></span>
                  <span class="dot"></span>
                </span>
              </div>
            </div>
          </div>

          <div class="input-container">
            <el-input
              v-model="inputMessage"
              placeholder="请输入你的问题，例如：推荐一种适合长时间学习的房型"
              @keyup.enter="sendMessage"
            />
            <el-button @click="sendMessage" :disabled="isSending" type="primary">发送</el-button>
          </div>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import axios from 'axios'
import { v4 as uuidv4 } from 'uuid'
import 'github-markdown-css/github-markdown.css'
import { renderMd } from '@/utils/md'

const messaggListRef = ref()
const isSending = ref(false)
const uuid = ref()
const inputMessage = ref('')
const messages = ref([])

const escapeHtml = (s = '') =>
  s
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;')
    .replace(/"/g, '&quot;')
    .replace(/'/g, '&#39;')

onMounted(() => {
  initUUID()
  watch(messages, () => scrollToBottom(), { deep: true })
  hello()
})

const scrollToBottom = () => {
  if (messaggListRef.value) {
    messaggListRef.value.scrollTop = messaggListRef.value.scrollHeight
  }
}

const hello = () => {
  sendRequest('你好')
}

const sendMessage = () => {
  if (inputMessage.value.trim()) {
    sendRequest(inputMessage.value.trim())
    inputMessage.value = ''
  }
}

const sendRequest = (message) => {
  isSending.value = true
  const userMsg = {
    isUser: true,
    content: message,
    isTyping: false,
    isThinking: false,
  }

  if (messages.value.length > 0) {
    messages.value.push(userMsg)
  }

  const botMsg = {
    isUser: false,
    content: '',
    contentHtml: '',
    isTyping: true,
    isThinking: false,
  }

  messages.value.push(botMsg)
  const lastMsg = messages.value[messages.value.length - 1]
  scrollToBottom()

  axios
    .post(
      'http://localhost:8080/aiservice/chat',
      { memoryId: uuid.value, message },
      {
        responseType: 'stream',
        onDownloadProgress: (e) => {
          const fullText = e.event.target.responseText
          const newText = fullText.substring(lastMsg.content.length)
          lastMsg.content += newText
          lastMsg.contentHtml = renderMd(lastMsg.content)
          scrollToBottom()
        },
      },
    )
    .then(() => {
      messages.value.at(-1).isTyping = false
      isSending.value = false
    })
    .catch((error) => {
      console.error('AI 请求失败:', error)
      messages.value.at(-1).content = '请求失败，请稍后重试'
      messages.value.at(-1).contentHtml = '请求失败，请稍后重试'
      messages.value.at(-1).isTyping = false
      isSending.value = false
    })
}

const initUUID = () => {
  let storedUUID = localStorage.getItem('user_uuid')
  if (!storedUUID) {
    storedUUID = uuidToNumber(uuidv4())
    localStorage.setItem('user_uuid', storedUUID)
  }
  uuid.value = storedUUID
}

const uuidToNumber = (rawUuid) => {
  let number = 0
  for (let i = 0; i < rawUuid.length && i < 6; i++) {
    const hexValue = rawUuid[i]
    number = number * 16 + (parseInt(hexValue, 16) || 0)
  }
  return number % 1000000
}

const newChat = () => {
  localStorage.removeItem('user_uuid')
  window.location.reload()
}
</script>

<style scoped>
.ai-page {
  display: grid;
  gap: 20px;
}

.ai-hero {
  display: grid;
  grid-template-columns: minmax(0, 1.2fr) minmax(280px, 0.8fr);
  gap: 24px;
  align-items: end;
}

.app-layout {
  display: grid;
  grid-template-columns: 280px minmax(0, 1fr);
  gap: 20px;
  min-height: 72vh;
}

.sidebar,
.main-content {
  border-radius: 28px;
  border: 1px solid rgba(143, 77, 22, 0.12);
  background: rgba(255, 252, 247, 0.8);
  box-shadow: var(--shadow-lg);
  backdrop-filter: blur(18px);
}

.sidebar {
  padding: 24px;
  display: grid;
  align-content: start;
  gap: 24px;
}

.logo-section {
  display: grid;
  gap: 14px;
}

.logo-section img {
  border-radius: 24px;
  box-shadow: var(--shadow-sm);
}

.logo-text {
  display: block;
  font-size: 20px;
  font-weight: 800;
}

.logo-section p,
.sidebar-tips span {
  margin: 6px 0 0;
  color: var(--text-soft);
  line-height: 1.7;
}

.sidebar-tips {
  display: grid;
  gap: 14px;
}

.sidebar-tips div {
  padding: 16px;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.72);
  border: 1px solid rgba(143, 77, 22, 0.12);
}

.sidebar-tips strong {
  display: block;
  margin-bottom: 4px;
}

.new-chat-button {
  height: 48px;
  border: none;
  border-radius: 16px;
  background: linear-gradient(135deg, #bc6c25, #f6bd60);
  color: #1f2529;
  font-weight: 800;
}

.main-content {
  padding: 18px;
}

.chat-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  gap: 16px;
}

.message-list {
  flex: 1;
  min-height: 460px;
  overflow-y: auto;
  padding: 14px;
  border-radius: 22px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.9), rgba(247, 240, 230, 0.88));
  border: 1px solid rgba(143, 77, 22, 0.12);
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.message {
  display: flex;
  gap: 12px;
  align-items: flex-start;
}

.user-message {
  align-self: flex-end;
  flex-direction: row-reverse;
}

.message-badge {
  width: 38px;
  height: 38px;
  border-radius: 14px;
  display: grid;
  place-items: center;
  background: linear-gradient(135deg, #17313a, #2f7f73);
  color: #fff;
  font-size: 13px;
  font-weight: 800;
  flex-shrink: 0;
}

.user-message .message-badge {
  background: linear-gradient(135deg, #bc6c25, #f6bd60);
  color: #1f2529;
}

.message-body {
  max-width: min(82%, 760px);
  padding: 14px 16px;
  border-radius: 20px;
  background: #fffdf9;
  border: 1px solid rgba(143, 77, 22, 0.12);
  box-shadow: var(--shadow-sm);
}

.user-message .message-body {
  background: linear-gradient(135deg, rgba(246, 189, 96, 0.24), rgba(188, 108, 37, 0.14));
}

.msg-content {
  white-space: pre-wrap;
  word-break: break-word;
  font-size: 14px;
  color: var(--text-main);
}

.loading-dots {
  display: inline-flex;
  gap: 6px;
  margin-top: 8px;
}

.dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background: var(--brand);
  animation: pulse 1.2s infinite ease-in-out both;
}

.dot:nth-child(2) {
  animation-delay: -0.6s;
}

.input-container {
  display: grid;
  grid-template-columns: minmax(0, 1fr) auto;
  gap: 12px;
  align-items: center;
  padding: 12px;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.86);
  border: 1px solid rgba(143, 77, 22, 0.12);
}

.input-container :deep(.el-input__wrapper) {
  min-height: 48px;
  border-radius: 14px;
  box-shadow: none;
  background: rgba(250, 247, 243, 0.96);
}

.input-container :deep(.el-button) {
  height: 48px;
  border-radius: 14px;
  border: none;
  background: linear-gradient(135deg, #2f7f73, #6fb8ac);
  font-weight: 700;
}

.bot-message :deep(.markdown-body) {
  background: transparent;
  color: inherit;
  line-height: 1.45;
}

.bot-message :deep(.markdown-body p),
.bot-message :deep(.markdown-body ul),
.bot-message :deep(.markdown-body ol),
.bot-message :deep(.markdown-body pre) {
  margin: 0.4em 0;
}

.bot-message :deep(.markdown-body pre) {
  padding: 12px;
  border-radius: 12px;
}

@keyframes pulse {
  0%,
  100% {
    transform: scale(0.7);
    opacity: 0.4;
  }

  50% {
    transform: scale(1);
    opacity: 1;
  }
}

@media (max-width: 960px) {
  .ai-hero,
  .app-layout {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .sidebar,
  .main-content {
    border-radius: 22px;
  }

  .sidebar,
  .main-content,
  .message-list {
    padding: 16px;
  }

  .message-body {
    max-width: 100%;
  }

  .input-container {
    grid-template-columns: 1fr;
  }
}
</style>
