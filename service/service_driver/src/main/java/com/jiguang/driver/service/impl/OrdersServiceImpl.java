package com.jiguang.driver.service.impl;

import com.jiguang.driver.entity.Orders;
import com.jiguang.driver.mapper.OrdersMapper;
import com.jiguang.driver.service.OrdersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-04
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {

}
