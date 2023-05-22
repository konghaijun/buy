package com.jiguang.shop.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiguang.shop.entity.ViewCount;


public interface ViewCountService extends IService<ViewCount> {


    Integer add(ViewCount viewCount);
}
