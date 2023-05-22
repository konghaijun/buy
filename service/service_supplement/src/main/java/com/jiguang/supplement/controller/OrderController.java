package com.jiguang.supplement.controller;


import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;


import com.jiguang.commonutils.R;
import com.jiguang.supplement.entity.*;
import com.jiguang.supplement.entity.Vo.*;
import com.jiguang.supplement.service.OrderDescriptionService;
import com.jiguang.supplement.service.OrderService;
import com.jiguang.supplement.service.ProductSellService;
import com.jiguang.supplement.utils.OrderCodeFactory;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin //跨域
@RequestMapping("/supplement/order")
public class OrderController {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

@Autowired
private OrderDescriptionService orderDescriptionService;


    @Autowired
    MongoTemplate mongoTemplate;

@Autowired
    ProductSellService productSellService;

    @Autowired
    OrderService orderService;

    @PostMapping( "alipaytest")
    public void alipaytest(HttpServletRequest httpRequest,
                           HttpServletResponse httpResponse) throws IOException, ParseException {
        //构造sdk的客户端对象
        httpRequest.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("text/html;charset=UTF-8");

//获取用户id


        //生成订单编号
        String orderNumber= OrderCodeFactory.getOrderCode(httpRequest.getParameter("uId"));


        Order order=new Order();
        order.setOrderNumber(orderNumber);


        String s1=httpRequest.getParameter("orderPrice");

        System.out.println(s1);



        System.out.println(new BigDecimal(httpRequest.getParameter("orderPrice")));

        order.setOrderPrice(new BigDecimal(httpRequest.getParameter("orderPrice")));


        order.setDriverId(Long.parseLong(httpRequest.getParameter("uId")));
        order.setSupplementNumber(httpRequest.getParameter("supplementNumber"));


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String s=httpRequest.getParameter("bookTime");

        Date date = dateFormat.parse(s);
        order.setBookTime(date);
        order.setStatus(0);
        orderService.save(order);


        String oders = httpRequest.getParameter("description");
        List<OrderDescription> list = JSON.parseArray(oders, OrderDescription.class);


        for (OrderDescription description : list) {
            description.setOrderNumber(orderNumber);

            //修改商品销售
            UpdateWrapper<ProductSell>  wrapper=new UpdateWrapper<>();
            wrapper.eq("id",description.getProductId());

            ProductSell productSell=productSellService.getById(description.getProductId());
            if(productSell!=null) {
                ProductSell productSell1=new ProductSell();
                productSell1.setProductNumber(productSell.getProductNumber() + description.getProductNum());
                productSellService.update(productSell1,wrapper);
            }else{
                ProductSell productSell1=new ProductSell();
                productSell1.setProductNumber(description.getProductNum());
                productSell1.setProductName(description.getProductName());
                productSellService.save(productSell1);
                //把售货站查出来
                Query query=new Query();
                Criteria criteria = Criteria.where("supplementNumber").is(order.getSupplementNumber());
                query.addCriteria(criteria);
                SupplementMap supplementMap=mongoTemplate.findOne(query,SupplementMap.class);

            }


            orderDescriptionService.save(description);
        }







        AlipayClient alipayClient = new DefaultAlipayClient(YmlParament.ServerUrl, YmlParament.APP_ID,
                YmlParament.APP_Privatekey, YmlParament.Format, YmlParament.Charset,
                YmlParament.APP_Publickey, YmlParament.SignType); //获得初始化的AlipayClient



        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
//        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
//        alipayRequest.setNotifyUrl("http://domain.com/CallBack/notify_url.jsp");//在公共参数中设置回跳和通知地址
      /*  alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"20150420010101017\"," +
                " \"total_amount\":\"88.88\"," +
                " \"subject\":\"Iphone6 16G\"," +
                " \"product_code\":\"QUICK_WAP_PAY\"" +
                " }");//填充业务参数
*/


        alipayRequest.putOtherTextParam("out_trade_no",orderNumber);
        alipayRequest.putOtherTextParam("total_amount",httpRequest.getParameter("orderPrice"));
        alipayRequest.putOtherTextParam("subject","随手买");
        alipayRequest.putOtherTextParam("product_code","QUICK_WAP_PAY");


        String form="";
        try {
            //请求支付宝下单接口,发起http请求
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + YmlParament.Charset);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
    }



    @GetMapping("select/{supplementNumber}")
    public R selectAll(@PathVariable String supplementNumber) {
        List<OrderVo> orderVo=orderService.selectAll(supplementNumber);
        return R.ok().data("order",orderVo);
    }





 /*   //司机发来的订单
   @PostMapping("add")
    public  R add(@RequestBody OrderVo orderVo){

       String key = "order:" + orderVo.getOrderNumber();
       redisTemplate.opsForValue().set(key, orderVo);
       // 设置订单过期时间为30分钟
       redisTemplate.expire(key, 30, TimeUnit.MINUTES);
       orderService.addUnreadOrder(orderVo);
    Integer a=  orderService.goadd(orderVo);
       if(a>0) {
           return R.ok();
       }else {
           return R.error();
       }

   }

*/








    //完成订单的时候
 //   @PostMapping()






//商家接收离线的订单
















   //查看司机发来的订单
    @GetMapping("selectOrder/{supplementNumber}")
    public R selectOrder(@PathVariable String supplementNumber){
        List<OrderVo> orderVo=orderService.selectOrder(supplementNumber);
        return  R.ok().data("orderVo",orderVo);
    }


   //接单
    @PostMapping("receive")
           public R receive(@RequestBody receive receive)
   {
       UpdateWrapper<Order> wrapper=new UpdateWrapper<Order>();
       wrapper.eq("order_number",receive.getOrderNumber());
       Order order=new Order();
       order.setStatus(receive.getStatus());
     Boolean a=orderService.update(order,wrapper);
      if(a) {
          return R.ok();
      } else {
          return R.error();
      }
   }

  //拒接订单

@GetMapping("send")
    public send s(){
        send se=new send();
    Map map=new HashMap(
    );
    map.put("lock",1);
        se.setLockData(map);
    System.out.println(se);
    return se;

}







    //查询某月账单信息用司机端信息
    @GetMapping("getMonthDriver/{month}/{driver}")
    public R getMonthDriver(@PathVariable String month , @PathVariable Long driver){
        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.like("gmt_create",month);
        wrapper.eq("driver_id",driver);

        List<SendOrderVo> list=new ArrayList<>();

        List<Order> orders=orderService.list(wrapper);

        for(Order i:orders){
            QueryWrapper wrapper1=new QueryWrapper();
            wrapper1.eq("order_number",i.getOrderNumber());
            List<OrderDescription> orderDescriptions=orderDescriptionService.list(wrapper1);

            Query query = new Query();
            query.addCriteria(Criteria.where("supplementNumber").is(Integer.parseInt(i.getSupplementNumber())));

            List<SupplementMap> supplementMap=mongoTemplate.find(query, SupplementMap.class);

            for(OrderDescription k:orderDescriptions){
                SendOrderVo orderVo=new SendOrderVo();
                orderVo.setGmtCreate(i.getGmtCreate());
                orderVo.setProductCondition(0);
                orderVo.setNickname(supplementMap.get(0).getName());
               orderVo.setPicture(supplementMap.get(0).getPicture());
                orderVo.setOrderPrice(new BigDecimal(k.getProductPrice()));
                orderVo.setProductName(k.getProductName());
                orderVo.setProductNumber(k.getProductNum());
                list.add(orderVo);
            }
        }

        return R.ok().data("list",list);
    }





@GetMapping("dayP")
    public R dayP(){
        BigDecimal bigDecimal=new BigDecimal(0);
        List<String> tx=new ArrayList<>();
        List<Integer> ty=new ArrayList<>();
        List<BigDecimal> oy=new ArrayList<>();
        tx.add("03:00");
        tx.add("09:00");
        tx.add("15:00");
        tx.add("21:00");
    Date currentTime = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String dateString = formatter.format(currentTime);

    for(int i=0;i<4;i++) {
        QueryWrapper<Order> wrapper = new QueryWrapper();
        //小于 大于等于
     if(i==0) {
         wrapper.lt("gmt_create", dateString + " " + tx.get(i)).ge("gmt_create",dateString + " " + "00:00");
     }else if(i==3) {
            wrapper.ge("gmt_create", dateString + " " + tx.get(i)).lt("gmt_create",dateString + " " + "23:59");
        }else{
         wrapper.ge("gmt_create", dateString + " " + tx.get(i-1)).lt("gmt_create",dateString + " " + tx.get(i));
     }

     List<Order> orders=orderService.list(wrapper);
     ty.add(orders.size());
  BigDecimal num=new BigDecimal(0);
     for(Order j:orders)
     {
       num=num.add(j.getOrderPrice());
       bigDecimal=bigDecimal.add(j.getOrderPrice());
     }
   oy.add(num);

    }

    Map map=new HashMap();
    map.put("tx",tx);
    map.put("ty",ty);
    map.put("oy",oy);
    map.put("price",bigDecimal);
        return R.ok().data("map",map);
}


@GetMapping("big")
    public  R big(){
        return R.ok().data("big",101.4);
}




//___________________________________________________________________________________________________________









}

