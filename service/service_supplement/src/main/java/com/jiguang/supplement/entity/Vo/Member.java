package com.jiguang.supplement.entity.Vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;


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
    private String mailbox;

/*    @ApiModelProperty(value = "注册时间")
    private Date gmtCreate;*/

    @ApiModelProperty(value = "用户积分")
    private Integer userIntegral;

    @ApiModelProperty(value = "头像")
    private String avatar;


}
