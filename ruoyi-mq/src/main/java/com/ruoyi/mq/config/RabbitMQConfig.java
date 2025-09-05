package com.ruoyi.mq.config;

import com.ruoyi.mq.constants.RabbitMQConstants;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {


    /**
     * 声明一个持久化队列，用于测试字符串消息
     */
    @Bean
    public Queue testStringQueue() {
        // queueName, durable(是否持久化)
        return new Queue(RabbitMQConstants.TEST_STRING_QUEUE, true);
    }

    /**
     * 声明一个直连交换机
     */
    @Bean
    public DirectExchange testExchange() {
        // exchangeName, durable(是否持久化), autoDelete(是否自动删除)
        return new DirectExchange(RabbitMQConstants.TEST_EXCHANGE, true, false);
    }

    /**
     * 将队列与交换机绑定，并指定路由键
     */
    @Bean
    public Binding bindingString(Queue testStringQueue, DirectExchange testExchange) {
        return BindingBuilder.bind(testStringQueue)
                .to(testExchange)
                .with(RabbitMQConstants.TEST_ROUTING_KEY);
    }
    
    /**
     * 声明订单超时处理队列
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(RabbitMQConstants.ORDER_QUEUE, true);
    }
    
    /**
     * 声明订单交换机
     */
    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(RabbitMQConstants.ORDER_EXCHANGE, true, false);
    }
    
    /**
     * 绑定订单队列与交换机
     */
    @Bean
    public Binding bindingOrder(Queue orderQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderQueue)
                .to(orderExchange)
                .with(RabbitMQConstants.ORDER_ROUTING_KEY);
    }
}