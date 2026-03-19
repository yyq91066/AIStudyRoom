package org.example.aidemo.studyroom.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.example.aidemo.studyroom.entity.Reservation;

import java.util.List;

@Mapper
public interface ReservationMapper {

    @Insert(
        "insert into reservation(user_id,room_id,store_id,start_time,end_time,status,create_time,remark1,remark2)values(#{userId},#{roomId},#{storeId},#{startTime},#{endTime},#{status},now(),#{remark1},#{remark2})"
    )
    void insertReservation(Reservation reservation);

//    @Select("select * from reservation where user_id = #{userId}")
    List<Reservation> findByUserId(Long userId);

}