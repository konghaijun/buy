package com.jiguang.shop.entity.Vo;


import com.jiguang.shop.entity.OrderDescription;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class AddOrder {


    @ApiModelProperty(value = "用户id")
    private String memberId;


    @ApiModelProperty(value = "订单总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "实际支付")
    private BigDecimal actualPrice;

    @ApiModelProperty(value = "优惠卷抵扣金额")
    private BigDecimal couponDeduction;

    private OrderDescription orderDescriptions;



}
