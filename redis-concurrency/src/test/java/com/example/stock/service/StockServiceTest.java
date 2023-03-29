package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

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
    public void stock_decrease() {
        stockService.decrease(1L, 1L);

        Stock stock = stockRepository.findById(1L).orElseThrow();
        assertThat(stock.getQuantity()).isEqualTo(99);
    }

    /*
    ExecutorService: 비동기로 실행하는 작업을 단순화하여 사용할 수 있도록 도와주는 Java의 API
    CountDownLatch: 다른 스레드에서 실행 중인 작업이 완료될 때까지 대기할 수 있도록 도와주는 클래스
     */

    /*
    1. 100개 재고 저장 (BeforeEach)
    2. 100개의 스레드에서 재고를 1씩 감소
    3. 실행이 끝난 후 재고는 0이 될거라 예상
     */

    /*
    예상과 다른 결과가 발생한 이유는 Race Condition이 일어났기 때문
    Race Condition은 둘 이상의 스레드가 공유 데이터에 접근할 수 있는 경우, 공유 데이터를 동시에 변경하려 할 때 발생하는 문제
    해결하기 위해선 하나의 스레드가 작업이 완료된 후에 다른 스레드가 공유 데이터에 접근할 수 있도록 하면 된다.
     */

    @Test
    public void 동시에_100개_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(threadCount);

        for(int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(1L, 1L);
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
