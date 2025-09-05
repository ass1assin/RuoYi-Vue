package com.ruoyi.order.service;

import com.ruoyi.order.domain.Order;

public interface IOrderService {
    Order createOrder(Order order);
    Order getOrderById(Long orderId);
    Order getOrderByOrderNo(String orderNo);
    int updateOrderStatus(Long orderId, String status);
}