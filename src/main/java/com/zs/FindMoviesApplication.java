package com.zs;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.colin.mapper")
@SpringBootApplication(scanBasePackages = "com.zs")
public class FindMoviesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FindMoviesApplication.class, args);
    }

}
