package com.ruoyi.payment.service.impl;

import com.ruoyi.payment.domain.OrderUpdateParam;
import com.ruoyi.payment.domain.PaymentRequest;
import com.ruoyi.payment.domain.PaymentResponse;
import com.ruoyi.payment.mapper.PaymentMapper;
import com.ruoyi.payment.service.IPaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Value("${mock.payment.url:http://localhost:8080/mock-pay/unifiedorder}")
    private String mockPaymentUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private PaymentMapper paymentMapper;

    private static final Logger log = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    @Retryable(
            value = {ResourceAccessException.class, DataAccessException.class, Exception.class}, // 重试的异常类型
    maxAttempts = 3, // 最大重试次数
    backoff = @Backoff(delay = 1000L, multiplier = 1.5) // 重试间隔：初始1秒， multiplier=1.5表示下次是上次的1.5倍
    )
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        //todo: 预支付补充spring重试

            // 构造调用Mock支付平台的请求参数
            Map<String, Object> mockRequest = new HashMap<>();
            mockRequest.put("out_trade_no", paymentRequest.getOrderNo());
            mockRequest.put("total_fee", paymentRequest.getAmount().multiply(new BigDecimal("100")).intValue()); // 转换为分
            mockRequest.put("notify_url", "http://localhost:8080/pay/notify"); // 支付结果回调地址
            // 添加其他需要的参数
            mockRequest.put("body", paymentRequest.getBody());
            mockRequest.put("subject", paymentRequest.getSubject());

            // 调用Mock支付平台的统一下单接口
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(mockPaymentUrl, mockRequest, Map.class);

            Map<String, Object> responseBody = responseEntity.getBody();

            // 构造返回给前端的支付响应
            PaymentResponse paymentResponse = new PaymentResponse();

            if (responseBody != null && "SUCCESS".equals(responseBody.get("return_code")) && "SUCCESS".equals(responseBody.get("result_code"))) {
                paymentResponse.setSuccess(true);
                paymentResponse.setMessage("支付参数生成成功");
                paymentResponse.setPayUrl((String) responseBody.get("code_url"));
                paymentResponse.setQrCode((String) responseBody.get("code_url"));
            } else {
                paymentResponse.setSuccess(false);
                paymentResponse.setMessage("支付参数生成失败");
            }

            return paymentResponse;

    }

    // 重试全部失败后的降级处理
    @Recover
    public PaymentResponse recoverProcessPayment(Exception e, PaymentRequest paymentRequest) {
        log.error("支付处理最终失败，订单号: {}, 错误信息: {}", paymentRequest.getOrderNo(), e.getMessage());

        PaymentResponse response = new PaymentResponse();
        response.setSuccess(false);
        response.setMessage("支付服务暂时不可用，请稍后重试");
        return response;
    }

    // 辅助方法：获取当前重试次数（用于日志记录）
    private int getRetryCount() {
        // 实际项目中可以通过更复杂的方式获取重试次数
        // 这里简化处理，只是示意
        return 1; // 需要更复杂实现来跟踪实际重试次数
    }

    // 模拟随机故障（测试用）
    private void simulateRandomFailure() {
        if (Math.random() < 0.7) { // 70%的概率模拟失败
            throw new RuntimeException("模拟的随机服务故障");
        }
    }

    @Override
    public Map<String, Object> handlePaymentCallback(Map<String, Object> callbackData) {
        Map<String, Object> result = new HashMap<>();

        try {
            // 1. 验签
            if (!verifySign(callbackData)) {
                result.put("return_code", "FAIL");
                result.put("return_msg", "签名验证失败");
                return result;
            }

            // 2. 幂等性处理 - 检查该订单是否已处理过
            String outTradeNo = (String) callbackData.get("out_trade_no");
            String redisKey = "payment:callback:" + outTradeNo;
            Boolean hasProcessed = redisTemplate.hasKey(redisKey);
            if (Boolean.TRUE.equals(hasProcessed)) {
                result.put("return_code", "SUCCESS");
                result.put("return_msg", "已处理");
                return result;
            }

            // 3. 更新订单状态
            String tradeState = (String) callbackData.get("trade_state");
            if ("SUCCESS".equals(tradeState)) {
                // 支付成功，更新订单状态
                updateOrderStatus(outTradeNo, "PAID");

                // 4. 触发后续业务 - 发送消息到 RabbitMQ
                triggerSubsequentBusiness(outTradeNo);
            }

            // 5. 记录已处理状态到Redis，防止重复处理
            redisTemplate.opsForValue().set(redisKey, "1", Duration.ofHours(24));
            result.put("return_code", "SUCCESS");
            result.put("return_msg", "OK");
        } catch (Exception e) {
            result.put("return_code", "FAIL");
            result.put("return_msg", "处理异常: " + e.getMessage());
        }

        return result;
    }

    /**
     * 验签方法
     * @param params 回调参数
     * @return 验签结果
     */
    private boolean verifySign(Map<String, Object> params) {
        // 获取接收到的签名
        String receivedSign = (String) params.get("sign");
        if (receivedSign == null) {
            return false;
        }

        // 创建参数的副本，排除 sign 字段
        Map<String, Object> paramsForVerify = new HashMap<>(params);
        paramsForVerify.remove("sign");

        // 使用排除 sign 后的参数计算签名
        String calculatedSign = generateMockSign(paramsForVerify);

        return calculatedSign.equals(receivedSign);
    }

    /**
     * 模拟签名生成
     * @param params 参数
     * @return 签名
     */
    private String generateMockSign(Map<String, Object> params) {
        // 简单模拟签名生成
        return "MOCK_SIGN_" + params.hashCode();
    }

    /**
     * 更新订单状态
     * @param orderNo 订单号
     * @param status 状态
     */
    private void updateOrderStatus(String orderNo, String status) {
        // 直接操作数据库更新订单状态
        try {
            OrderUpdateParam orderUpdateParam = new OrderUpdateParam(orderNo, status);
            int result = paymentMapper.updateOrderStatusByOrderNo(orderUpdateParam);
            if (result > 0) {
                System.out.println("订单状态更新成功: 订单号=" + orderNo + ", 状态=" + status);
            } else {
                System.out.println("订单状态更新失败: 未找到对应订单，订单号=" + orderNo);
            }
        } catch (Exception e) {
            System.err.println("订单状态更新异常: 订单号=" + orderNo + ", 错误信息=" + e.getMessage());
            throw e;
        }
    }

    /**
     * 触发后续业务
     * @param orderNo 订单号
     */
    private void triggerSubsequentBusiness(String orderNo) {
        // 这里应该发送消息到 RabbitMQ 触发后续业务
        // 由于缺少具体实现，暂时留空
        System.out.println("触发后续业务: 订单号=" + orderNo);
    }
}
