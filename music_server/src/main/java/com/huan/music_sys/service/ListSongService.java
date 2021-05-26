package com.huan.music_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huan.music_sys.domain.ListSong;
import com.huan.music_sys.domain.Song;
import com.huan.music_sys.domain.vo.ListSongAndSongVo;

import java.util.List;


public interface ListSongService extends IService<ListSong> {
    // 嵌套查询，通过歌单id查询歌曲表中的name
    List<ListSongAndSongVo> getSongList(int id);

    // 嵌套查询，通过歌单id查询歌曲表中的歌曲信息
    List<Song> getSongListById(int songListId);
}
