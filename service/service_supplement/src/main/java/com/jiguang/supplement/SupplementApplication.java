package com.jiguang.supplement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;


@MapperScan("com.jiguang.supplement.mapper")
@SpringBootApplication
public class SupplementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupplementApplication.class, args);
    }


    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }


}
