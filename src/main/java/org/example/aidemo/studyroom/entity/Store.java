package org.example.aidemo.studyroom.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 门店实体类
 */
@Data
public class Store implements Serializable {

    private Long id;

    private String name;

    private String city;

    private String address;

    private String openTime;

    private String closeTime;

}