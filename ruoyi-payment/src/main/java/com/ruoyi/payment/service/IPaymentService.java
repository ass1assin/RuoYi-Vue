package com.ruoyi.payment.service;

import com.ruoyi.payment.domain.PaymentRequest;
import com.ruoyi.payment.domain.PaymentResponse;

public interface IPaymentService {
    /**
     * 处理支付请求
     * @param paymentRequest 支付请求
     * @return 支付响应
     */
    PaymentResponse processPayment(PaymentRequest paymentRequest);
}