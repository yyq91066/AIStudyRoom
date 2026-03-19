package org.example.aidemo.User.vo;


import lombok.Data;
import org.example.aidemo.User.model.User;

@Data
public class RegisterVO {

    private String token;
    private User user;
}