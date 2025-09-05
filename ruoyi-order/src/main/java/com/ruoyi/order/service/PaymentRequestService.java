package com.ruoyi.order.service;

import com.ruoyi.mq.config.RabbitMQConfig;
import com.ruoyi.order.domain.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentRequestService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

//    public void sendPaymentRequest(Order order) {
//        System.out.println("[订单服务] 准备发送支付请求消息: 订单号=" + order.getOrderNo());
//
//        // 构造支付请求消息
//        Map<String, Object> paymentMessage = new HashMap<>();
//        paymentMessage.put("orderNo", order.getOrderNo());
//        paymentMessage.put("amount", order.getAmount());
//        paymentMessage.put("userId", order.getUserId());
//
//        // 发送到消息队列
//        rabbitTemplate.convertAndSend(RabbitMQConfig.PAYMENT_EXCHANGE, RabbitMQConfig.PAYMENT_ROUTING_KEY, paymentMessage);
//        System.out.println("[订单服务] 支付请求消息发送完成: 订单号=" + order.getOrderNo());
//    }
}
