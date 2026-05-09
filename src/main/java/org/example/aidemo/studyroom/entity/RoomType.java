package org.example.aidemo.studyroom.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoomType implements Serializable {

    private Long id;
    private String name;
    private Integer capacity;
    private Double price;
    private String description;
    private String picture;

}