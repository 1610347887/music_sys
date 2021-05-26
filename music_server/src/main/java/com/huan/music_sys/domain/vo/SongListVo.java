package com.huan.music_sys.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "歌单条件查询实体类")
@Data
public class SongListVo {
    @ApiModelProperty(value = "歌单标题")
    private String title;
}
