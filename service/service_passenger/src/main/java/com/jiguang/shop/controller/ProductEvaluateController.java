package com.jiguang.shop.controller;


import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.jiguang.commonutils.R;
import com.jiguang.shop.entity.Order;
import com.jiguang.shop.entity.ProductEvaluate;
import com.jiguang.shop.service.OrderService;
import com.jiguang.shop.service.ProductEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin //跨域
@RequestMapping("/shop/evaluate")
public class ProductEvaluateController {

    @Autowired
    ProductEvaluateService productEvaluateService;

    @Autowired
    OrderService orderService;

     //添加评价
    @PostMapping("add")
    public R add(@RequestBody ProductEvaluate productEvaluate){
        productEvaluateService.save(productEvaluate);
        UpdateWrapper<Order> wrapper=new UpdateWrapper<Order>();
        wrapper.eq("order_number",productEvaluate.getOrderNumber());
        Order order=new Order();
        order.setEvaluationStatus(1);
        orderService.update(order,wrapper);
        return  R.ok();
    }



}

