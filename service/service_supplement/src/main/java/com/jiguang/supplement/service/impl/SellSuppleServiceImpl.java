package com.jiguang.supplement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiguang.supplement.entity.SellSupple;
import com.jiguang.supplement.entity.Vo.SellVo;
import com.jiguang.supplement.mapper.SellSuppleMapper;
import com.jiguang.supplement.service.SellSuppleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiguang.supplement.service.SellSupplementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class SellSuppleServiceImpl extends ServiceImpl<SellSuppleMapper, SellSupple> implements SellSuppleService {

    @Autowired
    SellSupplementService sellSupplementService;

    @Override
    public List<SellVo> selectPage() {


        // 构建查询条件
        QueryWrapper<SellSupple> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("supplement_count");
// 指定查询结果的数量为10条
        queryWrapper.last("limit 10"); //限制查询记录数为10
        List<SellSupple> entityList = baseMapper.selectList(queryWrapper);

        List<SellVo> list=new ArrayList<>();
        for(SellSupple i:entityList){
            SellVo sellVo=new SellVo();
            sellVo.setSellSupple(i);
            sellVo.setSellSupplements(sellSupplementService.selectPage(i.getSupplementNumber()));
            list.add(sellVo);
        }

        return list;
    }
}
