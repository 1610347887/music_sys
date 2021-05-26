package com.huan.music_sys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huan.music_sys.domain.ListSong;
import com.huan.music_sys.domain.Song;
import com.huan.music_sys.domain.vo.ListSongAndSongVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ListSongMapper extends BaseMapper<ListSong> {
    // 嵌套查询，通过歌单id查询歌曲表中的name
    List<ListSongAndSongVo> getSongList(int songListId);

    // 嵌套查询，通过歌单id查询歌曲表中的歌曲信息
    List<Song> getSongListById(int songListId);
}
