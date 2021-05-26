package com.huan.music_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huan.music_sys.domain.Song;
import com.huan.music_sys.domain.vo.SongVo;
import com.huan.music_sys.result.Result;
import com.huan.music_sys.service.SongService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Api(value = "歌曲管理")
@RestController
@RequestMapping("/music_sys/song")
public class SongController {
    @Autowired
    private SongService songService;
    @Value("${web.upload-path}")
    private  String filePath;

    // 获取歌曲数量
    @ApiOperation(value = "获取歌曲数量")
    @GetMapping("/getSongCount")
    public Result getSongCount(){
        int count = songService.count();
        return Result.ok(count);
    }

    // 根据歌手id查询歌曲
    @ApiOperation(value = "根据歌手id查询歌曲")
    @GetMapping("/getSongBySingerId/{id}")
    public Result getSongBySingerId(@PathVariable Integer id){
        List<Song> songs = songService.selectList(id);
        return Result.ok(songs);
    }

    // 条件查询带分页（根据歌曲名模糊查询）
    @ApiOperation(value = "条件查询带分页（根据歌曲名模糊查询）")
    @PostMapping("/findSong/{current}/{limit}")
    public Result findSong(@PathVariable int current,
                           @PathVariable int limit,
                           @RequestBody(required = false) SongVo songVo){
        // 创建page对象，传递当前页和每页数量
        Page<Song> page = new Page<>(current,limit);
        // 构造条件
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.eq("singer_id", songVo.getSingerId());
        if (!StringUtils.isEmpty(songVo.getName())){
            wrapper.like("name",songVo.getName());
        }
        Page<Song> songPage = songService.page(page, wrapper);
        return Result.ok(songPage);
    }

    // 添加歌曲
    @ApiOperation(value = "添加歌曲")
    @PostMapping("/addSong")
    public Result addSong(@RequestBody Song song){
        boolean flag = songService.save(song);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 根据id查询歌曲
    @ApiOperation(value = "根据id查询歌曲")
    @GetMapping("/getSongById/{id}")
    public Result getSongById(@PathVariable int id){
        Song song = songService.getById(id);
        return Result.ok(song);
    }

    // 修改歌曲信息
    @ApiOperation(value = "修改歌曲信息")
    @PostMapping("/updateSong")
    public Result updateSong(@RequestBody Song song){
        boolean flag = songService.updateById(song);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 删除歌曲
    @ApiOperation(value = "删除歌曲")
    @GetMapping("/deleteSong/{id}")
    public Result deleteSong(@PathVariable int id){
        boolean flag = songService.removeById(id);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 通过歌名查询歌曲对象
    @ApiOperation(value = "通过歌名查询歌曲对象")
    @GetMapping("/getSongByName/{songName}")
    public Result getSongByName(@PathVariable String songName){
        QueryWrapper<Song> wrapper = new QueryWrapper<>();
        wrapper.eq("name",songName);
        Song song = songService.getOne(wrapper);
        return Result.ok(song);
    }

    // 通过歌名模糊查询
    @ApiOperation(value = "通过歌名模糊查询")
    @GetMapping("/getSongByN/{name}")
    public Result getSongByN(@PathVariable String name){
        List<Song> songList = songService.getSongByNa("%"+name+"%");
        return Result.ok(songList);
    }

    // 上传歌曲
    @ApiOperation(value = "上传歌曲")
    @PostMapping("/updateSong/{id}")
    public Result addSong(@RequestParam("file") MultipartFile file, @PathVariable int id){
        if (file.isEmpty()){
            return Result.fail();
        }
        // 文件名：当前时间（毫秒）+原来的文件名
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
//        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "song";

        String realPath = null;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")){
            try {
                realPath = ResourceUtils.getURL("classpath:").getPath() + "static/song/";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            realPath = filePath + "/song/";
        }

        File file1 = new File(realPath);
        if (!file1.exists()){
            file1.mkdir();
        }
        // 实际的文件地址
        File dest = new File(realPath + fileName);
        // 存储到数据库里（相对文件地址）
        String picPath = "/song/" + fileName;
        try {
            file.transferTo(dest);
            Song song = songService.getById(id);
            song.setUrl(picPath);
            boolean flag = songService.updateById(song);
            if (flag){
                return Result.ok();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

    // 更新歌曲图片
    @ApiOperation(value = "更新歌手图片")
    @PostMapping("/updateSongPic/{id}")
    public Result updateSingerPic(@RequestParam("file") MultipartFile file, @PathVariable Integer id){
        if (file.isEmpty()){
            return Result.fail();
        }
        // 文件名：当前时间（毫秒）+原来的文件名
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
//        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
//                + System.getProperty("file.separator") + "songPic";

        String realPath = null;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")){
            try {
                realPath = ResourceUtils.getURL("classpath:").getPath() + "static/img/songPic/";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            realPath = filePath + "/img/songPic/";
        }

        File file1 = new File(realPath);
        if (!file1.exists()){
            file1.mkdir();
        }
        // 实际的文件地址
        File dest = new File(realPath + fileName);
        // 存储到数据库里（相对文件地址）
        String picPath = "/img/songPic/" + fileName;

        try {
            file.transferTo(dest);
            Song song = songService.getById(id);
            song.setPic(picPath);
            boolean flag = songService.updateById(song);
            if (flag){
                return Result.ok();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
}
