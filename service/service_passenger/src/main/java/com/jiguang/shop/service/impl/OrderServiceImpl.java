package com.jiguang.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiguang.shop.entity.Order;
import com.jiguang.shop.entity.OrderDescription;
import com.jiguang.shop.entity.ProductEvaluate;
import com.jiguang.shop.entity.Vo.OrderVo;
import com.jiguang.shop.mapper.OrderMapper;
import com.jiguang.shop.service.OrderDescriptionService;
import com.jiguang.shop.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiguang.shop.service.ProductEvaluateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {



    @Autowired
    OrderDescriptionService orderDescriptionService;

     @Autowired
    ProductEvaluateService productEvaluateService;

    @Override
    public List<OrderVo> selectAll(String uId) {
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("member_id",uId);
        List<Order> orders=this.list(wrapper);

        List<OrderVo> orderVos=new ArrayList<>();

        for(Order i:orders){
            OrderVo orderVo=new OrderVo();
            BeanUtils.copyProperties(i, orderVo);

            //添加订单项信息
            QueryWrapper<OrderDescription> wrapper1=new QueryWrapper<>();
            wrapper1.eq("order_number",i.getOrderNumber());
            List<OrderDescription> orderDescriptions=orderDescriptionService.list(wrapper1);
            orderVo.setOrderDescriptions(orderDescriptions);

            //添加订单评论信息
            QueryWrapper<ProductEvaluate>  wrapper2=new QueryWrapper<>();
            wrapper2.eq("order_number",i.getOrderNumber());
           List<ProductEvaluate>productEvaluate=productEvaluateService.list(wrapper2);
           orderVo.setProductEvaluate(productEvaluate);

            orderVos.add(orderVo);


        }



        return orderVos;
    }
}
