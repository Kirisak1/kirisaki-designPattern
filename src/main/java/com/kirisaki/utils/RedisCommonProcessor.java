package com.kirisaki.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCommonProcessor {
    //依赖注入我们定制好的 redisTemplate
    @Autowired
    private RedisTemplate redisTemplate;

    //通过 key 获取 value
    public Object get(String key) {
        if (key == null) {
            throw new UnsupportedOperationException("Redis key could not be null");
        }
        return redisTemplate.opsForValue().get(key);
    }

    //向 Redis 中存入 key:value 数据对
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    //向 Redis 中存入 key:value 数据对,并支持过期时间
    public void set(String key, Object value, long time) {
        if (time > 0) {
            redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
        }else {
            set(key, value);
        }
    }
}
