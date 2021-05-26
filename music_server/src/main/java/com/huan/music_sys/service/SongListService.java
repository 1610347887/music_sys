package com.huan.music_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huan.music_sys.domain.SongList;

import java.util.List;


public interface SongListService extends IService<SongList> {
    // 根据歌单标题模糊查询
    List<SongList> selectSongListByT(String title);

    // 根据歌单风格查询
    List<SongList> selectSongListByS(String style);
}
