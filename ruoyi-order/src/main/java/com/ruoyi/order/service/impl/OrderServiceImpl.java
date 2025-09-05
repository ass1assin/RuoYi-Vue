package com.ruoyi.order.service.impl;

import com.ruoyi.mq.config.RabbitMQConfig;
import com.ruoyi.order.domain.Order;
import com.ruoyi.order.mapper.OrderMapper;
import com.ruoyi.order.service.IOrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.ruoyi.mq.constants.RabbitMQConstants.ORDER_EXCHANGE;
import static com.ruoyi.mq.constants.RabbitMQConstants.ORDER_ROUTING_KEY;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

        // 发送延迟消息用于订单超时处理
        sendDelayMessage(order);

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

    /**
     * 发送延迟消息用于订单超时处理
     * @param order 订单信息
     */
    private void sendDelayMessage(Order order) {
        Map<String, Object> message = new HashMap<>();
        message.put("orderNo", order.getOrderNo());
        message.put("orderId", order.getOrderId());
        message.put("createTime", order.getCreateTime());

        // 发送延迟消息，延迟30分钟(1800000毫秒)
        rabbitTemplate.convertAndSend(
            ORDER_EXCHANGE,
            ORDER_ROUTING_KEY,
            message,
            msg -> {
                msg.getMessageProperties().setDelay(1800000); // 30分钟
                return msg;
            }
        );
    }
}
