package org.example.aidemo.frontapi;

import lombok.Data;

import java.io.Serializable;

@Data
public class FrontCategory implements Serializable {
    public Integer id;
    public String name;
    public String picture;
    public String describe;
    public String path;
    public Integer enable;
}
