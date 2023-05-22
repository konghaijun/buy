package com.jiguang.shop.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("shop_product")
@ApiModel(value="Product对象", description="商品")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品名字")
    private String productName;

    @ApiModelProperty(value = "描述")
    private String productDescription;

    @ApiModelProperty(value = "价格")
    private Double productPrice;

    @ApiModelProperty(value = "库存")
    private Integer productStock;

    @ApiModelProperty(value = "分类")
    private Integer productVariety;

    @ApiModelProperty(value = "图片")
    private String productPicture;

    @ApiModelProperty(value = "机器编号")
    private String machineNumber;


    private  Integer productStatus;

    private Integer productLock;


}
