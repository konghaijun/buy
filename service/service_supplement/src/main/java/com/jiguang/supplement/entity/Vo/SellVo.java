package com.jiguang.supplement.entity.Vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jiguang.supplement.entity.SellSupple;
import com.jiguang.supplement.entity.SellSupplement;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Auther: 23091
 * @Date: 2023/4/12 16:30
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Order对象", description="订单表")
public class SellVo {

    private SellSupple sellSupple;
    private List<SellSupplement> sellSupplements;



}
