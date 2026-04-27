<template>
  <div class="page-shell category-page">
    <section class="page-hero">
      <span class="eyebrow">Category Page</span>
      <h1 class="section-title">空间分类页现在有了明确的内容结构。</h1>
      <p class="section-desc">
        这里不再只是一个空白占位，而是作为房型介绍页承接首页入口，后续可以继续接入更多筛选和详情数据。
      </p>
    </section>

    <section class="category-grid">
      <article class="section-card card">
        <strong>安静自习区</strong>
        <p>适合长时间学习，强调安静、照明和桌面尺度。</p>
      </article>
      <article class="section-card card">
        <strong>双人协作区</strong>
        <p>适合讨论、组队刷题和短时协作，强调灵活组合与沟通效率。</p>
      </article>
      <article class="section-card card">
        <strong>沉浸包间</strong>
        <p>适合高专注时段或预约型用户，强调私密性和独立性。</p>
      </article>
    </section>
  </div>
</template>

<style scoped>
.category-page {
  display: grid;
  gap: 24px;
}

.category-grid {
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: 18px;
}

.card {
  padding: 26px;
  min-height: 220px;
}

.card strong {
  display: block;
  margin-bottom: 14px;
  font-size: 24px;
}

.card p {
  margin: 0;
  color: var(--text-soft);
  line-height: 1.8;
}

@media (max-width: 900px) {
  .category-grid {
    grid-template-columns: 1fr;
  }
}
</style>
