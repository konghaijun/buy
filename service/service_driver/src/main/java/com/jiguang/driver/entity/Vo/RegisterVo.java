package com.jiguang.driver.entity.Vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterVo {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "密码")
    private String userPassword;

   private  String  userNickname;



}
