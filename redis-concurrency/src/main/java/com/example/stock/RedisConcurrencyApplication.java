package com.example.stock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisConcurrencyApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisConcurrencyApplication.class, args);
	}

}
