package com.huan.music_sys.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huan.music_sys.domain.Evaluate;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EvaluateMapper extends BaseMapper<Evaluate> {
    // 查询歌单评价总分
    int selectScoreSum(int songListId);

}
