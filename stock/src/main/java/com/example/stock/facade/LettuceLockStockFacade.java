package com.example.stock.facade;

import com.example.stock.repository.RedisLockRepository;
import com.example.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {

    private final RedisLockRepository redisLockRepository;
    private final StockService stockService;

    /* StockId를 key로 사용, Reids의 SETNX 명령을 통해 lock 획득
    lock 획득이 별거는 아니고, Redis에 key:value 값이 저장되면 lock 획득,
    key에 대한 값이 이미 존재해 저장에 실패하면 lock 획득할 때까지 재시도 (다른 스레드가 lock을 해제할 때까지)
     */
    public void decrease(Long id, Long quantity) throws InterruptedException {
        while(!redisLockRepository.lock(id)) {
            Thread.sleep(100);
        }

        try {
            stockService.decrease(id, quantity);
        } finally {
            redisLockRepository.unlock(id);
        }
    }
}
