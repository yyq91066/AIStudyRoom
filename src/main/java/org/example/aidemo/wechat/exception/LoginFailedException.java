package org.example.aidemo.wechat.exception;

/**
 * 登录失败异常
 */
public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        super("登录失败");
    }

    public LoginFailedException(String message) {
        super(message);
    }

    public LoginFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
