package org.example.aidemo.studyroom.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.aidemo.studyroom.entity.Room;

import java.util.List;

@Mapper
public interface RoomMapper {
    @Select("select * from room")
    List<Room> getAllRooms();

    @Select("select * from room where store_id = #{storeId}")
    List<Room> findByStoreId(Long storeId);

    List<Room> findByStoreOrType(Long storeId,Long roomTypeId);

    List<Room> findByStoreOrTypeAvailable(Long storeId,Long roomTypeId);

    void updateRoom(Room room);

    Room findRoomById(Long id);
    @Select("delete from room where id = #{id}")
    void deleteRoom (Long id);
}
