package com.jiguang.supplement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jiguang.commonutils.R;
import com.jiguang.supplement.entity.Product;
import com.jiguang.supplement.service.FileService;
import com.jiguang.supplement.service.ProductService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin //跨域

@RequestMapping("/supplement/product")
public class ProductController {


    @Autowired(required=false)
    private FileService fileService;




    @Autowired
    ProductService productService;


//修改商品
     @PostMapping("update")
    public R update(@RequestBody Product product){
         UpdateWrapper<Product> wrapper=new UpdateWrapper<>();
         wrapper.eq("id",product.getId());
      boolean f=   productService.update(product,wrapper);

if(f) {
    return R.ok().message("修改成功");
}else {
    return R.error().message("修改失败");
}
    }





    //查全部商品
    @GetMapping("selectAll/{supplementNumber}")
    public R selectAll(@PathVariable String supplementNumber) {
        QueryWrapper<Product> wrapper = new QueryWrapper();
        System.out.println(supplementNumber);
        wrapper.eq("supplement_number",supplementNumber);
        List<Product> list =productService.list(wrapper);
        return R.ok().data("list",list);
    }


    //查分类商品
    @GetMapping("selectByV/{supplementNumber}/{varietyId}")
    public R selectAll(@PathVariable String supplementNumber,@PathVariable Integer varietyId) {
        QueryWrapper<Product> wrapper = new QueryWrapper();
        System.out.println(supplementNumber);
        wrapper.eq("supplement_number",supplementNumber);
        wrapper.eq("product_variety",varietyId);
        List<Product> list =productService.list(wrapper);
        return R.ok().data("list",list);
    }

    //模糊查询
    @RequestMapping("selectByN/{supplementNumber}")
    public R selectByN(HttpServletRequest request , @PathVariable String  supplementNumber) {
        String name=request.getParameter("name");//获取html页面搜索框的值
        //  String machineNumber=request.getParameter("machineNumber");
        QueryWrapper<Product> wrapper=new QueryWrapper<>();
        wrapper.like("product_name",name);
        wrapper.eq("supplement_number",supplementNumber);
        List<Product> list=productService.list(wrapper);	//在数据库中进行查询
        return R.ok().data("list",list);
    }


    //
    @PostMapping("add")
    public  R add( @RequestParam("file") MultipartFile file,
                   @RequestParam("productName") String productName,
                   @RequestParam("productPrice") Double productPrice,
                   @RequestParam("productStock") Integer productStock,
                   @RequestParam("productVariety")Integer productVariety,
                   @RequestParam("supplementNumber")String supplementNumber
    ){
        String uploadUrl = fileService.upload(file);
        Product product=new Product();
        product.setProductName(productName);
        product.setProductPrice(productPrice);
        product.setProductStock(productStock);
         product.setProductVariety(productVariety);
        product.setSupplementNumber(supplementNumber);
       product.setProductPicture(uploadUrl);
        productService.save(product);
        return  R.ok();
    }



//
    @GetMapping("selectById/{id}")
    public R selectById(@PathVariable Long id)
    {
        QueryWrapper<Product> wrapper=new QueryWrapper<>();
        wrapper.eq("id",id);
        Product product=productService.getOne(wrapper);
        return  R.ok().data("product",product);
    }






}

