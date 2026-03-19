package org.example.aidemo.studyroom.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Reservation {

    private Long id;

    private Long userId;

    private Long storeId;

    private Long roomId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    private LocalDateTime createTime;

    private String remark1;//左连接查询 存自习室名称

    private String remark2;

}