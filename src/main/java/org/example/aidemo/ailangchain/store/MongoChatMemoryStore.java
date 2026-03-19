package org.example.aidemo.ailangchain.store;

import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageDeserializer;
import dev.langchain4j.data.message.ChatMessageSerializer;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import org.example.aidemo.ailangchain.bean.ChatMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.List;
@Component
public class MongoChatMemoryStore implements ChatMemoryStore {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public List<ChatMessage> getMessages(Object o) {
        Criteria criteria=Criteria.where("memoryId").is(o);
        Query queru=new Query(criteria);
        ChatMessages chatMessages = mongoTemplate.findOne(queru, ChatMessages.class);
        if (chatMessages == null)
            return new LinkedList<>();
        String content = chatMessages.getContent();

        return ChatMessageDeserializer.messagesFromJson(content);
    }

    //更新聊天记录
    @Override
    public void updateMessages(Object o, List<ChatMessage> list) {
        Criteria criteria = Criteria.where("memoryId").is(o);
        Query query = new Query(criteria);
        Update update = new Update().set("content", ChatMessageSerializer.messagesToJson(list));
        mongoTemplate.upsert(query, update, ChatMessages.class);
    }

    //删除聊天记录
    @Override
    public void deleteMessages(Object memoryId) {
        Criteria criteria = Criteria.where("memoryId").is(memoryId);
        Query query = new Query(criteria);
        mongoTemplate.remove(query, ChatMessages.class);
    }
}
