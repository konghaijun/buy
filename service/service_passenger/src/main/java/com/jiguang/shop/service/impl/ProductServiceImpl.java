package com.jiguang.shop.service.impl;

import com.jiguang.shop.entity.Product;
import com.jiguang.shop.mapper.ProductMapper;
import com.jiguang.shop.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
