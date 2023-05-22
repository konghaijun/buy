package com.jiguang.supplement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiguang.supplement.entity.ProductSell;
import com.jiguang.supplement.mapper.ProductSellMapper;
import com.jiguang.supplement.service.ProductSellService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品销售表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-04-12
 */
@Service
public class ProductSellServiceImpl extends ServiceImpl<ProductSellMapper, ProductSell> implements ProductSellService {

    @Override
    public List<ProductSell> pages() {
        // 构建查询条件
        QueryWrapper<ProductSell> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("product_number");
        queryWrapper.last("limit 10"); //限制查询记录数为10
// 指定查询结果的数量为10条


// 执行查询操作
        List<ProductSell> entityList = baseMapper.selectList( queryWrapper);
        return entityList;
    }
}
