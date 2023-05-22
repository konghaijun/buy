package com.jiguang.manage.entity.Vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;





@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MemberVo  {



    private String username;

    private String password;



}
