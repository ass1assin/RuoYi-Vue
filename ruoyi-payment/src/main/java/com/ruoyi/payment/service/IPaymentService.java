package com.ruoyi.payment.service;

import com.ruoyi.payment.domain.PaymentRequest;
import com.ruoyi.payment.domain.PaymentResponse;

import java.util.Map;

public interface IPaymentService {
    /**
     * 处理支付请求
     * @param paymentRequest 支付请求
     * @return 支付响应
     */
    PaymentResponse processPayment(PaymentRequest paymentRequest);
    
    /**
     * 处理支付回调
     * @param callbackData 回调数据
     * @return 处理结果
     */
    Map<String, Object> handlePaymentCallback(Map<String, Object> callbackData);
}