package com.huan.music_sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huan.music_sys.domain.Singer;
import com.huan.music_sys.mapper.SingerMapper;
import com.huan.music_sys.service.SingerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerServiceImpl extends ServiceImpl<SingerMapper, Singer> implements SingerService {
    // 根据歌手性别查询歌手
    @Override
    public List<Singer> getSingerBySex(byte sex) {
        QueryWrapper<Singer> wrapper = new QueryWrapper<>();
        wrapper.eq("sex",sex);
        return baseMapper.selectList(wrapper);
    }
    //根据歌手名字模糊查询歌手
    @Override
    public List<Singer> getSingerByName(String name) {
        QueryWrapper<Singer> wrapper = new QueryWrapper<>();
        wrapper.like("name",name);
        return baseMapper.selectList(wrapper);
    }
}
