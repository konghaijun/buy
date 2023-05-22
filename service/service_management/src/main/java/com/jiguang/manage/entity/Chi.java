package com.jiguang.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Chi对象", description="")
public class Chi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.ID_WORKER_STR)
    private Integer Id;

    @TableField("Name")
    private String Name;

    @TableField("Pid")
    private Integer Pid;


}
