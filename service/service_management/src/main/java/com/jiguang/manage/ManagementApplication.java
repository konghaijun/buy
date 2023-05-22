package com.jiguang.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan(basePackages = {"com.jiguang"})
@MapperScan("com.jiguang.manage.mapper")
@EnableScheduling //开启定时任务
public class ManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class,args);
    }
}
