package com.jiguang.supplement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品销售表
 * </p>
 *
 * @author testjava
 * @since 2023-04-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ProductSell对象", description="商品销售表")
public class ProductSell implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String productName;

    private Integer productNumber;

    private Date gmtCreate;




}
