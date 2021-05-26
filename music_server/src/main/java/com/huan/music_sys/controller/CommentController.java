package com.huan.music_sys.controller;


import com.huan.music_sys.domain.Comment;
import com.huan.music_sys.result.Result;
import com.huan.music_sys.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户评论管理")
@RestController
@RequestMapping("music_sys/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    // 提交评论
    @ApiOperation(value = "用户提交评论")
    @PostMapping("/submitComment")
    public Result submitComment(@RequestBody Comment comment){
        boolean flag = commentService.save(comment);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 通过id获取评论信息
    @ApiOperation(value = "通过id获取评论信息")
    @GetMapping("/getCommentById/{id}")
    public Result getCommentById(@PathVariable Integer id){
        Comment comment = commentService.getById(id);
        return Result.ok(comment);
    }

    // 修改评论
    @ApiOperation(value = "用户修改评论")
    @PostMapping("/updateComment")
    public Result updateComment(@RequestBody Comment comment){
        boolean flag = commentService.updateById(comment);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 删除评论
    @ApiOperation(value = "删除评论")
    @GetMapping("/deleteComment/{id}")
    public Result deleteComment(@PathVariable Integer id){
        boolean flag = commentService.removeById(id);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 查询所有评论
    @ApiOperation(value = "查询所有评论")
    @GetMapping("/getAllComment")
    public Result getAllComment(){
        List<Comment> commentList = commentService.list();
        return Result.ok(commentList);
    }

    // 查询某个歌曲或歌单下的所有评论
    @ApiOperation(value = "查询某个歌曲下的所有评论")
    @GetMapping("/getComment/{type}/{id}")
    public Result getComment(@PathVariable Byte type,
                                 @PathVariable Integer id){
        List<Comment> commentList = commentService.selectListById(type,id);
        return Result.ok(commentList);
    }

    // 给某个评论点赞
    @ApiOperation(value = "给某个评论点赞")
    @PostMapping("/likeComment")
    public Result likeComment(@RequestBody Comment comment){
        boolean flag = commentService.updateById(comment);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }
}
