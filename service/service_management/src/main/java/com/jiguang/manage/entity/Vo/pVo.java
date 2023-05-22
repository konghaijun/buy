package com.jiguang.manage.entity.Vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Auther: 23091
 * @Date: 2023/4/14 16:40
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class pVo {

    private  String name;
    private  Integer value;

    public pVo() {
    }

    public pVo(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
}
