
package com.huan.music_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huan.music_sys.domain.ListSong;
import com.huan.music_sys.domain.Song;
import com.huan.music_sys.domain.vo.ListSongAndSongVo;
import com.huan.music_sys.result.Result;
import com.huan.music_sys.service.ListSongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Api(value = "歌单的歌曲管理")
@RestController
@RequestMapping("/music_sys/listSong")
public class ListSongController {
    @Autowired
    private ListSongService listSongService;

    // 给歌单添加歌曲
    @ApiOperation(value = "给歌单添加歌曲")
    @GetMapping("/addListSong/{songId}/{songListId}")
    public Result addListSong(@PathVariable int songId,
                              @PathVariable int songListId){
        ListSong listSong = new ListSong();
        listSong.setSongId(songId);
        listSong.setSongListId(songListId);
        boolean flag = listSongService.save(listSong);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 根据歌单id查询歌曲名
    @ApiOperation(value = "根据歌单id查询歌曲名")
    @GetMapping("/getListSongById/{songListId}")
    public Result getListSongById(@PathVariable int songListId){
        QueryWrapper<ListSong> wrapper = new QueryWrapper<>();
        wrapper.eq("song_list_id",songListId);
        List<ListSong> listSong = listSongService.list(wrapper);
        ArrayList<Integer> list = new ArrayList<>();
        for (ListSong song : listSong) {
            list.add(song.getId());
        }
        List<ListSongAndSongVo> songList = listSongService.getSongList(songListId);
        int i = 0;
        for (ListSongAndSongVo listSongAndSongVo : songList) {
            listSongAndSongVo.setId(list.get(i++));
        }
        return Result.ok(songList);
    }

    // 根据歌单id查询歌曲信息
    @ApiOperation(value = "根据歌单id查询歌曲信息")
    @GetMapping("/getSongByListSongId/{songListId}")
    public Result getSongByListSongId(@PathVariable int songListId){
        List<Song> songList = listSongService.getSongListById(songListId);
        return Result.ok(songList);
    }

    // 删除歌单中的歌曲
    @ApiOperation(value = "删除歌单中的歌曲")
    @GetMapping("/deleteListSong/{id}")
    public Result deleteListSong(@PathVariable int id){
        boolean flag = listSongService.removeById(id);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }
}
