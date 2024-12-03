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


    // 计算结束时间
    private void calculateEndTime(SystemOrders systemOrders) {
        if (systemOrders.getStartTime() == null || systemOrders.getOrderPackage() == null) {
            throw new IllegalArgumentException("Start time or order package cannot be null.");
        }

        // 解析 orderPackage 字段
        String orderPackage = systemOrders.getOrderPackage(); // 格式: "2小时" 或 "1天"
        int duration = Integer.parseInt(orderPackage.replaceAll("\\D+", ""));  // 提取数字部分
        String unit = orderPackage.replaceAll("\\d+", "").trim();  // 提取单位部分 (小时/天)

        // 获取开始时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(systemOrders.getStartTime());

        // 根据单位来决定加上小时数或天数
        if ("小时".equals(unit)) {
            calendar.add(Calendar.HOUR, duration);  // 加上小时
        } else if ("天".equals(unit)) {
            calendar.add(Calendar.DAY_OF_MONTH, duration);  // 加上天数
        } else {
            throw new IllegalArgumentException("Invalid order package unit: " + unit);
        }

        // 设置结束时间
        systemOrders.setEndTime(new Timestamp(calendar.getTimeInMillis()));
    }
}
