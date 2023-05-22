package com.jiguang.driver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiguang.commonutils.R;
import com.jiguang.driver.entity.Lock;
import com.jiguang.driver.entity.Orders;
import com.jiguang.driver.service.LockService;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.swing.event.ListDataEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


@RestController
@RequestMapping("/driver/orders")
public class OrdersController {


    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LockService lockService;


    @GetMapping("getOrderM/{month}/{driver}")
        public R getOrderM(@PathVariable String month,@PathVariable  Long driver)
{


    String url="http://1.12.244.193:80/supplement/order/getMonthDriver/"+month+"/"+driver;
      R supplement=restTemplate.getForObject(url,R.class);


    QueryWrapper<Lock> wrapper=new QueryWrapper<>();
    wrapper.eq("driver_id",driver);
    Lock lock=lockService.getOne(wrapper);

    String url1="http://1.12.244.193:80/shop/order/getMonthDriver/"+month+"/"+lock.getMachineNumber();
    R passenger=restTemplate.getForObject(url1,R.class);


    ObjectMapper objectMapper = new ObjectMapper();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    objectMapper.setDateFormat(dateFormat);
    List<LinkedHashMap> supplementList = (List<LinkedHashMap>) supplement.getData().get("list");
    List<Orders> supplementOrdersList = new ArrayList<>();
    for (LinkedHashMap map : supplementList) {
        Orders orders = objectMapper.convertValue(map, Orders.class);
        supplementOrdersList.add(orders);
    }
    List<LinkedHashMap> passengerList = (List<LinkedHashMap>) passenger.getData().get("list");
    List<Orders> passengerOrdersList = new ArrayList<>();
    for (LinkedHashMap map : passengerList) {
        Orders orders = objectMapper.convertValue(map, Orders.class);
        passengerOrdersList.add(orders);
    }
    List<Orders> list = new ArrayList<>();
    list.addAll(supplementOrdersList);
    list.addAll(passengerOrdersList);
    Collections.sort(list, new Comparator<Orders>() {
        @Override
        public int compare(Orders o1, Orders o2) {
            return o2.getGmtCreate().compareTo(o1.getGmtCreate()); // 按照日期从大到小排序
        }
    });




    return R.ok().data("list",list);
}

    @GetMapping("getOrderP/{month}/{driver}")
    public R getOrderP(@PathVariable String month,@PathVariable  Long driver)
    {




            String url="http://1.12.244.193:80/supplement/order/getMonthDriver/"+month+"/"+driver;
            R supplement=restTemplate.getForObject(url,R.class);


            QueryWrapper<Lock> wrapper=new QueryWrapper<>();
            wrapper.eq("driver_id",driver);
            Lock lock=lockService.getOne(wrapper);

            String url1="http://1.12.244.193:80/shop/order/getMonthDriver/"+month+"/"+lock.getMachineNumber();
            R passenger=restTemplate.getForObject(url1,R.class);





            ObjectMapper objectMapper = new ObjectMapper();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            objectMapper.setDateFormat(dateFormat);
            List<LinkedHashMap> supplementList = (List<LinkedHashMap>) supplement.getData().get("list");
            List<Orders> supplementOrdersList = new ArrayList<>();
            for (LinkedHashMap map : supplementList) {
                Orders orders = objectMapper.convertValue(map, Orders.class);
                supplementOrdersList.add(orders);
            }
            List<LinkedHashMap> passengerList = (List<LinkedHashMap>) passenger.getData().get("list");
            List<Orders> passengerOrdersList = new ArrayList<>();
            for (LinkedHashMap map : passengerList) {
                Orders orders = objectMapper.convertValue(map, Orders.class);
                passengerOrdersList.add(orders);
            }
            List<Orders> list = new ArrayList<>();
            list.addAll(supplementOrdersList);
            list.addAll(passengerOrdersList);
            Collections.sort(list, new Comparator<Orders>() {
                @Override
                public int compare(Orders o1, Orders o2) {
                    return o2.getGmtCreate().compareTo(o1.getGmtCreate()); // 按照日期从大到小排序
                }
            });


            //1为收入0为支出
        List<BigDecimal> shouru=new ArrayList<>();
        List<BigDecimal> zhichu=new ArrayList<>();
        List<String> riqi=new ArrayList<>();

        shouru.add(new BigDecimal(50));
        shouru.add(new BigDecimal(76));
        shouru.add(new BigDecimal(66));
        shouru.add(new BigDecimal(55));

        zhichu.add(new BigDecimal(30));
        zhichu.add(new BigDecimal(50));
        zhichu.add(new BigDecimal(40));
        zhichu.add(new BigDecimal(45));

        riqi.add("4-01");
        riqi.add("4-02");
        riqi.add("4-03");
        riqi.add("4-04");

        Map mapShouru=new HashMap();
        Map mapZhichu=new HashMap();

        /*List<String>  riqi=new ArrayList<>();
            for(Orders i:list){
             if(i.getProductCondition()==0) {shouru.add(i.getOrderPrice());}
            }*/


        Map map=new HashMap();
        map.put("shouru",shouru);
        map.put("zhichu",zhichu);
        map.put("riqi",riqi);

        return R.ok().data("map",map);
    }


}

