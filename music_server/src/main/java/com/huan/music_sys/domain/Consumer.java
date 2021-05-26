package com.huan.music_sys.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "用户表实体类")
@Data
public class Consumer {
    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "性别")
    private Byte sex;
    @ApiModelProperty(value = "手机号")
    private String phoneNum;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "生日")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date birth;
    @ApiModelProperty(value = "签名")
    private String introduction;
    @ApiModelProperty(value = "地区")
    private String location;
    @ApiModelProperty(value = "头像")
    private String avator;
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)      // 自动填充
    private Date createTime;
    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)    // 自动填充
    private Date updateTime;
}
