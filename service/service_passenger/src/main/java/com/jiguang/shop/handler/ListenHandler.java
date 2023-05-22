package com.jiguang.shop.handler;

import com.jiguang.shop.entity.ViewCount;
import com.jiguang.shop.service.ViewCountService;
import com.jiguang.shop.utils.RedisUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ListenHandler {

    @Autowired
    private RedisUtil redisUtil;

     @Autowired
     private ViewCountService viewCountService;


    @PostConstruct
    public void init() throws ParseException {



        System.out.println ("Redis及数据库开始初始化");
        //插入一条空数据

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=df.format(new Date());
        Date date=df.parse(str);


        ViewCount viewCount=new ViewCount();
        viewCount.setViewNum(Long.valueOf(0));
        viewCount.setViewTime(date);
        System.out.println(date);


       Integer viewId = viewCountService.add(viewCount);

        redisUtil.set("view_id", viewCount.getId());
        redisUtil.set("view_count", 0);

    }
}

