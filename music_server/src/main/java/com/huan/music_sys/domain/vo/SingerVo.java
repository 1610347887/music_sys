package com.huan.music_sys.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "歌手条件查询实体类")
@Data
public class SingerVo {
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "性别")
    private Byte sex;
}
