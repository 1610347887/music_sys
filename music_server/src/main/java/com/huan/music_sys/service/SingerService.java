package com.huan.music_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huan.music_sys.domain.Singer;

import java.util.List;

public interface SingerService extends IService<Singer> {
    // 根据歌手性别查询歌手
    List<Singer> getSingerBySex(byte sex);
    // 根据歌手名字模糊查询歌手
    List<Singer> getSingerByName(String name);
}
