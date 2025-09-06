package com.ruoyi.payment.controller;

import com.ruoyi.payment.domain.PaymentRequest;
import com.ruoyi.payment.domain.PaymentResponse;
import com.ruoyi.payment.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    @Autowired
    private IPaymentService paymentService;

    /**
     * 预支付接口
     * @param paymentRequest 支付请求参数
     * @return 支付响应信息
     */
    @PostMapping("/prepay")
    public PaymentResponse prepay(@RequestBody PaymentRequest paymentRequest) {
        return paymentService.processPayment(paymentRequest);
    }

    /**
     * 支付回调接口
     * @param callbackData 回调数据
     * @return 处理结果
     */
    @PostMapping("/notify")
    public Map<String, Object> notify(@RequestBody Map<String, Object> callbackData) {
        return paymentService.handlePaymentCallback(callbackData);
    }
}
