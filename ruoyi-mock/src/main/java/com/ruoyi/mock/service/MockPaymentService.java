package com.ruoyi.mock.service;



import com.ruoyi.mock.domain.PaymentQuery;
import com.ruoyi.mock.domain.PrepayResponse;

import java.util.Map;

/**
 * 模拟支付服务接口
 */
public interface MockPaymentService {

    /**
     * 统一下单
     */
    PrepayResponse createPrepayOrder(Map<String, Object> params);

    /**
     * 查询支付状态
     */
    PaymentQuery queryOrder(String outTradeNo);

    /**
     * 执行支付
     */
    boolean executePayment(String outTradeNo, String status);

    /**
     * 验证签名
     */
    boolean verifySign(Map<String, Object> params);
}
