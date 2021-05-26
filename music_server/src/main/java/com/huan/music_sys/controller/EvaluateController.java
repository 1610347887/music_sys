package com.huan.music_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huan.music_sys.domain.Evaluate;
import com.huan.music_sys.result.Result;
import com.huan.music_sys.service.EvaluateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@Api(value = "评价管理")
@RestController
@RequestMapping("music_sys/evaluate")
public class EvaluateController {
    @Autowired
    private EvaluateService evaluateService;

    // 用户评价
    @ApiOperation(value = "用户评价")
    @PostMapping("/submitEvaluate")
    public Result submitEvaluate(@RequestBody Evaluate evaluate){
        boolean flag = evaluateService.save(evaluate);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 计算平均分
    @ApiOperation(value = "歌单平均分")
    @GetMapping("/getAverageScore/{songListId}")
    public Result getAverageScore(@PathVariable int songListId){
        Float averageScore = evaluateService.getAverage(songListId);
        System.out.println(averageScore);
        return Result.ok(averageScore);
    }

    // 通过歌单id和用户id获取用户评分
    @ApiOperation(value = "通过歌单id和用户id获取用户评分")
    @PostMapping("/getUserEvaluate")
    public Result getUserEvaluate(@RequestBody Evaluate evaluate){
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        wrapper.eq("song_list_id",evaluate.getSongListId());
        if (!StringUtils.isEmpty(evaluate.getConsumerId())){
            wrapper.eq("consumer_id",evaluate.getConsumerId());
            Evaluate one = evaluateService.getOne(wrapper);
            return Result.ok(one);
        }
        return Result.fail();
    }
}
