package com.lcjing.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author lcjing
 * @date 2020/08/10
 */
@SpringBootApplication
@MapperScan("com.lcjing.demo.mapper")
public class QuartzApp {

    public static void main(String[] args) {
        SpringApplication.run(QuartzApp.class, args);
    }
}
