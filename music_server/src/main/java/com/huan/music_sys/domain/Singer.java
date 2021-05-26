package com.huan.music_sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@ApiModel(value = "歌手表实体类")
@Data
public class Singer {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "性别")
    private Byte sex;
    @ApiModelProperty(value = "头像")
    private String pic;
    @ApiModelProperty(value = "生日")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
    private Date birth;
    @ApiModelProperty(value = "地区")
    private String location;
    @ApiModelProperty(value = "简介")
    private String introduction;
}
