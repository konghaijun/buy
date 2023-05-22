package com.jiguang.driver.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiguang.commonutils.R;
import com.jiguang.driver.entity.Product;
import com.jiguang.driver.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-01-21
 */
@RestController
@CrossOrigin //跨域
@RequestMapping("/driver/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("selectAll/{machineNumber}")
    public R selectAll(@PathVariable String machineNumber){
    QueryWrapper<Product> wrapper=new QueryWrapper<>();
    wrapper.eq("machine_number",machineNumber);
    List<Product> list=productService.list(wrapper);
    return R.ok().data("list",list);
}



    //模糊查询
    @RequestMapping("selectByN/{machineNumber}")
    public R selectByN(HttpServletRequest request , @PathVariable String  machineNumber) {
        String name=request.getParameter("name");//获取html页面搜索框的值
        //  String machineNumber=request.getParameter("machineNumber");
        QueryWrapper<Product> wrapper=new QueryWrapper<>();
        wrapper.like("product_name",name);
        wrapper.eq("machine_number",machineNumber);
        wrapper.eq("product_status",1);
        List<Product> list=productService.list(wrapper);	//在数据库中进行查询
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


    //根据锁号来显示上架商品
    @GetMapping("selectLock/{machineNumber}/{lock}")
    public R selectLock(@PathVariable String machineNumber,@PathVariable Integer lock) {
        QueryWrapper<Product> wrapper = new QueryWrapper();
        wrapper.eq("machine_number",machineNumber);
        wrapper.eq("product_status",1);
        wrapper.eq("product_lock",lock);
        List<Product> list =productService.list(wrapper);
        return R.ok().data("list",list);
    }


    //修改商品
    @PostMapping("update")
    public R update(@RequestBody Product product)
    {
    UpdateWrapper<Product> wrapper=new UpdateWrapper<>();
    wrapper.eq("id",product.getId());
    boolean f=productService.update(product,wrapper);
        if(f) {return R.ok().message("修改成功");} else {return  R.error().message("修改失败");}
    }






}

