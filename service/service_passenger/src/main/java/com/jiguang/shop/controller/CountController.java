package com.jiguang.shop.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.jiguang.commonutils.R;
import com.jiguang.shop.entity.Order;
import com.jiguang.shop.entity.ViewCount;
import com.jiguang.shop.service.OrderService;
import com.jiguang.shop.service.ViewCountService;
import com.jiguang.shop.utils.RedisUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@CrossOrigin //跨域
@RequestMapping("/shop/count")
public class CountController {


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private  ViewCountService viewCountService;



    @Autowired
    private OrderService orderService;

    @GetMapping("add")
    public R count()
    {
        redisUtil.incr("view_count",1);
        return R.ok();
    }




    @Scheduled(cron = "0 0 0/3 * * ?")
    public void viewDB() throws ParseException {



        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=df.format(new Date());
        Date date=df.parse(str);

        UpdateWrapper<ViewCount> wrapper1=new UpdateWrapper<>();
        wrapper1.eq("id",Integer.parseInt(redisUtil.get("view_id").toString()));


        if(viewCountService.count(wrapper1)>0) {
            ViewCount view = new ViewCount();
            view.setViewNum((Long) redisUtil.get("view_count"));
            view.setViewTime(date);
            viewCountService.update(view, wrapper1);
        }


        System.out.println("准备从redis写入mysql");


        ViewCount viewCount = new ViewCount();
        viewCount.setViewNum(Long.valueOf(0));
        viewCount.setViewTime(date);

        Integer viewId = viewCountService.add(viewCount);

        redisUtil.set("view_id", viewId);
        redisUtil.set("view_count", 0);


    }




    //读取每日访问量
    @GetMapping("getDay/{day}")
    public R getDay(@PathVariable String day) throws ParseException {

        SimpleDateFormat Format=new SimpleDateFormat("yyyy-MM-dd");
        //通过SimpleDateFormat对象的parse()方法将指定字符串转换为Date对象
        Date date=Format.parse(day);
        Calendar begin=Calendar.getInstance();

        //今天
        begin.setTime(date);
        QueryWrapper<ViewCount> wrapper=new QueryWrapper<>();
        wrapper.like("view_time",Format.format(begin.getTime()));



        begin.add(Calendar.DAY_OF_MONTH, -1);
        QueryWrapper<ViewCount> wrapper1=new QueryWrapper<>();
        wrapper1.like("view_time",Format.format(begin.getTime()));




        long num=0;
        List<ViewCount> viewCount=viewCountService.list(wrapper);
        for(ViewCount i:viewCount){
            num=num+i.getViewNum();
        }


        long num1=0;
        List<ViewCount> viewCount1=viewCountService.list(wrapper1);
        for(ViewCount i:viewCount1){
            num1=num1+i.getViewNum();
        }



        Map map =new HashMap();
        map.put("number",num);
        double result=0;

        if(num!=0){
            result=(num-num1)*100/num;
        }


        if(result>0){
            map.put("compare", "+"+result +"%");
        }
        else {
            map.put("compare", result +"%");
        }

        return R.ok().data("map",map);
    }




    //获取日访问量与成交量图
    @GetMapping("getDayP")
    public R getDayP(){


     //获取日访问量
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        Date yesterday = cal.getTime();
    //  System.out.println("Yesterday's date: " + yesterday);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String yest = sdf.format(yesterday);

        QueryWrapper<ViewCount> wrapper=new QueryWrapper<>();
        wrapper.like("view_time",yest);


         List<ViewCount> viewCounts=viewCountService.list(wrapper);

       List<String> ftx=new ArrayList();
       ftx.add("03:00");
       ftx.add("06:00");
       ftx.add("09:00");
       ftx.add("12:00");
       ftx.add("15:00");
       ftx.add("18:00");
       ftx.add("21:00");
       ftx.add("24:00");


       List<Long> fty=new ArrayList<>();

       for(ViewCount i:viewCounts){
           fty.add(i.getViewNum());
       }





       Map map=new HashMap<>();
       map.put("ftx",ftx);
       map.put("fty",fty);



       List list=new ArrayList();
       for(int i=0;i<24;i=i+3){
           QueryWrapper<Order> wrapper1=new QueryWrapper<>();
            if(i<9){
                String s=yest+" 0"+i;
                String s1=yest+" 0"+(i+1);
                String s2=yest+" 0"+(i+2);
                wrapper1.like("gmt_create",s).or().like("gmt_create",s1).or().like("gmt_create",s2);
            }
            else if(i==9){
               String s=yest+" 0"+i;
                String s1=yest+" "+(i+1);
                String s2=yest+" "+(i+2);
                wrapper1.like("gmt_create",s).or().like("gmt_create",s1).or().like("gmt_create",s2);
            }
            else {
                String s=yest+" "+i;
                String s1=yest+" "+(i+1);
                String s2=yest+" "+(i+2);
                wrapper1.like("gmt_create",s).or().like("gmt_create",s1).or().like("gmt_create",s2);
            }


            List<Order> orders=orderService.list(wrapper1);
             list.add(orders.size());
       }


       map.put("oty",list);


        return  R.ok().data("map",map);
    }

    //获取周访问量与成交量图
     @GetMapping("getMon")
    public  R getMon() throws  Exception{


         Calendar cal = Calendar.getInstance();
         cal.add(Calendar.DATE, -7);



         List fty=new ArrayList();
         List ftx=new ArrayList();
         List oty=new ArrayList();
         ftx.add("周日");
         ftx.add("周一");
         ftx.add("周二");
         ftx.add("周三");
         ftx.add("周四");
         ftx.add("周五");
         ftx.add("周六");

         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         for (int i = 1; i <= 7; i++) {
             cal.set(Calendar.DAY_OF_WEEK, i);
             Date dayOfWeek = cal.getTime();
             String formate = sdf.format(dayOfWeek);

             QueryWrapper<ViewCount>  wrapper=new QueryWrapper<>();
             wrapper.like("view_time",formate);


             QueryWrapper<Order> wrapper1=new QueryWrapper<>();
             wrapper1.like("gmt_create",formate);
             List<Order> orders=orderService.list(wrapper1);
             oty.add(orders.size());

             long num=0;
             List<ViewCount> list=viewCountService.list(wrapper);
             for(ViewCount d:list){
               num=num+d.getViewNum();
             }
             fty.add(num);
         }


         Map map=new HashMap();
         map.put("fty",fty);
         map.put("ftx",ftx);
         map.put("oty",oty);


         return R.ok().data("map",map);
     }

    //获取年访问量与成交量图
     @GetMapping("getYear")
    public R getYear(){

        Map map=new HashMap();
        List<String> tx=new ArrayList();
        List fty=new ArrayList();
        List oty=new ArrayList();

        tx.add("一月");
        tx.add("二月");
        tx.add("三月");
        tx.add("四月");
        tx.add("五月");
        tx.add("六月");
        tx.add("七月");
        tx.add("八月");
        tx.add("九月");
        tx.add("十月");
        tx.add("十一月");
        tx.add("十二月");
        map.put("ftx",tx);



       //  List<String> months = new ArrayList<>();
         LocalDate now = LocalDate.now().minusYears(1);
         for (int i = 1; i <= 12; i++) {
             long num=0;
            String s=now.withMonth(i).format(DateTimeFormatter.ofPattern("yyyy-MM"));

             QueryWrapper<ViewCount>  wrapper=new QueryWrapper<>();
             wrapper.like("view_time",s);

             List<ViewCount> list=viewCountService.list(wrapper);
             for(ViewCount d:list){
                 num=num+d.getViewNum();
             }
              fty.add(num);


             QueryWrapper<Order> wrapper1=new QueryWrapper<>();
             wrapper1.like("gmt_create",s);
             List<Order> orders=orderService.list(wrapper1);
             oty.add(orders.size());

         }

       map.put("fty",fty);
       map.put("oty",oty);




        return R.ok().data("map",map);
     }






}
