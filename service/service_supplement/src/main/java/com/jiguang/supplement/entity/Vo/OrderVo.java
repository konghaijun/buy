package com.jiguang.supplement.entity.Vo;


import com.baomidou.mybatisplus.annotation.*;
import com.jiguang.supplement.entity.OrderDescription;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_order")
@ApiModel(value="Order对象", description="订单表")
public class OrderVo {

    private static final long serialVersionUID = 1L;


    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty(value = "场站id")
    private String supplementNumber;

    private Member driver;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    private Date bookTime;

    private BigDecimal orderPrice;

    private String orderCondition;

    private Date gmtCreate;

    private Date time;

    private  Integer status;

    private List<OrderDescription> orderDescriptions;



}
