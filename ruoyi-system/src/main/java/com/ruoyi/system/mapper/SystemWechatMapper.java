package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SystemComment;
import com.ruoyi.system.domain.SystemOrders;
import com.ruoyi.system.domain.SystemUserAddress;

import java.util.List;

public interface SystemWechatMapper {
    public int insertSystemUserAddress(SystemUserAddress systemUserAddress);

    public List<SystemUserAddress> getUserAddress(Long userId);

    public List<SystemOrders> getOrder(SystemOrders systemOrders);

    public int createOrder(SystemOrders systemOrders);

    public List<SystemComment> getComment(SystemComment systemComment);
}
