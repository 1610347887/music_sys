package com.huan.music_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huan.music_sys.domain.Song;

import java.util.List;

public interface SongService extends IService<Song> {
    List<Song> selectList(Integer id);
    // 通过歌名模糊查询
    List<Song> getSongByNa(String name);
}
