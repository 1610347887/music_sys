package com.huan.music_sys.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "收藏表实体类")
@Data
public class Collect {
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer consumerId;
    @ApiModelProperty(value = "收藏类型(0歌曲，1歌单)")
    private Byte type;
    @ApiModelProperty(value = "歌曲id")
    private Integer songId;
    @ApiModelProperty(value = "歌单id")
    private Integer songListId;
    @ApiModelProperty(value = "收藏时间")
    @TableField(fill = FieldFill.INSERT)             // 自动填充
    private Date createTime;
}
