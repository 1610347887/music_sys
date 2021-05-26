package com.huan.music_sys.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@ApiModel(value = "评论表实体类")
@Data
public class Comment {
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer consumerId;
    @ApiModelProperty(value = "评论类型")
    private Byte type;
    @ApiModelProperty(value = "歌手id")
    private Integer singerId;
    @ApiModelProperty(value = "歌单id")
    private Integer songListId;
    @ApiModelProperty(value = "评论内容")
    private String content;
    @ApiModelProperty(value = "评论时间")
    @TableField(fill = FieldFill.INSERT)         // 自动填充
    private Date createTime;
    @ApiModelProperty(value = "评论点赞数")
    private Integer up;
}
