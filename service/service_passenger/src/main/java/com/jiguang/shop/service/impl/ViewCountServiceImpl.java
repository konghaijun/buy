package com.jiguang.shop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiguang.shop.entity.ViewCount;
import com.jiguang.shop.mapper.ViewCountMapper;
import com.jiguang.shop.service.ViewCountService;
import org.springframework.stereotype.Service;


@Service
public class ViewCountServiceImpl extends ServiceImpl<ViewCountMapper, ViewCount> implements ViewCountService {




    @Override
    public Integer add(ViewCount viewCount) {
        Integer i=this.baseMapper.insert(viewCount);
        return i;
    }
}
