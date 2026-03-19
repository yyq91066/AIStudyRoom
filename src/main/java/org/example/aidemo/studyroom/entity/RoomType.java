package org.example.aidemo.studyroom.entity;

import lombok.Data;

@Data
public class RoomType {

    private Long id;
    private String name;
    private Integer capacity;
    private Double price;
    private String description;

}