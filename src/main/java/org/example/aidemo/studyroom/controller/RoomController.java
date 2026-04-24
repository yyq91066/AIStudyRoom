package org.example.aidemo.studyroom.controller;

import org.example.aidemo.common.model.Result;
import org.example.aidemo.studyroom.entity.Room;
import org.example.aidemo.studyroom.entity.RoomType;
import org.example.aidemo.studyroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/room")
@CrossOrigin
public class RoomController {
    @Autowired
    private RoomService roomServiceImpl;
    @GetMapping("/getAllRooms")
    public Result<List<Room>> getAllRooms() {

        List<Room> allRooms = roomServiceImpl.getAllRooms();
        return Result.success(allRooms);
    }
    @GetMapping("/getRoomById")
    @Cacheable(cacheNames = "roomCache", key = "#id")
    public Result<Room> getRoomById(Long id) {
        Room roomById = roomServiceImpl.findRoomById(id);
        return Result.success(roomById);
    }
    @GetMapping("/DeleteRoomById")
    public Result deleteRoomById(Long id) {
        roomServiceImpl.deleteRoomById(id);
        return Result.success("success");
    }

    @GetMapping("/getByStoreId")
//    @Cacheable(cacheNames = "roomCache", key = "#id")//roomCache::id
    public Result<List<Room>> getByStoreId(Long id) {
        List<Room> storeById = roomServiceImpl.getStoreById(id);
        return Result.success(storeById);
    }

    /**
     * 根据店铺id或者房间类型查询出房间详情
     * @param storeId
     * @param roomType
     * @return
     */
    @GetMapping("/findByStoreOrType")
    public Result<List<Room>> findByStoreOrType(@RequestParam(required = false) Long storeId,
                                                // 若 roomType 也可空，同样加 required = false；若必传则省略
                                                @RequestParam Long roomType) {
        List<Room> findByStoreOrType = roomServiceImpl.findByStoreOrType(storeId, roomType);
        return Result.success(findByStoreOrType);
    }
    @PostMapping("/updateRoom")
    @CacheEvict(cacheNames = "roomCache", allEntries = true)
    public Result<String> updateRoom(@RequestBody Room room) {
        roomServiceImpl.updateRoom(room);
        return Result.success("更新成功");
    }
    @GetMapping("/getRoomTypes")
    public Result<List<RoomType>> getRoomTypes() {
        List<RoomType> roomTypes = roomServiceImpl.getRoomTypes();
        return Result.success(roomTypes);
    }
}
