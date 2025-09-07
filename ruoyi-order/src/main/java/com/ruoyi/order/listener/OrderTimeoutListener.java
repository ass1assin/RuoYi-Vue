package com.ruoyi.order.listener;

import com.ruoyi.order.domain.Order;
import com.ruoyi.order.service.IOrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.ruoyi.mq.constants.RabbitMQConstants.ORDER_QUEUE;

@Component
public class OrderTimeoutListener {

    @Autowired
    private IOrderService orderService;

    /**
     * 监听订单超时队列
     * @param message 超时订单消息
     */
    @RabbitListener(queues = ORDER_QUEUE)
    public void handleOrderTimeout(Map<String, Object> message) {
        //todo 保证可靠性（消息处理过程中抛异常，消息会被RabbitMQ丢弃）
        try {
            System.out.println("[订单超时监听器] 接收到订单超时消息: " + message);

            // 获取订单号
            String orderNo = (String) message.get("orderNo");

            // 查询订单当前状态
            Order order = orderService.getOrderByOrderNo(orderNo);

            if (order == null) {
                System.out.println("[订单超时监听器] 订单不存在: " + orderNo);
                return;
            }

            // 检查订单状态是否为待支付(CREATED)
            if ("CREATED".equals(order.getStatus())) {
                // 执行关单操作，更新状态为已超时
                int result = orderService.updateOrderStatus(order.getOrderId(), "TIMEOUT");
                if (result > 0) {
                    System.out.println("[订单超时监听器] 订单超时关闭成功: " + orderNo);
                } else {
                    System.out.println("[订单超时监听器] 订单超时关闭失败: " + orderNo);
                }
            } else {
                System.out.println("[订单超时监听器] 订单状态不是待支付，无需关闭: " + orderNo + ", 当前状态: " + order.getStatus());
            }
        } catch (Exception e) {
            System.err.println("[订单超时监听器] 处理订单超时消息异常: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
