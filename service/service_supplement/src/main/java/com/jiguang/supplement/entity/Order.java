package com.jiguang.supplement.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("supplement_order")
@ApiModel(value="Order对象", description="")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "场站id")
    private String supplementNumber;

    private Long driverId;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    private Date bookTime;

    private BigDecimal orderPrice;

    private String orderCondition;

    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    private Date time;

    private  Integer status;
    //0未处理 1接单 0拒绝接单


}
