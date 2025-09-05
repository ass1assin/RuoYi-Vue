package com.ruoyi.order.service;


import com.ruoyi.mq.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TestMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送一条测试字符串消息
     * @param message
     */
    public void sendTestMessage(String message) {
        System.out.println("[生产者] 准备发送消息: " + message);
        // convertAndSend(String exchange, String routingKey, Object message)
        rabbitTemplate.convertAndSend(RabbitMQConfig.TEST_EXCHANGE, RabbitMQConfig.TEST_ROUTING_KEY, message);
        System.out.println("[生产者] 消息发送完成: " + message);
    }

    /**
     * 发送支付请求测试消息
     * @param paymentInfo
     */
//    public void sendPaymentRequest(Map<String, Object> paymentInfo) {
//        System.out.println("[订单服务] 准备发送支付请求测试消息: " + paymentInfo);
//        rabbitTemplate.convertAndSend(RabbitMQConfig.PAYMENT_EXCHANGE, RabbitMQConfig.PAYMENT_ROUTING_KEY, paymentInfo);
//        System.out.println("[订单服务] 支付请求测试消息发送完成");
//    }
}
