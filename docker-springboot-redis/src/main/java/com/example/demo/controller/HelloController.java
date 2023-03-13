package com.example.demo.controller;

import com.example.demo.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final RedisRepository redisRepository;

    @PostConstruct
    public void init() {
        redisRepository.set("number", 0);
    }

    @GetMapping("/")
    public String hello() {
        Integer num = redisRepository.get("number");
        redisRepository.set("number", num + 1);
        return "숫자가 1씩 올라갑니다. 숫자: " + num;
    }
}
