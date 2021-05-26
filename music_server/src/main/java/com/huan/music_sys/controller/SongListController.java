package com.huan.music_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huan.music_sys.domain.SongList;
import com.huan.music_sys.domain.vo.SongListVo;
import com.huan.music_sys.result.Result;
import com.huan.music_sys.service.SongListService;
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

@Api(value = "歌单管理")
@RestController
@RequestMapping("/music_sys/songList")
public class SongListController {
    @Autowired
    private SongListService songListService;
    @Value("${web.upload-path}")
    private  String filePath;

    // 获取所有歌单
    @ApiOperation(value = "获取所有歌单")
    @GetMapping("/getAllSongList")
    public Result getAllSongList(){
        List<SongList> songLists = songListService.list();
        return Result.ok(songLists);
    }

    // 添加歌单
    @ApiOperation(value = "添加歌单")
    @PostMapping("/addSongList")
    public Result addSongList(@RequestBody(required = false) SongList songList){
        boolean flag = songListService.save(songList);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 通过id获取歌单
    @ApiOperation(value = "通过id获取歌单")
    @GetMapping("/getSongListById/{id}")
    public Result getSongListById(@PathVariable Integer id){
        SongList songList = songListService.getById(id);
        return Result.ok(songList);
    }

    // 修改歌单信息
    @ApiOperation(value = "修改歌单信息")
    @PostMapping("/updateSongList")
    public Result updateSongList(@RequestBody SongList songList){
        boolean flag = songListService.updateById(songList);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 删除歌单
    @ApiOperation(value = "删除歌单")
    @GetMapping("/deleteSongList/{id}")
    public Result deleteSongList(@PathVariable Integer id){
        boolean flag = songListService.removeById(id);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 条件查询带分页(根据歌单标题模糊查询）
    @ApiOperation(value = "条件查询带分页（根据歌单标题模糊查询）")
    @PostMapping("findSongList/{current}/{limit}")
    public Result findSongList(@PathVariable int current,
                             @PathVariable int limit,
                             @RequestBody(required = false) SongListVo songListVo){
        // 创建page对象，传递当前页和每页数量
        Page<SongList> page = new Page<>(current,limit);
        // 构造条件
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(songListVo.getTitle())){
            wrapper.like("title",songListVo.getTitle());
        }
        Page<SongList> singerPage = songListService.page(page, wrapper);
        return Result.ok(singerPage);
    }

    // 根据歌单标题模糊查询
    @ApiOperation(value = "根据歌单标题模糊查询")
    @GetMapping("/findSongListByT/{title}")
    public Result findSongListByT(@PathVariable String title){
        List<SongList> songLists = songListService.selectSongListByT(title);
        return Result.ok(songLists);
    }

    // 根据歌单风格查询
    @ApiOperation(value = "根据歌单标题模糊查询")
    @GetMapping("/findSongListByS/{style}")
    public Result findSongListByS(@PathVariable String style){
        List<SongList> songLists = songListService.selectSongListByS(style);
        return Result.ok(songLists);
    }

    // 更新歌单图片
    @ApiOperation(value = "更新歌单图片")
    @PostMapping("/updateSongListPic/{id}")
    public Result updateSongListPic(@RequestParam("file") MultipartFile file, @PathVariable Integer id){
        if (file.isEmpty()){
            return Result.fail();
        }
        // 文件名：当前时间（毫秒）+原来的文件名
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
//        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
//                + System.getProperty("file.separator") + "songListPic";

        String realPath = null;
        String os = System.getProperty("os.name");     // 获取系统名称
        if (os.toLowerCase().startsWith("win")){       // win系统
            try {
                realPath = ResourceUtils.getURL("classpath:").getPath() + "static/img/songListPic/";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {                                        // Linux系统
            realPath = filePath + "/img/songListPic/";
        }

        File file1 = new File(realPath);
        if (!file1.exists()){
            file1.mkdir();
        }
        // 实际的文件地址
        File dest = new File(realPath + fileName);
        // 存储到数据库里（相对文件地址）
        String picPath = "/img/songListPic/" + fileName;

        try {
            file.transferTo(dest);
            SongList songList = songListService.getById(id);
            songList.setPic(picPath);
            boolean flag = songListService.updateById(songList);
            if (flag){
                return Result.ok();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
}
