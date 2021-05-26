package com.huan.music_sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huan.music_sys.domain.Collect;
import com.huan.music_sys.domain.Comment;
import com.huan.music_sys.result.Result;
import com.huan.music_sys.service.CollectService;
import com.huan.music_sys.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户收藏管理")
@RestController
@RequestMapping("music_sys/collect")
public class CollectController {
    @Autowired
    private CollectService collectService;

    // 添加收藏
    @ApiOperation(value = "添加收藏")
    @PostMapping("/addCollect")
    public Result addCollect(@RequestBody Collect collect){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("consumer_id",collect.getConsumerId());
        wrapper.eq("song_id",collect.getSongId());
        Collect one = collectService.getOne(wrapper);
        if (one != null){
            return Result.build(202,"已经收藏过了");
        }
        if (StringUtils.isEmpty(collect.getSongId())){
            return Result.build(203, "请选择歌曲收藏");
        }
        boolean flag = collectService.save(collect);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 删除收藏
    @ApiOperation(value = "删除收藏")
    @GetMapping("/deleteCollect/{songId}/{consumerId}")
    public Result deleteCollect(@PathVariable Integer songId,
                                @PathVariable Integer consumerId){
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("song_id",songId);
        wrapper.eq("consumer_id",consumerId);
        boolean flag = collectService.remove(wrapper);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 查询某个用户的收藏列表
    @ApiOperation(value = "查询某个用户的收藏列表")
    @GetMapping("/getCollectByConsumerId/{consumerId}")
    public Result getCollectByConsumerId(@PathVariable Integer consumerId){
        List<Collect> collectList = collectService.selectCollectList(consumerId);
        return Result.ok(collectList);
    }

}
