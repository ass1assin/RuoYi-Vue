package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SystemComment;
import com.ruoyi.system.domain.SystemOrders;
import com.ruoyi.system.domain.SystemUserAddress;
import com.ruoyi.system.mapper.SystemWechatMapper;
import com.ruoyi.system.service.ISystemWechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public List<SystemOrders> getOrder(SystemOrders systemOrders){
        return systemWechatMapper.getOrder(systemOrders);
    };

    public int createOrder(SystemOrders systemOrders){
        // 生成订单ID
        String orderId = String.format("%d%s", System.currentTimeMillis(), (int) (Math.random() * 10000));
        // 设置订单ID
        systemOrders.setOrderId(orderId);
        return systemWechatMapper.createOrder(systemOrders);
    };

    public List<SystemComment> getComment(SystemComment systemComment){
        return systemWechatMapper.getComment(systemComment);
    };
}
