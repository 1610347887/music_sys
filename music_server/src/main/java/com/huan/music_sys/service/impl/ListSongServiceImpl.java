package com.huan.music_sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huan.music_sys.domain.ListSong;
import com.huan.music_sys.domain.Song;
import com.huan.music_sys.domain.vo.ListSongAndSongVo;
import com.huan.music_sys.mapper.ListSongMapper;
import com.huan.music_sys.service.ListSongService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ListSongServiceImpl extends ServiceImpl<ListSongMapper, ListSong> implements ListSongService {

    // 嵌套查询，通过歌单id查询歌曲表中的name
    @Override
    public List<ListSongAndSongVo> getSongList(int id) {
        return baseMapper.getSongList(id);
    }

    // 嵌套查询，通过歌单id查询歌曲表中的歌曲信息
    @Override
    public List<Song> getSongListById(int songListId) {
        return baseMapper.getSongListById(songListId);
    }
}
