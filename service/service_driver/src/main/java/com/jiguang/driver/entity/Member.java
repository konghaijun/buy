package com.jiguang.driver.entity;

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
@ApiModel(value="Member对象", description="用户会员表")
public class Member implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "会员等级")
    private Long gradeId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String userPassword;

    @ApiModelProperty(value = "昵称")
    private String userNickname;

    @ApiModelProperty(value = "手机号")
    private String mobilePhone;

    @ApiModelProperty(value = "邮箱")
    private String machineNumber;

    @ApiModelProperty(value = "注册时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    @ApiModelProperty(value = "用户积分")
    private Integer userIntegral;

    @ApiModelProperty(value = "头像")
    private String avatar;


    @ApiModelProperty(value = "用户是否被禁用")
    private Integer status;


}
