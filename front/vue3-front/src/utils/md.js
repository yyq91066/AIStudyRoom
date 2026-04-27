import MarkdownIt from "markdown-it";
import hljs from "highlight.js";
import "highlight.js/styles/github.css";

const md = new MarkdownIt({
    html: false,     // 最简单且安全：不允许原始HTML
    linkify: true,
    breaks: true,
    highlight(code, lang) {
        if (lang && hljs.getLanguage(lang)) {
            const v = hljs.highlight(code, { language: lang }).value;
            return `<pre class="hljs"><code>${v}</code></pre>`;
        }
        const v = hljs.highlightAuto(code).value;
        return `<pre class="hljs"><code>${v}</code></pre>`;
    },
});

export function renderMd(text = "") {
    return md.render(text);
}
