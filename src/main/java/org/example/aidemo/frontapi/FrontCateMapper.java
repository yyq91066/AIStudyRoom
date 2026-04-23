package org.example.aidemo.frontapi;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FrontCateMapper {
    @Select("select * from front_category")
    List<FrontCategory> getCategory ();
}
