package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.SystemComment;
import com.ruoyi.system.domain.SystemOrders;
import com.ruoyi.system.domain.SystemUserAddress;
import com.ruoyi.system.mapper.SystemWechatMapper;
import com.ruoyi.system.service.ISystemWechatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
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

        // 计算结束时间
        calculateEndTime(systemOrders);

        return systemWechatMapper.createOrder(systemOrders);
    };

    public List<SystemComment> getComment(SystemComment systemComment){
        return systemWechatMapper.getComment(systemComment);
    };


    private void calculateEndTime(SystemOrders systemOrders) {
        if (systemOrders.getStartTime() == null || systemOrders.getOrderPackage() == null) {
            throw new IllegalArgumentException("Start time or order package cannot be null.");
        }

        // 解析 orderPackage 字段，直接当作小时数来处理
        String orderPackage = systemOrders.getOrderPackage().trim();  // 去除前后空格
        int duration = Integer.parseInt(orderPackage);  // 直接解析为数字，表示小时数

        // 输出日志帮助调试，确认时长是否正确
        System.out.println("Order Package (Hours): " + duration);

        // 获取开始时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(systemOrders.getStartTime());

        // 加上小时数
        calendar.add(Calendar.HOUR, duration);

        // 设置结束时间
        systemOrders.setEndTime(new Timestamp(calendar.getTimeInMillis()));
    }

}
