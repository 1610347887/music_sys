package com.huan.music_sys.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "歌曲条件查询实体类")
@Data
public class SongVo {
    @ApiModelProperty(value = "歌手id")
    private Integer singerId;
    @ApiModelProperty(value = "歌名")
    private String name;
}
