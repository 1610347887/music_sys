package com.huan.music_sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huan.music_sys.domain.Song;
import com.huan.music_sys.mapper.SongMapper;
import com.huan.music_sys.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongServiceImpl extends ServiceImpl<SongMapper, Song> implements SongService {

    // 根据歌手id查询歌曲
    @Override
    public List<Song> selectList(Integer id) {
        return baseMapper.selectList(id);
    }
    // 通过歌名模糊查询
    @Override
    public List<Song> getSongByNa(String name) {
        return baseMapper.getSongByN(name);
    }
}
