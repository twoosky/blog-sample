package com.example.stock.facaed;

import com.example.stock.domain.Stock;
import com.example.stock.facade.RedissonLockStockFacade;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedissonLockStockFacadeTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private RedissonLockStockFacade redissonLockStockFacade;

    @BeforeEach
    public void before() {
        Stock stock = new Stock(1L, 100L);
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    public void 동시에_100개_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    redissonLockStockFacade.decrease(1L, 1L);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();
        assertThat(stock.getQuantity()).isEqualTo(0L);
    }
}
