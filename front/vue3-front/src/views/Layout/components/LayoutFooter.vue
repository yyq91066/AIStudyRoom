<template>
  <footer class="page-shell footer-shell">
    <div class="footer-card">
      <div>
        <strong>智学空间</strong>
        <p>面向自习室场景的预约、空间展示、AI 问答与数据报表平台。</p>
      </div>
      <div class="footer-tags">
        <span>空间导览</span>
        <span>智能问答</span>
        <span>经营报表</span>
      </div>
    </div>
  </footer>
</template>

<style scoped>
.footer-shell {
  padding-top: 8px;
}

.footer-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 18px;
  padding: 18px 22px;
  border-radius: 24px;
  background: rgba(24, 41, 45, 0.9);
  color: rgba(255, 249, 241, 0.9);
  box-shadow: 0 18px 40px rgba(17, 25, 29, 0.14);
}

.footer-card strong {
  display: block;
  margin-bottom: 6px;
  font-size: 16px;
}

.footer-card p {
  margin: 0;
  color: rgba(255, 249, 241, 0.68);
  font-size: 13px;
}

.footer-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.footer-tags span {
  padding: 8px 12px;
  border-radius: 999px;
  background: rgba(255, 255, 255, 0.08);
  font-size: 13px;
}

@media (max-width: 768px) {
  .footer-card {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
