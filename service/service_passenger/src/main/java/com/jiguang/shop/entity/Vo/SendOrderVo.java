package com.jiguang.shop.entity.Vo;


import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SendOrderVo implements Serializable {


    private String nickname;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal orderPrice;

    @ApiModelProperty(value = "商品名字")
    private String productName;

    private Date gmtCreate;

    @ApiModelProperty(value = "商品数量")
    private Integer productNumber;

    @ApiModelProperty(value = "1为收入0为支出")
    private Integer productCondition;

    private String picture;


}
