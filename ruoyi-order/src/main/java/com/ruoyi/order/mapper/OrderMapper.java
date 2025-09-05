package com.ruoyi.order.mapper;

import com.ruoyi.order.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    int insertOrder(Order order);
    Order selectOrderById(Long orderId);
    Order selectOrderByOrderNo(String orderNo);
    int updateOrderStatus(Order order);
}
