package com.jiguang.manage.entity.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Auther: 23091
 * @Date: 2023/4/4 15:46
 * @Description:
 */



@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class loginVo {


    private String mobile;

    private String code;



}
