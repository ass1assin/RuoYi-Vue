package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SystemUserAddress;
import com.ruoyi.system.mapper.SystemServiceCategoryMapper;
import com.ruoyi.system.mapper.SystemWechatMapper;
import com.ruoyi.system.service.ISystemWechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemWechatServiceImpl implements ISystemWechatService {

    @Autowired
    private SystemWechatMapper systemWechatMapper;

    public int insertSystemUserAddress(SystemUserAddress systemUserAddress){
        return systemWechatMapper.insertSystemUserAddress(systemUserAddress);
    };

    public List<SystemUserAddress> getUserAddress(Long userId){
        return systemWechatMapper.getUserAddress(userId);
    };
}
