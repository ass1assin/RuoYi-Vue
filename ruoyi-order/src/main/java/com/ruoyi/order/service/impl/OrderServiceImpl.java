package com.ruoyi.order.service.impl;

import com.ruoyi.order.domain.Order;
import com.ruoyi.order.mapper.OrderMapper;
import com.ruoyi.order.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Order createOrder(Order order) {
        // 生成订单号
        String orderNo = "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        order.setOrderNo(orderNo);
        order.setStatus("CREATED");
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        
        // 插入数据库
        orderMapper.insertOrder(order);
        return order;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    @Override
    public Order getOrderByOrderNo(String orderNo) {
        return orderMapper.selectOrderByOrderNo(orderNo);
    }

    @Override
    public int updateOrderStatus(Long orderId, String status) {
        Order order = new Order();
        order.setOrderId(orderId);
        order.setStatus(status);
        order.setUpdateTime(new Date());
        return orderMapper.updateOrderStatus(order);
    }
}