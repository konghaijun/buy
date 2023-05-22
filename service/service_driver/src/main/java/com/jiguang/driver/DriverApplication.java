package com.jiguang.driver;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@MapperScan("com.jiguang.driver.mapper")
@ComponentScan(basePackages = {"com.jiguang"})
public class  DriverApplication {
    public static void main(String[] args) {
     SpringApplication.run(DriverApplication.class,args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }

}
