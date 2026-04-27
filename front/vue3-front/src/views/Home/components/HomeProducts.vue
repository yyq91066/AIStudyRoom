<template>
  <section class="section-card capability-section">
    <div class="section-head">
      <div>
        <span class="eyebrow">Core Modules</span>
        <h2>平台能力矩阵</h2>
      </div>
      <p>用统一的能力卡替代原来的占位文本，首页终于像一个完整产品，而不是几个页面入口的合集。</p>
    </div>

    <div class="capability-grid">
      <article>
        <strong>空间分类展示</strong>
        <p>对不同房型做图文展示，适合引导用户快速理解场地差异。</p>
      </article>
      <article>
        <strong>AI 智能服务</strong>
        <p>提供流式对话窗口，可用于咨询、问答、规则说明和个性化推荐。</p>
      </article>
      <article>
        <strong>经营数据报表</strong>
        <p>结合日期筛选、图表和导出功能，为运营分析提供一个可视化入口。</p>
      </article>
      <article>
        <strong>统一登录体系</strong>
        <p>登录和注册页沿用相同视觉骨架，减少界面风格跳变。</p>
      </article>
    </div>
  </section>
</template>

<style scoped>
.capability-section {
  padding: 28px;
}

.section-head {
  display: flex;
  align-items: end;
  justify-content: space-between;
  gap: 20px;
  margin-bottom: 22px;
}

.section-head h2 {
  margin: 14px 0 0;
  font-size: 32px;
}

.section-head p {
  max-width: 430px;
  margin: 0;
  color: var(--text-soft);
  line-height: 1.7;
}

.capability-grid {
  display: grid;
  grid-template-columns: repeat(4, minmax(0, 1fr));
  gap: 16px;
}

.capability-grid article {
  min-height: 220px;
  padding: 22px;
  border-radius: 22px;
  background:
    linear-gradient(180deg, rgba(255, 255, 255, 0.82), rgba(249, 241, 230, 0.88));
  border: 1px solid rgba(143, 77, 22, 0.12);
  box-shadow: var(--shadow-sm);
}

.capability-grid strong {
  display: block;
  margin-bottom: 14px;
  font-size: 20px;
  line-height: 1.25;
}

.capability-grid p {
  margin: 0;
  color: var(--text-soft);
  line-height: 1.8;
}

@media (max-width: 1080px) {
  .capability-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}

@media (max-width: 900px) {
  .section-head {
    display: grid;
  }
}

@media (max-width: 768px) {
  .capability-section {
    padding: 20px;
  }

  .capability-grid {
    grid-template-columns: 1fr;
  }
}
</style>
