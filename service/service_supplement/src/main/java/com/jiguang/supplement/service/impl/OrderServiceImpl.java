package com.jiguang.supplement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiguang.supplement.entity.Order;
import com.jiguang.supplement.entity.OrderDescription;
import com.jiguang.supplement.entity.Vo.Member;
import com.jiguang.supplement.entity.Vo.OrderVo;
import com.jiguang.supplement.mapper.OrderMapper;
import com.jiguang.supplement.service.OrderDescriptionService;
import com.jiguang.supplement.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiguang.supplement.utils.OrderCodeFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {



    @Autowired
    RestTemplate restTemplate;


   @Autowired
    private RedisTemplate<String, Object> redisTemplate;


@Autowired
    OrderDescriptionService orderDescriptionService;


    @Override
    public List<OrderVo> selectOrder(String uId){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("supplement_number",uId);
        wrapper.eq("status",0);
        List<Order> orders=this.list(wrapper);

        List<OrderVo> orderVos=new ArrayList<>();
        for(Order i:orders){
            OrderVo orderVo=new OrderVo();
            BeanUtils.copyProperties(i, orderVo);

            String url="http://1.12.244.193:8033/driver/member/getById/"+i.getDriverId();

            Member member=restTemplate.getForObject(url,Member.class);

            orderVo.setDriver(member);

            QueryWrapper<OrderDescription> wrapper1=new QueryWrapper<>();
            wrapper1.eq("order_number",i.getOrderNumber());
            List<OrderDescription> orderDescriptions=orderDescriptionService.list(wrapper1);
            orderVo.setOrderDescriptions(orderDescriptions);
            orderVos.add(orderVo);
        }

        return orderVos;
    }

    @Override
    public List<OrderVo> selectAll(String uId) {
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("supplement_number",uId);
        List<Order> orders=this.list(wrapper);

        List<OrderVo> orderVos=new ArrayList<>();
        for(Order i:orders){
            OrderVo orderVo=new OrderVo();
            BeanUtils.copyProperties(i, orderVo);

          String url="http://1.12.244.193:80/driver/member/getById/"+i.getDriverId();

            Member member=restTemplate.getForObject(url,Member.class);

            orderVo.setDriver(member);
            QueryWrapper<OrderDescription> wrapper1=new QueryWrapper<>();
            wrapper1.eq("order_number",i.getOrderNumber());
            List<OrderDescription> orderDescriptions=orderDescriptionService.list(wrapper1);
            orderVo.setOrderDescriptions(orderDescriptions);
            orderVos.add(orderVo);
        }

        return orderVos;
    }






         @Override
         public  Integer goadd(OrderVo orderVo){
             Order order=new Order();
             BeanUtils.copyProperties(orderVo,order);
             Member member=orderVo.getDriver();
             order.setDriverId(member.getId());
             order.setStatus(0);
             order.setOrderNumber(OrderCodeFactory.getOrderCode(member.getId().toString()));
           Integer a=  this.baseMapper.insert(order);

             for(OrderDescription i:orderVo.getOrderDescriptions()){
                 i.setOrderNumber(order.getOrderNumber());
                 orderDescriptionService.save(i);
             }

             return  a;
    };




    //商家接收离线消息
        @Override
    public List<OrderVo> getUnreadOrders(String SupplementNumber) {
        String key = "unread_orders:" + SupplementNumber;
        List<Object> orders = redisTemplate.opsForList().range(key, 0, -1);
        if (orders != null && !orders.isEmpty()) {
            List<OrderVo> result = new ArrayList<>();
            for (Object obj : orders) {
                result.add((OrderVo)obj);
            }
            // 删除该商家所有未读订单
            redisTemplate.delete(key);
            return result;
        } else {
            return Collections.emptyList();
        }
    }


    //添加离线订单
   @Override
    public void addUnreadOrder(OrderVo order) {
        String key = "unread_orders:" + order.getSupplementNumber();
        redisTemplate.opsForList().rightPush(key, order);
    }









}
