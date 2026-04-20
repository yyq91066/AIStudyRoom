package org.example.aidemo.studyroom.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportCountVO implements Serializable {
    private LocalDate orderDate;
    private Integer orderCount;
}
