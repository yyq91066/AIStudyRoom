package org.example.aidemo.studyroom.service.impl;

import org.example.aidemo.studyroom.entity.Room;
import org.example.aidemo.studyroom.entity.RoomType;
import org.example.aidemo.studyroom.mapper.RoomMapper;
import org.example.aidemo.studyroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomMapper roomMapper;
    @Override
    public List<Room> getAllRooms() {
        return roomMapper.getAllRooms();
    }

    @Override
    public List<Room> getStoreById(Long id) {
        return roomMapper.findByStoreId(id);
    }

    @Override
    public List<Room> findByStoreOrType(Long storeId, Long roomTypeId) {
        return roomMapper.findByStoreOrType(storeId,roomTypeId);
    }

    @Override
    public List<Room> findByStoreOrTypeAvailable(Long storeId, Long roomTypeId) {
         ;

        return roomMapper.findByStoreOrTypeAvailable(storeId,roomTypeId);
    }

    @Override
    public void updateRoom(Room room) {

        roomMapper.updateRoom(room);

    }

    @Override
    public Room findRoomById(Long id) {

        return roomMapper.findRoomById(id);
    }

    @Override
    public void deleteRoomById(Long id) {
         roomMapper.deleteRoom(id);
    }

    @Override
    public List<RoomType> getRoomTypes() {
        return roomMapper.getRoomTypes();
    }


}
