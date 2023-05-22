package com.jiguang.driver.controller;

import com.jiguang.commonutils.R;
import com.jiguang.driver.entity.DriverNumberMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/driver/NumberMap")
public class DriverNumberMapController {

    @Autowired
    MongoTemplate mongoTemplate;


    @GetMapping("get")
    public R get() {
        List<DriverNumberMap> list=mongoTemplate.findAll(DriverNumberMap.class);
        return R.ok().data("list",list);
    }
}
