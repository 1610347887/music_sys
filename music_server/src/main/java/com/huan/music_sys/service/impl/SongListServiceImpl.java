package com.huan.music_sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huan.music_sys.domain.SongList;
import com.huan.music_sys.mapper.SongListMapper;
import com.huan.music_sys.service.SongListService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SongListServiceImpl extends ServiceImpl<SongListMapper, SongList> implements SongListService {

    // 格局歌单标题模糊查询
    @Override
    public List<SongList> selectSongListByT(String title) {
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.like("title",title);
        return baseMapper.selectList(wrapper);
    }

    // 根据歌单风格查询
    @Override
    public List<SongList> selectSongListByS(String style) {
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.eq("style",style);
        return baseMapper.selectList(wrapper);
    }
}
