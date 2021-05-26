package com.huan.music_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huan.music_sys.domain.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    // 查询某个歌曲下的所有评论
    List<Comment> selectListById(byte type,int id);
}
