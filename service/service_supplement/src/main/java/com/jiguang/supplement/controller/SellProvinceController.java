package com.jiguang.supplement.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiguang.supplement.entity.SellProvince;
import com.jiguang.supplement.service.SellProvinceService;
import com.jiguang.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/supplement/sellProvince")
public class SellProvinceController {

    @Autowired
    SellProvinceService sellProvinceServicel;

    @GetMapping("getAll")
    public R getAll(){
        QueryWrapper<SellProvince> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("product_count");
        List<SellProvince> list=sellProvinceServicel.list(wrapper);
        return R.ok().data("list",list);
    }


    @GetMapping("getTop")
    public R getTop5(){
        QueryWrapper<SellProvince> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("product_count");
        wrapper.last("limit 5"); //限制查询记录数为10
        List<SellProvince> list=sellProvinceServicel.list(wrapper);
        return R.ok().data("list",list);
    }

}

