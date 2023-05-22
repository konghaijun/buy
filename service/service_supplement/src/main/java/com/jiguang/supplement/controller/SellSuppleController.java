package com.jiguang.supplement.controller;


import com.jiguang.commonutils.R;
import com.jiguang.supplement.entity.Vo.SellVo;

import com.jiguang.supplement.service.SellSuppleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/supplement/sellSupple")
public class SellSuppleController {

    @Autowired
    SellSuppleService sellSuppleService;


    //场站排行榜
    @GetMapping("getTen")
    public R getTen(){



    // 执行查询操作
        List<SellVo> entityList = sellSuppleService.selectPage();


        return R.ok().data("list",entityList);
    }


}

