package com.jiguang.shop.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiguang.commonutils.R;
import com.jiguang.shop.entity.Variety;
import com.jiguang.shop.service.VarietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin //跨域
@RequestMapping("/shop/variety")
public class VarietyController {
    @Autowired
    VarietyService varietyService;


    @GetMapping("select/{machineNumber}")
    public R selectAll(@PathVariable String machineNumber) {
        QueryWrapper<Variety> wrapper = new QueryWrapper();
        System.out.println(machineNumber);
        wrapper.eq("machine_number",machineNumber);
        List<Variety> list = varietyService.list(wrapper);
        return R.ok().data("list",list);
    }
}

