package org.example.aidemo.studyroom.service.impl;

import org.example.aidemo.studyroom.entity.Reservation;
import org.example.aidemo.studyroom.entity.Room;
import org.example.aidemo.studyroom.mapper.ReservationMapper;
import org.example.aidemo.studyroom.service.ReservationService;
import org.example.aidemo.studyroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationMapper reservationMapper;
    @Autowired
    private RoomService roomServiceImpl;

    //预约
    public String reserveRoom(Reservation reservation){

//        Map<String,Object > claims= ThreadLocalUtil.get();
//
//        Integer userId = (Integer) claims.get("id");
//        reservation.setUserId(Long.valueOf(userId));


        Room room=roomServiceImpl.findRoomById(reservation.getRoomId());

        if (room.getStatus()==0){
            room.setStatus(1);
            roomServiceImpl.updateRoom(room);
            reservation.setStatus(0);
            reservationMapper.insertReservation(reservation);

            return "预约成功";
        }else if (room.getStatus()==1){
            return "该自习室已被预约";
        }
        else return "自习室状态异常";
    }

    //查询用户预约
    public List<Reservation> getUserReservations(Long userId){

        return reservationMapper.findByUserId(userId);

    }

}