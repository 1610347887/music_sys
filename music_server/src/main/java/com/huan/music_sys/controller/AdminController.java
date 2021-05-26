package com.huan.music_sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huan.music_sys.domain.Admin;
import com.huan.music_sys.result.Result;
import com.huan.music_sys.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户管理")
@RestController
@RequestMapping("music_sys/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "用户登录")
    @PostMapping("login")
    public Result loginStatus(@RequestBody Admin admin){
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("name",admin.getName());
        wrapper.eq("password",admin.getPassword());
        int count = adminService.count(wrapper);
        if (count > 0){
            return Result.ok();
        }
        return Result.fail();
    }

}
