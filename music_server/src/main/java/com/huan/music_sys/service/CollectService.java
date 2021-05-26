package com.huan.music_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huan.music_sys.domain.Collect;

import java.util.List;


public interface CollectService extends IService<Collect> {
    // 查询某个用户的收藏列表
    List<Collect> selectCollectList(Integer consumerId);
}
