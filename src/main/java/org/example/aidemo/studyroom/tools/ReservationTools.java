package org.example.aidemo.studyroom.tools;

import dev.langchain4j.agent.tool.Tool;
import org.example.aidemo.mail.service.EmailService;
import org.example.aidemo.studyroom.entity.Reservation;
import org.example.aidemo.studyroom.entity.Room;
import org.example.aidemo.studyroom.service.ReservationService;
import org.example.aidemo.studyroom.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReservationTools {
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private EmailService emailService;
    @Tool(name="预约自习室", value = "调用bookRoom函数进行预约，可根据roomId进行预约")
    public String bookRoom(Reservation reservation){
        try {
            String reservation1 = reservationService.reserveRoom(reservation);
            reservation.setId(null);//防止大模型幻觉设置了id
            return reservation1;
        }catch (Exception e){
            return "预约失败"+e.getMessage();
        }

    }
    @Tool(name = "根据分店和房型查询空位", value = "根据分店StoreId和房间类型roomTypeId,查询自习室空位，用户选择具体房间后，将房间的roomId传给Reservation的roomId")
    public List<Room> queryEmptyRoom(Long storeId, Long roomTypeId){

        return roomService.findByStoreOrTypeAvailable(storeId,roomTypeId);

    }
//    @Tool(name = "获取唯一roomId", value = "查询到空位后，用户选择去具体房间，根据")
}
