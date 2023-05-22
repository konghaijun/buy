package com.jiguang.shop.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.jiguang.commonutils.R;
import com.jiguang.shop.entity.Order;
import com.jiguang.shop.entity.OrderDescription;

import com.jiguang.shop.entity.Vo.AddOrder;
import com.jiguang.shop.entity.Vo.OrderVo;
import com.jiguang.shop.entity.Vo.SendOrderVo;
import com.jiguang.shop.service.OrderDescriptionService;
import com.jiguang.shop.service.OrderService;
import com.jiguang.shop.service.UcenterMemberService;
import com.jiguang.shop.utils.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@CrossOrigin //跨域
@RequestMapping("/shop/order")
public class OrderController {

      @Autowired
    OrderService orderService;

      @Autowired
    OrderDescriptionService orderDescriptionService;

      @Autowired
    UcenterMemberService ucenterMemberService;

    @GetMapping("select/{uId}")
    public R selectAll(@PathVariable String uId) {
    List<OrderVo> orderVo=orderService.selectAll(uId);
          return R.ok().data("order",orderVo);
    }


     //查看今天的订单数量
    @GetMapping("selectDay/{day}")
    public R selectDay(@PathVariable String day) throws ParseException{
        SimpleDateFormat Format=new SimpleDateFormat("yyyy-MM-dd");
        //通过SimpleDateFormat对象的parse()方法将指定字符串转换为Date对象
        Date date=Format.parse(day);
        Calendar begin=Calendar.getInstance();

        //今天
        begin.setTime(date);
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.like("gmt_create",Format.format(begin.getTime()));



        begin.add(Calendar.DAY_OF_MONTH, -1);
        QueryWrapper<Order> wrapper1=new QueryWrapper<>();
        wrapper1.like("gmt_create",Format.format(begin.getTime()));


       int number =orderService.count(wrapper);
       int number1 =orderService.count(wrapper1);

       Map map =new HashMap();
       map.put("number",number);
       double result=0;

       if(number!=0){
             result=(number-number1)*100/number;
       }


       if(result>0){
           map.put("compare", "+"+result +"%");
       }
       else {map.put("compare", result +"%");}

        return R.ok().data("map",map);
    }



     //查看今日收入
    @GetMapping("selectM/{day}")
    public R selectM(@PathVariable String day) throws ParseException{
        SimpleDateFormat Format=new SimpleDateFormat("yyyy-MM-dd");
        //通过SimpleDateFormat对象的parse()方法将指定字符串转换为Date对象
        Date date=Format.parse(day);
        Calendar begin=Calendar.getInstance();

        //今天
        begin.setTime(date);
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.like("gmt_create",Format.format(begin.getTime()));



        begin.add(Calendar.DAY_OF_MONTH, -1);
        QueryWrapper<Order> wrapper1=new QueryWrapper<>();
        wrapper1.like("gmt_create",Format.format(begin.getTime()));


      List<Order> list=orderService.list(wrapper);
      List<Order> list1=orderService.list(wrapper1);

      BigDecimal number=new BigDecimal(0);
      for(Order i:list){
        number=number.add(i.getActualPrice());
      }

        BigDecimal number1=new BigDecimal(0);
        for(Order i:list1){
            number1=number1.add(i.getActualPrice());
        }

      BigDecimal result =new BigDecimal(0);
        if (number.compareTo(BigDecimal.ZERO) != 0) {

            // A 不等于 0，执行相应操作
           result = number.subtract(number1)   // 计算差
                    .multiply(BigDecimal.valueOf(100))  // 乘以 100
                    .divide(number, 2, RoundingMode.HALF_UP);  // 除以 A，并保留两位小数

        }



        Map map=new HashMap();
        map.put("number",number);
      //  String value = String.valueOf(number.divide(number1, 2, RoundingMode.HALF_UP));


        if (result.compareTo(BigDecimal.ZERO) >0) {
            map.put("compare", "+" +result + "%");
        }else {
            map.put("compare", result + "%");
        }

        return R.ok().data("map",map);
    }




    //添加订单
    @PostMapping("add")
    public  R add(@RequestBody AddOrder addOrder)
    {
        Order order=new Order();
        String orderNumber= OrderCodeFactory.getOrderCode(addOrder.getMemberId());
        order.setOrderNumber(orderNumber);
        order.setMemberId(addOrder.getMemberId());
        order.setActualPrice(addOrder.getActualPrice());
        order.setCouponDeduction(addOrder.getCouponDeduction());
        order.setTotalPrice(addOrder.getTotalPrice());


        addOrder.getOrderDescriptions().setOrderNumber(orderNumber);
        boolean f1=orderDescriptionService.save(addOrder.getOrderDescriptions());

      boolean f= orderService.save(order);
       if(f&&f1) {
           return R.ok();
       }else {
           return R.error();
       }
    }


    //日收益量 图
    @GetMapping("selectP/{day}")
    public  R selectP(@PathVariable String day)
    {
        String[] strings=new String[10];
        int num=0;
        strings[num]=day+" 00:00:00";
        List tx=new ArrayList();
        List ty=new ArrayList();

        for(int i=3;i<=24;i=i+3){
            String s;
            if(i<=9) {
                s = day + " 0";    tx.add("0"+i+":00");
            }else {
                s = day + " ";    tx.add(i+":00");
            }

            s=s+i+":00:00"; num++;
            strings[num]=s;



            QueryWrapper<Order> wrapper=new QueryWrapper<>();

            if(i!=24) {
                wrapper.ge("gmt_create", strings[num - 1]).lt("gmt_create", strings[num]);
            } else {
                String s1 = day + " 23:59:59";
                wrapper.ge("gmt_create", strings[num - 1]).lt("gmt_create",s1);
            }





            List<Order> list=orderService.list(wrapper);
             BigDecimal m=new BigDecimal(0);

            for(Order order:list)
            {
                m=m.add(order.getActualPrice());
            }
            ty.add(m);

        }

        Map<String,List> map=new HashMap();
        map.put("tx",tx);
        map.put("ty",ty);

        return R.ok().data("map",map);
    }

    //本月订单数
 @GetMapping("getM")
    public R getM(){
     LocalDate date = LocalDate.now();
     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
     String mon = date.format(formatter);
     QueryWrapper<Order> wrapper=new QueryWrapper<>();
     wrapper.like("gmt_create",mon);
     List<Order> orders=orderService.list(wrapper);
     BigDecimal num=new BigDecimal(0);
     for(Order i:orders){
         num=num.add(i.getActualPrice());
     }

     Map map=new HashMap();
     map.put("orderNumber",orders.size());
     map.put("orderPrice",num);
     return R.ok().data("map",map);

 }



  //销售图
    @GetMapping("orderP")
    public  R orderP(){

        List tx=new ArrayList();
        List oty=new ArrayList();
        List pty=new ArrayList();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        for (int i = 11; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
          if(i!=0) {
              calendar.add(Calendar.MONTH, -i);
          }
            Date date = calendar.getTime();
            String format = dateFormat.format(date);
            tx.add(tx(format));

            QueryWrapper<Order> wrapper=new QueryWrapper<>();
            wrapper.like("gmt_create",format);

            List<Order> orders=orderService.list(wrapper);



            oty.add(orders.size());
            BigDecimal num=new BigDecimal(0);
            for(Order k:orders){
                num=num.add(k.getActualPrice());
            }
            pty.add(num);

        }

       Map map=new HashMap();
        map.put("tx",tx);
        map.put("oty",oty);
        map.put("pty",pty);

        return R.ok().data("map",map);
    }




    //本月的订单信息
  @GetMapping("getAllM")
    public  R getAllM(){
      LocalDate date = LocalDate.now();
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
      String mon = date.format(formatter);
      QueryWrapper<Order> wrapper=new QueryWrapper<>();
      wrapper.like("gmt_create",mon);
      List<Order> orders=orderService.list(wrapper);
      return R.ok().data("list",orders);
    }


    public String tx(String s){
        String s1=s.substring(5,7);
        System.out.println(s1);
        if(s1.equals("01")) {
            return "一月";
        }if(s1.equals("02")) {
            return "二月";
        }if(s1.equals("03")) {
            return "三月";
        }if(s1.equals("04")) {
            return "四月";
        }if(s1.equals("05")) {
            return "五月";
        }if(s1.equals("06")) {
            return "六月";
        }if(s1.equals("07")) {
            return "七月";
        }if(s1.equals("08")) {
            return "八月";
        }if(s1.equals("09")) {
            return "九月";
        }if(s1.equals("10")) {
            return "十月";
        }if(s1.equals("11")) {
            return "十一月";
        }if(s1.equals("12")) {
            return "十二月";
        } return "0";
    }




    //查询某月账单信息用司机端信息
    @GetMapping("getMonthDriver/{month}/{driver}")
    public R getMonthDriver(@PathVariable String month ,@PathVariable String driver){
      QueryWrapper<Order> wrapper=new QueryWrapper<>();
      wrapper.like("gmt_create",month);
      wrapper.eq("machine_number",driver);

      List<SendOrderVo> list=new ArrayList<>();
      List<Order> orders=orderService.list(wrapper);
      for(Order i:orders){
          QueryWrapper wrapper1=new QueryWrapper();
          wrapper1.eq("order_number",i.getOrderNumber());
          List<OrderDescription> orderDescriptions=orderDescriptionService.list(wrapper1);
          for(OrderDescription k:orderDescriptions){
              SendOrderVo orderVo=new SendOrderVo();
              orderVo.setGmtCreate(i.getGmtCreate());
              orderVo.setProductCondition(1);
              orderVo.setNickname(ucenterMemberService.getById(i.getMemberId()).getNickname());
              orderVo.setPicture(ucenterMemberService.getById(i.getMemberId()).getAvatar());
              orderVo.setOrderPrice(i.getTotalPrice());
              orderVo.setProductName(k.getProductName());
              orderVo.setProductNumber(k.getProductNum());
              list.add(orderVo);
          }
      }

      return R.ok().data("list",list);
    }


}

