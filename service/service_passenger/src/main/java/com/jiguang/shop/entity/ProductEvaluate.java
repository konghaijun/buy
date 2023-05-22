package com.jiguang.shop.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="ProductEvaluate对象", description="商品评价表")
public class ProductEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "评论时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "评论星级")
    private Integer star;

    @ApiModelProperty(value = "点赞数量")
    private Integer praiseNumber;

    @ApiModelProperty(value = "回复数量")
    private Integer replyNumber;

    @ApiModelProperty(value = "评论类型")
    private Integer kind;

    @ApiModelProperty(value = "评论图片")
    private String picture;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    private String memberId;

    @ApiModelProperty(value = "订单id")
    private String orderNumber;


}
