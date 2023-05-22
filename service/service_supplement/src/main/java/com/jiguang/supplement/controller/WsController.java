package com.jiguang.supplement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@CrossOrigin //跨域
@RequestMapping("/ws")
public class WsController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void sendOrderRequest(String orderId, String message) {
        stringRedisTemplate.convertAndSend("order-request", orderId + ":" + message);
    }

}
