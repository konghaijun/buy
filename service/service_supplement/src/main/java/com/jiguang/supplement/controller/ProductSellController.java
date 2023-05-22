package com.jiguang.supplement.controller;



import com.jiguang.commonutils.R;
import com.jiguang.supplement.service.ProductSellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin //跨域
@RequestMapping("/supplement/productSell")
public class ProductSellController {



    @Autowired
    ProductSellService productSellService;

       @GetMapping("getTop")
        public R getTop(){

           return R.ok().data("list",productSellService.pages());
}



}

