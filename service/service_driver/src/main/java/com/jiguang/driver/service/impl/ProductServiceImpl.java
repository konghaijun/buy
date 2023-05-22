package com.jiguang.driver.service.impl;

import com.jiguang.driver.entity.Product;
import com.jiguang.driver.mapper.ProductMapper;
import com.jiguang.driver.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-01-21
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
