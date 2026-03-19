package org.example.aidemo.studyroom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.aidemo.studyroom.entity.Store;

import java.util.List;

@Mapper
public interface StoreMapper {

    @Select("select * from store")
    List<Store> getAllStores();
}
