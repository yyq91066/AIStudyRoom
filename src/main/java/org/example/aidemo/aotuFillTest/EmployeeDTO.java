package org.example.aidemo.aotuFillTest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
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
