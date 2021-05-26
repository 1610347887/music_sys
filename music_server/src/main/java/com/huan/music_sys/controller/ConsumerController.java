package com.huan.music_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.huan.music_sys.domain.Consumer;
import com.huan.music_sys.domain.vo.ConsumerVo;
import com.huan.music_sys.result.Result;
import com.huan.music_sys.service.ConsumerService;
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
import java.util.Locale;

@Api(value = "用户管理")
@RestController
@RequestMapping("/music_sys/consumer")
public class ConsumerController {
    @Autowired
    private ConsumerService consumerService;
    @Value("${web.upload-path}")
    private  String filePath;

    // 用户登录
    @ApiOperation(value = "用户登录")
    @PostMapping("/loginIn")
    public Result loginIn(@RequestBody ConsumerVo consumerVo){
        QueryWrapper<Consumer> wrapper = new QueryWrapper<>();
        wrapper.eq("username", consumerVo.getUsername());
        wrapper.eq("password",consumerVo.getPassword());
        Consumer consumer = consumerService.getOne(wrapper);
        if (consumer!=null){
            return Result.ok(consumer);
        }
        return Result.fail();
    }

    // 获取所有用户
    @ApiOperation(value = "获取所有用户")
    @GetMapping("/getAllConsumer")
    public Result getAllConsumer(){
        List<Consumer> consumerList = consumerService.list();
        return Result.ok(consumerList);
    }

    // 添加用户
    @PostMapping("/addConsumer")
    @ApiOperation(value = "添加用户")
    public Result addConsumer(@RequestBody(required = false) Consumer consumer){
        QueryWrapper<Consumer> wrapper = new QueryWrapper<>();
        wrapper.eq("username",consumer.getUsername());
        Consumer one = consumerService.getOne(wrapper);
        if (one!=null){
            return Result.build(201,"用户已存在");
        }
        boolean flag = consumerService.save(consumer);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 通过id获取用户信息
    @ApiOperation(value = "通过id获取用户信息")
    @GetMapping("/getConsumerInfo/{id}")
    public Result getConsumerInfo(@PathVariable Integer id){
        Consumer consumer = consumerService.getById(id);
        return Result.ok(consumer);
    }

    // 修改用户信息
    @ApiOperation(value = "修改用户信息")
    @PostMapping("/updateConsumer")
    public Result updateConsumer(@RequestBody Consumer consumer){
        boolean flag = consumerService.updateById(consumer);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 删除用户
    @ApiOperation(value = "删除用户")
    @GetMapping("/deleteConsumer/{id}")
    public Result deleteConsumer(@PathVariable Integer id){
        boolean flag = consumerService.removeById(id);
        if (flag){
            return Result.ok();
        }
        return Result.fail();
    }

    // 条件查询带分页(根据用户名和性别查询）
    @ApiOperation(value = "条件查询带分页（根据用户名和性别查询）")
    @PostMapping("findConsumer/{current}/{limit}")
    public Result findConsumer(@PathVariable int current,
                             @PathVariable int limit,
                             @RequestBody(required = false) ConsumerVo consumerVo){
        // 创建page对象，传递当前页和每页数量
        Page<Consumer> page = new Page<>(current,limit);
        // 构造条件
        QueryWrapper<Consumer> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(consumerVo.getUsername())){
            wrapper.like("username",consumerVo.getUsername());
        }
        if (!StringUtils.isEmpty(consumerVo.getSex())){
            wrapper.eq("sex",consumerVo.getSex());
        }
        Page<Consumer> consumerPage = consumerService.page(page, wrapper);
        String os = System.getProperty("os.name");
        System.out.println("66666666666666666666666666666666"+os);
        return Result.ok(consumerPage);
    }

    // 更新用户头像
    @ApiOperation(value = "更新用户头像")
    @PostMapping("/updateConsumerAvator/{id}")
    public Result updateConsumerAvator(@RequestParam("file") MultipartFile file, @PathVariable Integer id){
        if (file.isEmpty()){
            return Result.fail();
        }
        // 文件名：当前时间（毫秒）+原来的文件名
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
//        String filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + "img"
//                + System.getProperty("file.separator") + "consumerAvator";

        String realPath = null;
        String os = System.getProperty("os.name");         // 获取系统名称
        if (os.toLowerCase().startsWith("win")){          // win系统
            try {
                realPath = ResourceUtils.getURL("classpath:").getPath() + "static/img/consumerAvator/";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {                                           // Linux系统
            realPath = filePath + "/img/consumerAvator/";
        }

        File file1 = new File(realPath);
        if (!file1.exists()){
            file1.mkdir();
        }
        // 实际的文件地址
//        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        File dest = new File(realPath + fileName);
        // 存储到数据库里（相对文件地址）
        String picPath = "/img/consumerAvator/" + fileName;

        try {
            file.transferTo(dest);
            Consumer consumer = consumerService.getById(id);
            consumer.setAvator(picPath);
            boolean flag = consumerService.updateById(consumer);
            if (flag){
                return Result.ok();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.fail();
    }

}
