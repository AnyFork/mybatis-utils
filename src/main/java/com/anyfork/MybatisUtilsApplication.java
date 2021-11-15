package com.anyfork;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@MapperScan(basePackages = {"com.anyfork.modules.*.mapper"})
@EnableTransactionManagement
@Slf4j
public class MybatisUtilsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisUtilsApplication.class, args);
    }

}
