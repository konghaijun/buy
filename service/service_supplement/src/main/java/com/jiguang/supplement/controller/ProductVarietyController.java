package com.jiguang.supplement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiguang.commonutils.R;
import com.jiguang.supplement.entity.ProductVariety;
import com.jiguang.supplement.service.ProductVarietyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin //跨域
@RequestMapping("/supplement/variety")
public class ProductVarietyController {

    @Autowired
    ProductVarietyService productVarietyService;

    @GetMapping("select/{supplementNumber}")
    public R selectAll(@PathVariable String supplementNumber) {
        QueryWrapper<ProductVariety> wrapper = new QueryWrapper();
        System.out.println(supplementNumber);
        wrapper.eq("supplement_number",supplementNumber);
        List<ProductVariety> list = productVarietyService.list(wrapper);
        return R.ok().data("list",list);
    }






}

