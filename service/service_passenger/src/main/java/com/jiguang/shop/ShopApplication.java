package com.jiguang.shop;




import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@MapperScan("com.jiguang.shop.mapper")
@ComponentScan(basePackages = {"com.jiguang"})
@EnableScheduling //开启定时任务
public class ShopApplication {
    public static void main(String[] args) {
      //  ChatClient.class.s
      SpringApplication.run(ShopApplication.class,args);

    }
}
