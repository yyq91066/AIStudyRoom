<template>
  <section class="insight-grid">
    <article class="section-card insight-panel feature-panel">
      <span class="eyebrow">Service Flow</span>
      <h3>把用户旅程拆成三段，首页信息密度会更合理。</h3>
      <div class="timeline">
        <div>
          <strong>01 进入平台</strong>
          <p>先看到品牌定位、主功能入口和核心价值，而不是零散组件堆叠。</p>
        </div>
        <div>
          <strong>02 选择空间</strong>
          <p>通过房型卡片和实景图快速建立认知，再决定是否进一步预约。</p>
        </div>
        <div>
          <strong>03 获得服务</strong>
          <p>继续进入 AI 问答或经营报表，形成从前台到管理端的一致体验。</p>
        </div>
      </div>
    </article>

    <article class="section-card insight-panel stats-panel">
      <span class="eyebrow">Visual Goal</span>
      <h3>这次优化重点不只是“更花”，而是更有层级。</h3>
      <div class="metrics">
        <div>
          <strong>统一卡片体系</strong>
          <span>所有页面共用圆角、边框和留白节奏</span>
        </div>
        <div>
          <strong>暖色科技感</strong>
          <span>避免常见蓝紫模板，改为米色 + 铜棕 + 青绿</span>
        </div>
        <div>
          <strong>移动端可用</strong>
          <span>保证窄屏下布局不会塌陷成难看的表单堆叠</span>
        </div>
      </div>
    </article>
  </section>
</template>

<style scoped>
.insight-grid {
  display: grid;
  grid-template-columns: 1.15fr 0.85fr;
  gap: 24px;
}

.insight-panel {
  padding: 28px;
}

.insight-panel h3 {
  margin: 18px 0 20px;
  font-size: 28px;
  line-height: 1.2;
}

.timeline,
.metrics {
  display: grid;
  gap: 16px;
}

.timeline div,
.metrics div {
  padding: 18px;
  border-radius: 18px;
  background: rgba(255, 255, 255, 0.68);
  border: 1px solid rgba(143, 77, 22, 0.1);
}

.timeline strong,
.metrics strong {
  display: block;
  margin-bottom: 8px;
  font-size: 17px;
}

.timeline p,
.metrics span {
  margin: 0;
  color: var(--text-soft);
  line-height: 1.7;
}

@media (max-width: 900px) {
  .insight-grid {
    grid-template-columns: 1fr;
  }
}

@media (max-width: 768px) {
  .insight-panel {
    padding: 20px;
  }
}
</style>
