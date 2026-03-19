package org.example.aidemo;

import org.example.aidemo.ailangchain.bean.ChatMessages;
import org.example.aidemo.studyroom.entity.Room;
import org.example.aidemo.studyroom.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class projectTest {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public  void testMongoDBInsert() {
        ChatMessages  chatMessages = new ChatMessages();
        chatMessages.setContent("hello world");
        mongoTemplate.insert(chatMessages);

    }
    @Autowired
    private RoomService roomServiceImpl;
    @Test
    public  void test1() {
        Room room=new Room();
        room.setId(1L);
        room.setStatus(2);
        roomServiceImpl.updateRoom(room);
    }

}
