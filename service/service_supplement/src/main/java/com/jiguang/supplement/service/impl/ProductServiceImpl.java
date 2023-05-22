package com.jiguang.supplement.service.impl;

import com.jiguang.supplement.entity.Product;
import com.jiguang.supplement.mapper.ProductMapper;
import com.jiguang.supplement.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
