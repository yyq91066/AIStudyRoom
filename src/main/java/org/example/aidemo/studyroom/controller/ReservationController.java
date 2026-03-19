package org.example.aidemo.studyroom.controller;

import org.example.aidemo.studyroom.entity.Reservation;
import org.example.aidemo.studyroom.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation")
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    //预约
    @PostMapping("/create")
    public String create(@RequestBody Reservation reservation){

        return reservationService.reserveRoom(reservation);

    }

    //查询预约
    @GetMapping("/user/{userId}")
    public List<Reservation> list(@PathVariable Long userId){

        return reservationService.getUserReservations(userId);

    }

    //todo 查询空位

    //todo 取消预约 修改预约

}