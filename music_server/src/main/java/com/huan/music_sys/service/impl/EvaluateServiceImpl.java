package com.huan.music_sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huan.music_sys.domain.Evaluate;
import com.huan.music_sys.mapper.EvaluateMapper;
import com.huan.music_sys.service.EvaluateService;
import org.springframework.stereotype.Service;

@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate> implements EvaluateService {
    // 查询歌单评价总分
    @Override
    public int selectScoreSum(int songListId) {
        return baseMapper.selectScoreSum(songListId);
    }

    // 查询歌单评价人数
    @Override
    public int getCount(int songListId){
        QueryWrapper<Evaluate> wrapper = new QueryWrapper<>();
        wrapper.eq("song_list_id", songListId);
        return baseMapper.selectCount(wrapper);
    }

    // 查询歌单评价平均分
    @Override
    public float getAverage(int songListId){
        int count = getCount(songListId);
        if (count == 0){
            return 5.0f;
        }
        float score = (float)selectScoreSum(songListId) / (float)count;
        return ((float)Math.round(score*10))/10;
    }
}
