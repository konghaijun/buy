package com.jiguang.shop.service;

import com.jiguang.shop.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiguang.shop.entity.Vo.OrderVo;

import java.util.List;


public interface OrderService extends IService<Order> {


    List<OrderVo> selectAll(String uId);
}
