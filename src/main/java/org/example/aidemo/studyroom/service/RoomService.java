package org.example.aidemo.studyroom.service;

import org.example.aidemo.studyroom.entity.Room;

import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();
    List<Room> getStoreById(Long id);

    List<Room> findByStoreOrType(Long storeId,Long roomTypeId);

    List<Room> findByStoreOrTypeAvailable(Long storeId,Long roomTypeId);

    void updateRoom(Room room);
    Room findRoomById(Long id);
    void deleteRoomById(Long id);
}
