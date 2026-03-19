package org.example.aidemo.mail.controller;

import jakarta.mail.MessagingException;
import org.example.aidemo.mail.model.EmailRequest;
import org.example.aidemo.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendSimpleEmail")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        // 发送普通文本邮件
        String to = emailRequest.getTo();
        String subject = emailRequest.getSubject();
        String content = emailRequest.getContent();
        emailService.sendSimpleEmail(to, subject, content);
        // 发送带附件邮件
        // emailService.sendEmailWithAttachment(to, "带附件邮件", "附件测试", "/opt/test.txt");
        // 发送HTML邮件
        // emailService.sendVerifyCode(to, "888888");

        return "邮件发送成功";
    }
    @PostMapping("/sendHtmlEmail")
    public String sendHtmlEmail(@RequestBody EmailRequest emailRequest) {
        String to = emailRequest.getTo();
        String subject = emailRequest.getSubject();
        String html = emailRequest.getHtml();
        try {
            emailService.sendHtmlEmail(to, subject, html);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        // 发送带附件邮件
        // emailService.sendEmailWithAttachment(to, "带附件邮件", "附件测试", "/opt/test.txt");
        // 发送HTML邮件
        // emailService.sendVerifyCode(to, "888888");

        return "邮件发送成功";
    }

    @PostMapping("/sendTemplateEmail")
    public String sendTemplateEmail(@RequestBody EmailRequest emailRequest) {
        String to = emailRequest.getTo();
        String subject = emailRequest.getSubject();
        Map<String, Object> variables = new HashMap<>();
        variables.put("customerName", emailRequest.getCustomerName());
        variables.put("customerPhone", emailRequest.getCustomerPhone());
        try {
            emailService.sendHtmlEmailFromTemplate(to, subject, "jiudian", variables);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return "邮件发送成功";
    }
}
