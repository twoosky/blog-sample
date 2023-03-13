package com.example.demo.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {
    private final RedisTemplate<String, Integer> redisTemplate;

    public Integer get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void set(String key, Integer value) {
        redisTemplate.opsForValue().set(key, value);
    }
}
