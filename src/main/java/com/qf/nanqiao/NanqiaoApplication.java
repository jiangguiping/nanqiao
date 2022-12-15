package com.qf.nanqiao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.qf.nanqiao.dao")
public class NanqiaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(NanqiaoApplication.class, args);
    }

}
