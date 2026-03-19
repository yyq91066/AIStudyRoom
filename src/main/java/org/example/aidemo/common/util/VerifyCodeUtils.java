package org.example.aidemo.common.util;

import java.util.Random;

/**
 * 验证码工具类
 */
public class VerifyCodeUtils {
    // 生成6位数字验证码
    public static String generateCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
}