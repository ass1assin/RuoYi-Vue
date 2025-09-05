package com.ruoyi.mq.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestMessageListener {

    /**
     * 监听指定的队列，接收String类型的消息
     * @param message 接收到的消息内容
     */
    @RabbitListener(queues = "test.string.queue") // 队列名与配置中声明的常量一致
    public void handleTestStringMessage(String message) {
        System.out.println("[消费者] 收到消息: " + message);
        // 这里可以简单模拟处理消息
        System.out.println("[消费者] 模拟处理消息...");
    }
}

