package com.huan.music_sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "歌单中的歌曲表实体类")
@Data
public class ListSong {
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "歌曲id")
    private Integer songId;
    @ApiModelProperty(value = "歌单id")
    private Integer songListId;
}
