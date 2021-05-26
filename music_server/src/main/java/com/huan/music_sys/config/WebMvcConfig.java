package com.huan.music_sys.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 解决跨域问题
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(true);
    }
    /**
     * 定位资源位置
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // 歌手头像地址
//        registry.addResourceHandler("/img/singerPic/**").addResourceLocations(
//                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
//                        +System.getProperty("file.separator")+"singerPic"+System.getProperty("file.separator")
//        );
//
//        // 歌单图片地址
//        registry.addResourceHandler("/img/songListPic/**").addResourceLocations(
//                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
//                        +System.getProperty("file.separator")+"songListPic"+System.getProperty("file.separator")
//        );
//
//        // 歌曲图片地址
//        registry.addResourceHandler("/img/songPic/**").addResourceLocations(
//                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
//                        +System.getProperty("file.separator")+"songPic"+System.getProperty("file.separator")
//        );
//
//        // 歌曲地址
//        registry.addResourceHandler("song/**").addResourceLocations(
//                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"song"+System.getProperty("file.separator")
//        );
//
//        // 用户头像地址
//        registry.addResourceHandler("/img/consumerAvator/**").addResourceLocations(
//                "file:"+System.getProperty("user.dir")+System.getProperty("file.separator")+"img"
//                        +System.getProperty("file.separator")+"consumerAvator"+System.getProperty("file.separator")
//        );
//    }
}
