package com.jiguang.supplement.service;

import com.jiguang.supplement.entity.SellSupplement;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface SellSupplementService extends IService<SellSupplement> {


    List<SellSupplement> selectPage(String supplementNumber);
}
