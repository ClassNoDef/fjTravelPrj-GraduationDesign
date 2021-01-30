package com.travel.fj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@MapperScan("com.travel.fj.dao")
@EnableAsync
public class FjApplication {

    public static void main(String[] args) {
        SpringApplication.run(FjApplication.class, args);
    }
}
