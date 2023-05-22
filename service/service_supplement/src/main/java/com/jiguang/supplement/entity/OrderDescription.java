package com.jiguang.supplement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 订单项信息
 * </p>
 *
 * @author testjava
 * @since 2023-02-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="OrderDescription对象", description="订单项信息")
public class OrderDescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单id")
    private String orderNumber;

    @ApiModelProperty(value = "商品id")
    private Integer productId;

    @ApiModelProperty(value = "商品名字")
    private String productName;

    @ApiModelProperty(value = "商品图片")
    private String productPicture;

    @ApiModelProperty(value = "商品购买数量")
    private Integer productNum;

    private  Double productPrice;

}
