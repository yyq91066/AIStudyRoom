package org.example.aidemo.file.entiy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemFile implements Serializable {

  private long id;
  private String name;
  private String type;
  private String url;
  private LocalDateTime uploadTime;
  private String uploadUser;
  private String description;
  private String remark;
  private String remark1;
  private String remark2;
  private String remark3;
  private String remark4;


}
