package com.jiguang.supplement.service;

import com.jiguang.supplement.entity.SellSupple;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiguang.supplement.entity.Vo.SellVo;

import java.util.List;


public interface SellSuppleService extends IService<SellSupple> {

    List<SellVo> selectPage();
}
