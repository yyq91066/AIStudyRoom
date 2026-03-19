package org.example.aidemo.mail.model;
import lombok.Data;

import java.io.Serializable;

/**
 * 邮件请求参数实体
 */
@Data // 需引入lombok，或手动加getter/setter
public class EmailRequest implements Serializable {

    // 对应JSON中的to
    private String to;
    // 对应JSON中的subject
    private String subject;
    // 对应JSON中的content
    private String content;
    // 可选：邮件类型（文本/HTML）
    private String type = "text";
    // 可选：重试次数（用于失败重试）
    private int retryCount = 0;
    private String html;
    private String customerName;
    private String customerPhone;
}
