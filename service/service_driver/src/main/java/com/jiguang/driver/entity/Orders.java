package com.jiguang.driver.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 账单
 * </p>
 *
 * @author testjava
 * @since 2023-04-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Orders对象", description="账单")
public class Orders implements Serializable {



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
