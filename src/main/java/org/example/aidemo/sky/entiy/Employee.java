package org.example.aidemo.sky.entiy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    private Long id;
    private String userName;
    private String name;
    private String phoneNumber;
    private String sex;
    private Long idNumber;
    private String password;
    private LocalDateTime updateTime;
    private LocalDateTime createTime;
    private String createUser;
    private String updateUser;
}
