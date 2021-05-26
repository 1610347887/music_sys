package com.huan.music_sys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 热更新、热加载
 * 1、Ctrl+Shift+A --> 搜索registry，然后找到。。。。when.app.running,勾选
 * 2、执行快捷键Ctrl+F9
 */
@SpringBootApplication
public class MusicSysApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusicSysApplication.class, args);
    }

}
