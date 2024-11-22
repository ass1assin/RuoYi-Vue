package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SystemServiceCategory;
import com.ruoyi.system.domain.SystemUserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface SystemWechatMapper {
    public int insertSystemUserAddress(SystemUserAddress systemUserAddress);

    public List<SystemUserAddress> getUserAddress(Long userId);
}
