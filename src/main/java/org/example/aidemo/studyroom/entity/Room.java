package org.example.aidemo.studyroom.entity;

import lombok.Data;

@Data
public class Room {

    private Long id;

    private Long storeId;

    private Long roomTypeId;

    private Integer capacity;

    private String roomName;

    private Integer status;

    private String description;

    private String remark;

    private String pic;

}