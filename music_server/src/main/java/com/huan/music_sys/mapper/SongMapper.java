package com.huan.music_sys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huan.music_sys.domain.Song;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SongMapper extends BaseMapper<Song> {
    List<Song> selectList(Integer id);
    // 通过歌名模糊查询
    List<Song> getSongByN(String name);
}
