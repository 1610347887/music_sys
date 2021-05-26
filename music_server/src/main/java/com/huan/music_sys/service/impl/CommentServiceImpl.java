package com.huan.music_sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huan.music_sys.domain.Comment;
import com.huan.music_sys.mapper.CommentMapper;
import com.huan.music_sys.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    // 查询某个歌曲下的所有评论
    @Override
    public List<Comment> selectListById(byte type,int id) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        if (type == 0){
            wrapper.eq("singer_id",id);
        }else {
            wrapper.eq("song_list_id",id);
        }
        return baseMapper.selectList(wrapper);
    }
}
