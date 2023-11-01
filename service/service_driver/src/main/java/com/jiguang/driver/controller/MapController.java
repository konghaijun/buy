package com.jiguang.driver.controller;

import com.jiguang.commonutils.R;
import com.jiguang.driver.entity.DriverMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@CrossOrigin //跨域
@RequestMapping("/map")

public class MapController {

    @Autowired
    MongoTemplate mongoTemplate;


    @PostMapping("add")
    public R add(@RequestBody DriverMap driver)  {
        driver.setGmtCreate(new Date());
      mongoTemplate.insert(driver);
        return R.ok();
    }



    @GetMapping("send")
    public Map<String,Integer> send(){
        Map<String,Integer> map=new HashMap();
        map.put("lock",1);
        return map;
    }



}
