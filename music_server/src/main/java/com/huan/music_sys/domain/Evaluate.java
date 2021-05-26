package com.huan.music_sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@ApiModel(value = "评价表实体类")
@Data
public class Evaluate implements Serializable {
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "歌单id")
    private int songListId;
    @ApiModelProperty(value = "用户id")
    private int consumerId;
    @ApiModelProperty(value = "评分")
    private int score;

}
