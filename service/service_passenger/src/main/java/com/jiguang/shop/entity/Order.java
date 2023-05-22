package com.jiguang.shop.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author testjava
 * @since 2023-01-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_order")
@ApiModel(value="Order对象", description="订单表")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private String memberId;

    @ApiModelProperty(value = "订单号")
    private String orderNumber;

    @ApiModelProperty(value = "订单总价")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "实际支付")
    private BigDecimal actualPrice;

    @ApiModelProperty(value = "优惠卷抵扣金额")
    private BigDecimal couponDeduction;

    @ApiModelProperty(value = "获得积分")
    private Integer orderIntegral;


    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;



    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModify;


    @ApiModelProperty(value = "评价状态")
    @TableField(fill = FieldFill.INSERT)
    private  Integer evaluationStatus;

    @ApiModelProperty(value = "支付状态")
    @TableField(fill = FieldFill.INSERT)
    private Integer payStatus;


    private  String machineNumber;




}
