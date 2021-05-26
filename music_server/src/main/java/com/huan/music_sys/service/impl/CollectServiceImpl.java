package com.huan.music_sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huan.music_sys.domain.Collect;
import com.huan.music_sys.mapper.CollectMapper;
import com.huan.music_sys.service.CollectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {
    // 查询某个用户的收藏列表
    @Override
    public List<Collect> selectCollectList(Integer consumerId) {
        QueryWrapper<Collect> wrapper = new QueryWrapper<>();
        wrapper.eq("consumer_id",consumerId);
        return baseMapper.selectList(wrapper);
    }
}
