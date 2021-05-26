package com.huan.music_sys.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "歌曲表实体类")
@Data
public class Song {
    @ApiModelProperty(value = "id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "歌手id")
    private Integer singerId;
    @ApiModelProperty(value = "歌名")
    private String name;
    @ApiModelProperty(value = "简介")
    private String introduction;
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)           // 自动填充
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)    // 自动填充
    private Date updateTime;
    @ApiModelProperty(value = "歌曲图片")
    private String pic;
    @ApiModelProperty(value = "歌词")
    private String lyric;
    @ApiModelProperty(value = "歌曲地址")
    private String url;
}
