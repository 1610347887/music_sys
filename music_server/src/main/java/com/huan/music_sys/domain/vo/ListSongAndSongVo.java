package com.huan.music_sys.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "用于嵌套查询的实体类")
@Data
public class ListSongAndSongVo implements Serializable {
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;            // list_song 表的主键

    @ApiModelProperty(value = "歌名")
    private String name;           // song 表的歌名
}
