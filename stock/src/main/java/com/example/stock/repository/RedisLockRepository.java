package com.example.stock.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;


@Component
@RequiredArgsConstructor
public class RedisLockRepository {

    private final RedisTemplate<String, String> redisTemplate;

    /*
    key는 stockId, value는 lock으로 설정해 Redis에 SET
    로직을 시작하면 lock 메서드로 Redis에 Key에 대한 값을 SET해 lock 점유
    로직이 끝나면 unlock 메서드로 key에 대한 vlaue 삭제해 lock을 해제
    로직 실행 전 후로 lock 획득과 해제를 해줘야 하므로 Facade 클래스 생성
     */
    public Boolean lock(Long key) {
        return redisTemplate
                .opsForValue()
                .setIfAbsent(generateKey(key), "lock", Duration.ofMillis(3_0900));
    }

    public Boolean unlock(Long key) {
        return redisTemplate.delete(generateKey(key));
    }

    private String generateKey(Long key) {
        return key.toString();
    }
}
