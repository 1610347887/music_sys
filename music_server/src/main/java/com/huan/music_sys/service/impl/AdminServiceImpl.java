package com.huan.music_sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huan.music_sys.mapper.AdminMapper;
import com.huan.music_sys.domain.Admin;
import com.huan.music_sys.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

}
