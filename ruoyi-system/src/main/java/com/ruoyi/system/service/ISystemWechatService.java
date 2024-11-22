package com.ruoyi.system.service;

import com.ruoyi.system.domain.SystemServiceCategory;
import com.ruoyi.system.domain.SystemUserAddress;

import java.util.List;

public interface ISystemWechatService {
    public int insertSystemUserAddress(SystemUserAddress systemUserAddress);

    public List<SystemUserAddress> getUserAddress(Long userId);
}
