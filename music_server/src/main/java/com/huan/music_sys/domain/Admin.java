package com.huan.music_sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 管理员
 * Serializable 序列化，便于数据传输，尤其是远程调用的时候
 * Serializable 是一个空接口，实现该接口是在告诉JVM该类可被序列化
 */
@ApiModel(value = "管理员实体类")
@Data
public class Admin implements Serializable {
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户名")
    private String name;
    @ApiModelProperty(value = "密码")
    private String password;
}
