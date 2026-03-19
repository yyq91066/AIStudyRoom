package org.example.aidemo;

import org.example.aidemo.ailangchain.bean.ChatMessages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoDBTest {
    @Autowired
    private MongoTemplate mongoTemplate;
     @Test
    public  void testMongoDBInsert() {
         ChatMessages  chatMessages = new ChatMessages();
          chatMessages.setContent("hello world");
           mongoTemplate.insert(chatMessages);

    }
     @Test
     public  void testMongoDBFindById() {
         ChatMessages  chatMessages = mongoTemplate.findById("695f27b60c12bc3e69a9ed98", ChatMessages.class);
         System.out.println(chatMessages);
    }
      @Test
       public  void testMongoDBUpdate() {
         //找不到该id则新增
          Criteria  criteria = Criteria.where("_id").is("695f27b60c12bc3e69a9ed98");
          Query query = new Query(criteria);
          Update update = new Update().set("content", "新的聊天记录");
          mongoTemplate .updateFirst(query, update, ChatMessages.class);
      }
       @Test
        public  void testMongoDBDelete() {
          Criteria  criteria = Criteria.where("_id").is(1);
          Query query = new Query(criteria);
          mongoTemplate .remove(query, ChatMessages.class);
      }

}
