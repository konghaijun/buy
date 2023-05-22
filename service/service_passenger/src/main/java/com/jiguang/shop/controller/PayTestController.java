package com.jiguang.shop.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.jiguang.shop.entity.Order;
import com.jiguang.shop.entity.OrderDescription;
import com.jiguang.shop.entity.YmlParament;
import com.jiguang.shop.service.OrderDescriptionService;
import com.jiguang.shop.service.OrderService;
import com.jiguang.shop.utils.OrderCodeFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;



@Slf4j
@RestController
@CrossOrigin //跨域
@RequestMapping("/pay")
public class PayTestController {

       @Autowired
    OrderService orderService;

       @Autowired
       OrderDescriptionService orderDescriptionService;


    @PostMapping( "alipaytest")
    public void alipaytest(HttpServletRequest httpRequest,
                       HttpServletResponse httpResponse) throws IOException {
        //构造sdk的客户端对象
httpRequest.setCharacterEncoding("UTF-8");
httpResponse.setContentType("text/html;charset=UTF-8");
//获取用户id
           String s=httpRequest.getParameter("uId");

           //生成订单编号
         String orderNumber= OrderCodeFactory.getOrderCode(s);
         String price=httpRequest.getParameter("totalPrice");
          Order order=new Order();
         order.setOrderNumber(orderNumber);


        System.out.println(price);
         BigDecimal bigDecimal= new BigDecimal(price);
         order.setActualPrice( bigDecimal);
         order.setMemberId(s);
      orderService.save(order);


         OrderDescription orderDescription=new OrderDescription();
         orderDescription.setOrderNumber(orderNumber);
         orderDescription.setProductId(Integer.parseInt(httpRequest.getParameter("productId")));
        String str = new String(httpRequest.getParameter("productName").getBytes("iso-8859-1"), "utf-8");
        orderDescription.setProductName(str);
         orderDescription.setProductNum(Integer.parseInt(httpRequest.getParameter("productNum")));
         orderDescription.setProductPicture(httpRequest.getParameter("productPicture"));
         orderDescriptionService.save(orderDescription);


        System.out.println(YmlParament.APP_Privatekey);

        AlipayClient alipayClient = new DefaultAlipayClient(YmlParament.ServerUrl, YmlParament.APP_ID,
                YmlParament.APP_Privatekey, YmlParament.Format, YmlParament.Charset,
                YmlParament.APP_Publickey, YmlParament.SignType); //获得初始化的AlipayClient



        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request



        alipayRequest.putOtherTextParam("out_trade_no",orderNumber);
        alipayRequest.putOtherTextParam("total_amount",price);
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

}
