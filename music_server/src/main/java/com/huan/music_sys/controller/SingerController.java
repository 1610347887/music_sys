package com.huan.music_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huan.music_sys.domain.Singer;
import com.huan.music_sys.domain.vo.SingerVo;
import com.huan.music_sys.result.Result;
import com.huan.music_sys.service.SingerService;
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

@Api(value = "歌手管理")
@RestController
@RequestMapping("music_sys/singer")
public class SingerController {
    @Autowired
    private SingerService singerService;
    @Value("${web.upload-path}")
    private  String filePath;

    // 添加歌手
    @PostMapping("/add")
    @ApiOperation(value = "添加歌手")
    public Result addSinger(@RequestBody(required = false) Singer singer){
        boolean flag = singerService.save(singer);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 通过id获取歌手
    @ApiOperation(value = "通过id获取歌手")
    @GetMapping("/getSingerById/{id}")
    public Result getSingerById(@PathVariable Integer id){
        Singer singer = singerService.getById(id);
        return Result.ok(singer);
    }

    // 修改歌手信息
    @ApiOperation(value = "修改歌手信息")
    @PostMapping("/updateSinger")
    public Result updateSinger(@RequestBody Singer singer){
        boolean flag = singerService.updateById(singer);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 删除歌手
    @ApiOperation(value = "删除歌手")
    @GetMapping("/deleteSinger/{id}")
    public Result deleteSinger(@PathVariable Integer id){
        boolean flag = singerService.removeById(id);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 查询所有歌手
    @ApiOperation(value = "查询所有歌手")
    @GetMapping("/getAllSinger")
    public Result getAllSinger(){
        List<Singer> singerList = singerService.list();
        return Result.ok(singerList);
    }

    // 根据歌手性别查询歌手
    @ApiOperation(value = "根据歌手性别查询歌手")
    @GetMapping("/getSingerBySex/{sex}")
    public Result getSingerBySex(@PathVariable byte sex){
        List<Singer> singerList = singerService.getSingerBySex(sex);
        return Result.ok(singerList);
    }

    // 根据歌手名字模糊查询歌手
    @ApiOperation(value = "根据歌手名字模糊查询歌手")
    @GetMapping("/getSingerByName/{name}")
    public Result getSingerBySex(@PathVariable String name){
        List<Singer> singerList = singerService.getSingerByName(name);
        return Result.ok(singerList);
    }

    // 条件查询带分页(根据歌手名字或性别查询）
    @ApiOperation(value = "条件查询带分页（根据歌手姓名或性别查询）")
    @PostMapping("findSinger/{current}/{limit}")
    public Result findSinger(@PathVariable int current,
                             @PathVariable int limit,
                             @RequestBody(required = false) SingerVo singerVo){
        // 创建page对象，传递当前页和每页数量
        Page<Singer> page = new Page<>(current,limit);
        // 构造条件
        QueryWrapper<Singer> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(singerVo.getName())){
            wrapper.like("name",singerVo.getName());
        }
        if (!StringUtils.isEmpty(singerVo.getSex())){
            wrapper.eq("sex",singerVo.getSex());
        }
        Page<Singer> singerPage = singerService.page(page, wrapper);
        return Result.ok(singerPage);
    }

    // 更新歌手图片
    @ApiOperation(value = "更新歌手图片")
    @PostMapping("/updateSingerPic/{id}")
    public Result updateSingerPic(@RequestParam("file") MultipartFile file, @PathVariable Integer id){
        if (file.isEmpty()){
            return Result.fail();
        }
        // 文件名：当前时间（毫秒）+原来的文件名
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
//        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
//                + System.getProperty("file.separator") + "singerPic";

        String realPath = null;
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")){
            try {
                realPath = ResourceUtils.getURL("classpath:").getPath() + "static/img/singerPic/";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            realPath = filePath + "/img/singerPic/";
        }

        File file1 = new File(realPath);
        if (!file1.exists()){
            file1.mkdir();
        }
        // 实际的文件地址
        File dest = new File(realPath + fileName);
        // 存储到数据库里（相对文件地址）
        String picPath = "/img/singerPic/" + fileName;

        try {
            file.transferTo(dest);
            Singer singer = singerService.getById(id);
            singer.setPic(picPath);
            boolean flag = singerService.updateById(singer);
            if (flag){
                return Result.ok();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }
}
