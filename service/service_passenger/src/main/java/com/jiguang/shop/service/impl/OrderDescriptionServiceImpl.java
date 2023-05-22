package com.jiguang.shop.service.impl;

import com.jiguang.shop.entity.OrderDescription;
import com.jiguang.shop.mapper.OrderDescriptionMapper;
import com.jiguang.shop.service.OrderDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class OrderDescriptionServiceImpl extends ServiceImpl<OrderDescriptionMapper, OrderDescription> implements OrderDescriptionService {

}
