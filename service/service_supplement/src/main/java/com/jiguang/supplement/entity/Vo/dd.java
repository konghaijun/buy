package com.jiguang.supplement.entity.Vo;

import com.jiguang.supplement.entity.OrderDescription;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Auther: 23091
 * @Date: 2023/4/6 17:44
 * @Description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Member对象", description="用户会员表")
public class dd {
    private List<OrderDescription> list;
}
