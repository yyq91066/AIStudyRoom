package org.example.aidemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void testRedis() {

        redisTemplate.opsForValue().set("name", "张三");
        String name = (String) redisTemplate.opsForValue().get("name");
        System.out.println(name);
    }
}
