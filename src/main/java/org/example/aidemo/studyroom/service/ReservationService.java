package org.example.aidemo.studyroom.service;

import org.example.aidemo.studyroom.entity.Reservation;

import java.util.List;

public interface ReservationService {
    public String reserveRoom(Reservation reservation);
    public List<Reservation> getUserReservations(Long userId);

}
