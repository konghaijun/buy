package com.jiguang.supplement.service;

import com.jiguang.supplement.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiguang.supplement.entity.Vo.OrderVo;

import java.util.List;

public interface OrderService extends IService<Order> {


    List<OrderVo> selectOrder(String uId);

    List<OrderVo> selectAll(String uId);

Integer  goadd(OrderVo orderVo);
  List<OrderVo> getUnreadOrders(String merchantId);
    void addUnreadOrder(OrderVo order);


}
