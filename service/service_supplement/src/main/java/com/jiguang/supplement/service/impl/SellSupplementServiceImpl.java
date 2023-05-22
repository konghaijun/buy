package com.jiguang.supplement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiguang.supplement.entity.SellSupple;
import com.jiguang.supplement.entity.SellSupplement;
import com.jiguang.supplement.mapper.SellSupplementMapper;
import com.jiguang.supplement.service.SellSupplementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SellSupplementServiceImpl extends ServiceImpl<SellSupplementMapper, SellSupplement> implements SellSupplementService {

    @Override
    public List<SellSupplement> selectPage(String supplementNumber) {

        // 构建查询条件
        QueryWrapper<SellSupplement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("product_count");
        queryWrapper.eq("supplement_number",supplementNumber);
// 指定查询结果的数量为10条
        queryWrapper.last("limit 3"); //限制查询记录数为10
        List<SellSupplement> entityList = baseMapper.selectList( queryWrapper);

        return entityList;
    }
}
