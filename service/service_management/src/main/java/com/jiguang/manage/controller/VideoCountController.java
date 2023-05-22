package com.jiguang.manage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiguang.manage.entity.VideoCount;
import com.jiguang.manage.entity.Vo.pVo;
import com.jiguang.manage.service.VideoCountService;
import com.jiguang.manage.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin //跨域
@RequestMapping("/manage/videoCount")
public class VideoCountController {

    @Autowired
    VideoCountService videoCountService;


    //广告播放量 种类占比
    @GetMapping("guangGao/{driverId}")
    public  R GuangGao(@PathVariable Long driverId){
        System.out.println(driverId);
        List list=new ArrayList();

       list.add(new pVo("广告收入",1000));
       list.add(new pVo("销售收入",3000));
       list.add(new pVo("其他收入",500));



        return R.ok().data("map",list);
    }







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

        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String format = date.format(formatter);
        QueryWrapper<VideoCount> wrapper = new QueryWrapper<>();
        wrapper.like("month", format);
        List<VideoCount> list = videoCountService.list(wrapper);
        int num = 0;
        for (VideoCount i : list) {
            num = num + i.getCount();
        }
        return R.ok().data("num", num);


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
