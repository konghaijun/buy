package com.jiguang.supplement.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
@ApiModel(value="SellSupple对象", description="售货站前几排行榜")
public class SellSupple implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "售货站编号")
    private String supplementNumber;

    @ApiModelProperty(value = "售货站名字")
    private String supplementName;

    @ApiModelProperty(value = "总收益")
    private Double supplementCount;

    @ApiModelProperty(value = "统计时间")
    private Date gmtCreate;


}
