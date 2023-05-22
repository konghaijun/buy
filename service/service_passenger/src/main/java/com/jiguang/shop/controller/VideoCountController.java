package com.jiguang.shop.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.jiguang.commonutils.R;
import com.jiguang.shop.entity.VideoCount;
import com.jiguang.shop.service.VideoCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import java.util.*;

@RestController
@CrossOrigin //跨域
@RequestMapping("/shop/videoCount")
public class VideoCountController {

    @Autowired
    VideoCountService videoCountService;

    @GetMapping("getAll")
    public R getPlayTime() {
        int num = 0;
        List<VideoCount> videoCounts = videoCountService.list(null);
        for (VideoCount i : videoCounts) {
            num = num + i.getCount();
        }
        return R.ok().data("number", num);
    }



    //查今天的广告播放量
    @GetMapping("day")
    public R day() {

        SimpleDateFormat Format=new SimpleDateFormat("yyyy-MM-dd");
        //通过SimpleDateFormat对象的parse()方法将指定字符串转换为Date对象
        Calendar begin=Calendar.getInstance();


        QueryWrapper<VideoCount> wrapper=new QueryWrapper<>();
        wrapper.like("month",Format.format(begin.getTime()));



        begin.add(Calendar.DAY_OF_MONTH, -1);
        QueryWrapper<VideoCount> wrapper1=new QueryWrapper<>();
        wrapper1.like("month",Format.format(begin.getTime()));




        long num=0;
        List<VideoCount> viewCount=videoCountService.list(wrapper);
        for(VideoCount i:viewCount){
            num=num+i.getCount();
        }


        long num1=0;
        List<VideoCount> viewCount1=videoCountService.list(wrapper1);
        for(VideoCount i:viewCount1){
            num1=num1+i.getCount();
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
            map.put("compare", result+"%");
        }

        return R.ok().data("map",map);


    }





    public String tx(String s) {
        String s1 = s.substring(5, 7);
        System.out.println(s1);
        if (s1.equals("01")) {
            return "一月";
        }
        if (s1.equals("02")) {
            return "二月";
        }
        if (s1.equals("03")) {
            return "三月";
        }
        if (s1.equals("04")) {
            return "四月";
        }
        if (s1.equals("05")) {
            return "五月";
        }
        if (s1.equals("06")) {
            return "六月";
        }
        if (s1.equals("07")) {
            return "七月";
        }
        if (s1.equals("08")) {
            return "八月";
        }
        if (s1.equals("09")) {
            return "九月";
        }
        if (s1.equals("10")) {
            return "十月";
        }
        if (s1.equals("11")) {
            return "十一月";
        }
        if (s1.equals("12")) {
            return "十二月";
        }
        return "0";
    }






    @GetMapping("getP")
    public R getP() {



        List tx = new ArrayList();
        List cty = new ArrayList();
        List tty = new ArrayList();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        for (int i = 7; i >= 0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -i);
            Date date = calendar.getTime();
            String format = dateFormat.format(date);
            QueryWrapper<VideoCount> wrapper = new QueryWrapper<>();
            wrapper.like("month", format);
            List<VideoCount> videoCounts = videoCountService.list(wrapper);
            int num=0;
            BigDecimal time=new BigDecimal(0);
            for(VideoCount k:videoCounts){
                num=num+k.getCount();
                time=time.add(k.getTime());
            }
           tx.add(tx(format));
           cty.add(num);
            tty.add(time);

        }
        Map map=new HashMap<>();
        map.put("tx",tx);
        map.put("cty",cty);
        map.put("ttx",tty);
            return R.ok().data("map",map);



    }

}
