package com.huan.music_sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huan.music_sys.domain.Evaluate;

public interface EvaluateService extends IService<Evaluate> {
    // 查询歌单评价总分
    int selectScoreSum(int songListId);

    // 查询歌单评价人数
    int getCount(int songListId);

    // 查询歌单评价平均分
    float getAverage(int songListId);
}
