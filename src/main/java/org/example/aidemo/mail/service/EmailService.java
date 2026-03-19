package org.example.aidemo.mail.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private SpringTemplateEngine templateEngine;
    @Value("${spring.mail.username}")
    private String from;

    // 发送普通文本邮件
    public void sendSimpleEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from); // 发件人（需与配置文件的username一致）
        message.setTo(to); // 收件人（可传数组，如 new String[]{"a@qq.com", "b@163.com"}）
        message.setSubject(subject); // 邮件主题
        message.setText(content); // 邮件内容
        javaMailSender.send(message);
    }

    // 发送精美的HTML邮件
    public void sendHtmlEmail(String to, String subject, String html) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html, true); // true = HTML
        javaMailSender.send(mimeMessage);
    }
    public void sendHtmlEmailFromTemplate(String to, String subject, String templateName, Map<String, Object> variables)
            throws MessagingException {
        Context context = new Context();
        if (variables != null && !variables.isEmpty()) {
            context.setVariables(variables);
        }
        String html = templateEngine.process(templateName, context);
        sendHtmlEmail(to, subject, html); // 调用你已有的 HTML 发送方法
    }
}
