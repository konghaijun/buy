package com.jiguang.supplement.service;

import com.jiguang.supplement.entity.ProductSell;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品销售表 服务类
 * </p>
 *
 * @author testjava
 * @since 2023-04-12
 */
public interface ProductSellService extends IService<ProductSell> {



    List<ProductSell> pages();
}
