package com.jiguang.shop.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiguang.commonutils.R;
import com.jiguang.shop.entity.Product;
import com.jiguang.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@CrossOrigin //跨域
@RequestMapping("/shop/product")
public class ProductController {


    @Autowired
    ProductService productService;

     //根据机器编号 和 锁号查询上架的商品
    @GetMapping("selectAll/{machineNumber}/{lock}")
    public R selectAll(@PathVariable String machineNumber , @PathVariable Integer lock) {
        QueryWrapper<Product> wrapper = new QueryWrapper();
        wrapper.eq("machine_number",machineNumber);
        wrapper.eq("product_status",1);
        wrapper.eq("product_lock",lock);
        List<Product> list =productService.list(wrapper);
        return R.ok().data("list",list);
    }



    //查分类商品  根据机器编号 和 锁号查询上架的商品
    @GetMapping("selectByV/{machineNumber}/{varietyId}/{lock}")
    public R selectAll(@PathVariable String machineNumber,@PathVariable Integer varietyId,@PathVariable Integer lock) {
        QueryWrapper<Product> wrapper = new QueryWrapper();
        System.out.println(machineNumber);
        wrapper.eq("machine_number",machineNumber);
        wrapper.eq("product_variety",varietyId);
        wrapper.eq("product_status",1);
        wrapper.eq("product_lock",lock);
        List<Product> list =productService.list(wrapper);
        return R.ok().data("list",list);
    }




    //模糊查询
    @RequestMapping("selectByN/{machineNumber}/{status}")
    public R selectByN(HttpServletRequest request , @PathVariable String  machineNumber
    ,@PathVariable Integer status) {
        String name=request.getParameter("name");//获取html页面搜索框的值
      //  String machineNumber=request.getParameter("machineNumber");
        QueryWrapper<Product> wrapper=new QueryWrapper<>();
        wrapper.like("product_name",name);
        wrapper.eq("machine_number",machineNumber);
        wrapper.eq("product_status",status);
        List<Product> list=productService.list(wrapper);	//在数据库中进行查询
        return R.ok().data("list",list);
    }




    //模糊查询
    @RequestMapping("selectByN/{machineNumber}")
    public R selectByNN(HttpServletRequest request , @PathVariable String  machineNumber
            ) {
        String name=request.getParameter("name");//获取html页面搜索框的值
        //  String machineNumber=request.getParameter("machineNumber");
        QueryWrapper<Product> wrapper=new QueryWrapper<>();
        wrapper.like("product_name",name);
        wrapper.eq("machine_number",machineNumber);
        List<Product> list=productService.list(wrapper);	//在数据库中进行查询
        return R.ok().data("list",list);
    }










    //根据商品id显示商品
    @GetMapping("getById/{id}")
    public R getById(@PathVariable Integer id)
    {
        return R.ok().data("product",productService.getById(id));
    }



    //添加商品
    @PostMapping("add")
    public R add(@RequestBody Product product)
    {
      boolean f=productService.save(product);
      if (f){
        return  R.ok().message("添加成功");} else {return R.error().message("添加失败");}}



    //修改商品
    @PostMapping("update")
            public R update(@RequestBody Product product)
    {
        UpdateWrapper<Product> wrapper=new UpdateWrapper<>();
        wrapper.eq("id",product.getId());
        boolean f=productService.update(product,wrapper);
        if(f) {return R.ok().message("修改成功");} else {return  R.error().message("修改失败");}
    }








    @GetMapping("selectAll/{machineNumber}")
    public R selectAll(@PathVariable String machineNumber){
        QueryWrapper<Product> wrapper=new QueryWrapper<>();
        wrapper.eq("machine_number",machineNumber);
        List<Product> list=productService.list(wrapper);
        return R.ok().data("list",list);
    }





    //查看上架商品
    @GetMapping("selectUp/{machineNumber}")
    public R selectUp(@PathVariable String machineNumber) {
        QueryWrapper<Product> wrapper = new QueryWrapper();
        wrapper.eq("machine_number",machineNumber);
        wrapper.eq("product_status",1);
        List<Product> list =productService.list(wrapper);
        return R.ok().data("list",list);
    }



    //查看下架架商品
    @GetMapping("selectDown/{machineNumber}")
    public R selectDown(@PathVariable String machineNumber) {
        QueryWrapper<Product> wrapper = new QueryWrapper();
        wrapper.eq("machine_number",machineNumber);
        wrapper.eq("product_status",0);
        List<Product> list =productService.list(wrapper);
        return R.ok().data("list",list);
    }





























}

