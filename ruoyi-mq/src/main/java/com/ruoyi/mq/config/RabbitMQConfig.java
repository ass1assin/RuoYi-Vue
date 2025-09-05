package com.ruoyi.mq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitMQConfig {


    public static final String TEST_STRING_QUEUE = "test.string.queue";
    public static final String TEST_EXCHANGE = "test.exchange";
    public static final String TEST_ROUTING_KEY = "test.routing.key";

    /**
     * 声明一个持久化队列，用于测试字符串消息
     */
    @Bean
    public Queue testStringQueue() {
        // queueName, durable(是否持久化)
        return new Queue(TEST_STRING_QUEUE, true);
    }

    /**
     * 声明一个直连交换机
     */
    @Bean
    public DirectExchange testExchange() {
        // exchangeName, durable(是否持久化), autoDelete(是否自动删除)
        return new DirectExchange(TEST_EXCHANGE, true, false);
    }

    /**
     * 将队列与交换机绑定，并指定路由键
     */
    @Bean
    public Binding bindingString(Queue testStringQueue, DirectExchange testExchange) {
        return BindingBuilder.bind(testStringQueue)
                .to(testExchange)
                .with(TEST_ROUTING_KEY);
    }
}
