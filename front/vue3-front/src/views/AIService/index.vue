<template>
  <div class="app-layout">
    <div class="sidebar">
      <div class="logo-section">
        <img src="@/assets/logo.png" alt="智学空间" width="160" height="160" />
        <span class="logo-text">智学空间</span>
      </div>
      <el-button class="new-chat-button" @click="newChat">
        <i class="fa-solid fa-plus"></i>
        &nbsp;新会话
      </el-button>
    </div>
    <div class="main-content">
      <div class="chat-container">
        <div class="message-list" ref="messaggListRef">
          <div
              v-for="(message, index) in messages"
              :key="index"
              :class="
              message.isUser ? 'message user-message' : 'message bot-message'
            "
          >
            <i
                :class="
                message.isUser
                  ? 'fa-solid fa-user message-icon'
                  : 'fa-solid fa-robot message-icon'
              "
            ></i>
            <span>
<!--              <span v-html="message.content"></span>-->
              <div
                  class="msg-content"
                  :class="{ 'markdown-body': !message.isUser }"
                  v-html="message.isUser ? escapeHtml(message.content) : message.contentHtml"
              ></div>

              <!-- loading -->
              <span
                  class="loading-dots"
                  v-if="message.isThinking || message.isTyping"
              >
                <span class="dot"></span>
                <span class="dot"></span>
              </span>
            </span>
          </div>
        </div>
        <div class="input-container">
          <el-input
              v-model="inputMessage"
              placeholder="请输入消息"
              @keyup.enter="sendMessage"
          ></el-input>
          <el-button @click="sendMessage" :disabled="isSending" type="primary">
            发送
          </el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import axios from 'axios'
import { v4 as uuidv4 } from 'uuid'

const messaggListRef = ref()
const isSending = ref(false)
const uuid = ref()
const inputMessage = ref('')
const messages = ref([])
import "github-markdown-css/github-markdown.css";
import { renderMd } from "@/utils/md";

const escapeHtml = (s = "") =>
    s.replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#39;");

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
  if(messages.value.length > 0){
    messages.value.push(userMsg)
  }

  const botMsg = {
    isUser: false,
    content: "",        // 鍘熷娴佸紡鏂囨湰锛坢arkdown锛?
    contentHtml: "",    // 娓叉煋鍚庣殑HTML
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
              let newText = fullText.substring(lastMsg.content.length)
              lastMsg.content += newText
              lastMsg.contentHtml = renderMd(lastMsg.content)
              console.log(lastMsg)
              scrollToBottom()
            },
          }
      )
      .then(() => {
        messages.value.at(-1).isTyping = false
        isSending.value = false
      })
      .catch((error) => {
        console.error(':', error)
        messages.value.at(-1).content = '请求失败，请重试'
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

const uuidToNumber = (uuid) => {
  let number = 0
  for (let i = 0; i < uuid.length && i < 6; i++) {
    const hexValue = uuid[i]
    number = number * 16 + (parseInt(hexValue, 16) || 0)
  }
  return number % 1000000
}

const convertStreamOutput = (output) => {
  return output
      .replace(/\n/g, '<br>')
      .replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;')
      .replace(/&/g, '&amp;') // 鏂板杞箟锛岄伩鍏?HTML 娉ㄥ叆
      .replace(/</g, '&lt;')
      .replace(/>/g, '&gt;')
}

const newChat = () => {
  console.log('开始新会话')
  localStorage.removeItem('user_uuid')
  window.location.reload()
}

</script>
<style scoped>
.app-layout {
  display: flex;
  gap: 20px;
  height: 100vh;
  padding: 20px;
  background: transparent;
}

.sidebar {
  width: 240px;
  background: rgba(255, 255, 255, 0.75);
  border: 1px solid rgba(148, 169, 207, 0.35);
  box-shadow: 0 20px 45px rgba(30, 45, 90, 0.12);
  border-radius: 22px;
  padding: 24px 18px;
  display: flex;
  flex-direction: column;
  align-items: center;
  backdrop-filter: blur(18px);
}

.logo-section {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.logo-text {
  font-size: 18px;
  font-weight: 700;
  letter-spacing: 0.2px;
  color: #1b1f2b;
  margin-top: 0;
}

.new-chat-button {
  width: 100%;
  margin-top: 24px;
  height: 44px;
  border-radius: 14px;
  border: none;
  background: linear-gradient(135deg, #4b7bff 0%, #3ad0c4 100%);
  color: #fff;
  font-weight: 600;
  box-shadow: 0 12px 25px rgba(58, 144, 255, 0.25);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.new-chat-button:hover {
  transform: translateY(-1px);
  box-shadow: 0 16px 30px rgba(58, 144, 255, 0.3);
}

.main-content {
  flex: 1;
  padding: 0;
  overflow-y: auto;
  background: rgba(255, 255, 255, 0.65);
  border-radius: 24px;
  border: 1px solid rgba(148, 169, 207, 0.28);
  box-shadow: 0 18px 40px rgba(30, 45, 90, 0.1);
}
.chat-container {
  display: flex;
  flex-direction: column;
  height: 100%;
  padding: 20px;
}

.message-list {
  flex: 1;
  overflow-y: auto;
  padding: 16px;
  border: 1px solid rgba(148, 169, 207, 0.25);
  border-radius: 18px;
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.9) 0%, #f5f7fb 100%);
  margin-bottom: 14px;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.message {
  padding: 12px 14px;
  border-radius: 16px;
  display: flex;
  align-items: flex-start;
  gap: 6px;
  animation: message-rise 0.25s ease;
}

.user-message {
  max-width: 70%;
  background: linear-gradient(135deg, #d7e7ff 0%, #bfe4f4 100%);
  align-self: flex-end;
  flex-direction: row-reverse;
  box-shadow: 0 10px 18px rgba(35, 88, 168, 0.12);
}

.bot-message {
  max-width: 90%;
  background: #ffffff;
  align-self: flex-start;
  border: 1px solid rgba(148, 169, 207, 0.25);
  box-shadow: 0 10px 22px rgba(28, 40, 74, 0.08);
}

.message-icon {
  margin: 0 10px;
  font-size: 1.1em;
  color: #4b7bff;
}

.loading-dots {
  padding-left: 5px;
}

.dot {
  display: inline-block;
  margin-left: 5px;
  width: 8px;
  height: 8px;
  background-color: #000000;
  border-radius: 50%;
  animation: pulse 1.2s infinite ease-in-out both;
}

.dot:nth-child(2) {
  animation-delay: -0.6s;
}

@keyframes pulse {
  0%,
  100% {
    transform: scale(0.6);
    opacity: 0.4;
  }

  50% {
    transform: scale(1);
    opacity: 1;
  }
}
.input-container {
  display: flex;
  gap: 12px;
  align-items: center;
  padding: 12px;
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.85);
  border: 1px solid rgba(148, 169, 207, 0.25);
  box-shadow: 0 12px 24px rgba(28, 40, 74, 0.08);
}

.input-container .el-input {
  flex: 1;
  margin-right: 0;
}

.input-container :deep(.el-input__wrapper) {
  background: #f7f9fd;
  border: 1px solid rgba(148, 169, 207, 0.45);
  border-radius: 14px;
  box-shadow: none;
  padding: 6px 12px;
}

.input-container :deep(.el-input__wrapper.is-focus) {
  border-color: rgba(75, 123, 255, 0.8);
  box-shadow: 0 0 0 3px rgba(75, 123, 255, 0.18);
}

.input-container :deep(.el-input__inner) {
  font-size: 15px;
  color: #1a1d26;
}

.input-container :deep(.el-button) {
  height: 44px;
  border-radius: 12px;
  font-weight: 600;
}

.input-container :deep(.el-button--primary) {
  background: linear-gradient(135deg, #4b7bff 0%, #6a5dff 100%);
  border: none;
  box-shadow: 0 10px 18px rgba(75, 123, 255, 0.28);
}

.input-container :deep(.el-button--primary:hover) {
  filter: brightness(1.02);
}

/* 濯掍綋鏌ヨ锛屽綋璁惧瀹藉害灏忎簬绛変簬 768px 鏃跺簲鐢ㄤ互涓嬫牱寮?*/
@media (max-width: 768px) {
  .main-content {
    padding: 0;
  }
  .app-layout {
    flex-direction: column;
    padding: 12px;
  }

  .sidebar {
    /* display: none; */
    width: 100%;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: 12px 16px;
  }

  .logo-section {
    flex-direction: row;
    align-items: center;
  }

  .logo-text {
    font-size: 18px;
  }

  .logo-section img {
    width: 40px;
    height: 40px;
  }

  .new-chat-button {
    margin-right: 30px;
    width: auto;
    margin-top: 5px;
    height: 40px;
  }
}

/* 濯掍綋鏌ヨ锛屽綋璁惧瀹藉害澶т簬 768px 鏃跺簲鐢ㄥ師鏉ョ殑鏍峰紡 */
@media (min-width: 769px) {
  .main-content {
    padding: 0;
  }

  .app-layout {
    display: flex;
    height: 100vh;
  }

  .sidebar {
    width: 240px;
    background: rgba(255, 255, 255, 0.75);
    padding: 24px 18px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .logo-section {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .logo-text {
    font-size: 18px;
    font-weight: bold;
    margin-top: 0;
  }

  .new-chat-button {
    width: 100%;
    margin-top: 20px;
  }
}
.msg-content {
  width: 100%;
  white-space: pre-wrap;
  word-break: break-word;
  font-size: 14px;
  color: #1b1f2b;
}

/* 鏈?markdown 鏇村儚 ChatGPT */
.bot-message .markdown-body {
  background: transparent; /* 璁╂皵娉″簳鑹茬敱 .bot-message 鎺у埗 */
  font-size: 14px;
  line-height: 1.05;
}

/* 浠ｇ爜鍧楀ソ鐪?鍙í鍚戞粴鍔?*/
.bot-message .markdown-body pre {
  border-radius: 12px;
  padding: 12px;
  overflow: auto;
  margin: 10px 0;
}
.bot-message .markdown-body code {
  border-radius: 6px;
  padding: 2px 6px;
  background: rgba(0,0,0,.04);
}


/* 鉁?scoped 涓嬪繀椤?deep 鎵嶈兘绋冲畾瑕嗗埗 github-markdown-css */
.bot-message :deep(.markdown-body) {
  line-height: 1.25;   /* 琛岄棿璺濆埆澶皬锛?.05浼氄寻寰楅毦鐪?*/
}

.bot-message :deep(.markdown-body p) {
  margin: 0.1em 0 !important;   /* 娈佃惤闂磋窛锛氬叧閿?*/
}

.bot-message :deep(.markdown-body ul),
.bot-message :deep(.markdown-body ol) {
  margin: 0.25em 0 !important;  /* 鍒楄〃鏁翠綋涓ຼ下闂磋窛 */
  padding-left: 1.2em;          /* 鍒楄〃缂╄繘绋嶅井绱у噾 */
}

.bot-message :deep(.markdown-body li) {
  margin: 0.15em 0 !important;  /* 鍒楄〃椤归棿璺?*/
}

.bot-message :deep(.markdown-body pre) {
  margin: 0.35em 0 !important;  /* 浠ｇ爜鍧椾笂涓嬮棿璺?*/
}

@keyframes message-rise {
  from {
    transform: translateY(6px);
    opacity: 0.6;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

</style>


