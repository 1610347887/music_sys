package com.huan.music_sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "歌单表实体类")
@Data
public class SongList {
    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "歌单标题")
    private String title;
    @ApiModelProperty(value = "歌单图片")
    private String pic;
    @ApiModelProperty(value = "简介")
    private String introduction;
    @ApiModelProperty(value = "风格")
    private String style;
}
