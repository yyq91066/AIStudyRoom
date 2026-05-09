package org.example.aidemo.common.controller;

import org.example.aidemo.common.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * Redis日志测试控制器
 * 用于测试Redis操作日志功能
 */
@RestController
@RequestMapping("/api/test/redis")
@CrossOrigin
public class RedisLogTestController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 测试Redis set操作
     */
    @PostMapping("/set")
    public Result testSet(@RequestParam String key, @RequestParam String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, 5, TimeUnit.MINUTES);
            return Result.success("Redis set操作成功");
        } catch (Exception e) {
            return Result.error("Redis set操作失败: " + e.getMessage());
        }
    }

    /**
     * 测试Redis get操作
     */
    @GetMapping("/get")
    public Result testGet(@RequestParam String key) {
        try {
            String value = stringRedisTemplate.opsForValue().get(key);
            return Result.success(value);
        } catch (Exception e) {
            return Result.error("Redis get操作失败: " + e.getMessage());
        }
    }

    /**
     * 测试Redis delete操作
     */
    @DeleteMapping("/delete")
    public Result testDelete(@RequestParam String key) {
        try {
            Boolean result = stringRedisTemplate.delete(key);
            return Result.success("删除结果: " + result);
        } catch (Exception e) {
            return Result.error("Redis delete操作失败: " + e.getMessage());
        }
    }

    /**
     * 测试Redis hasKey操作
     */
    @GetMapping("/hasKey")
    public Result testHasKey(@RequestParam String key) {
        try {
            Boolean result = stringRedisTemplate.hasKey(key);
            return Result.success("键是否存在: " + result);
        } catch (Exception e) {
            return Result.error("Redis hasKey操作失败: " + e.getMessage());
        }
    }
}
