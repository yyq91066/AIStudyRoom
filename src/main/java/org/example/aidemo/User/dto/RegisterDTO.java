package org.example.aidemo.User.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RegisterDTO implements Serializable {

//    @NotBlank(message = "用户名不能为空")
    private String username;

//    @NotBlank(message = "邮箱不能为空")
    private String email;

//    @NotBlank(message = "验证码不能为空")
    private String code;

//    @NotBlank(message = "密码不能为空")
    private String password;
}