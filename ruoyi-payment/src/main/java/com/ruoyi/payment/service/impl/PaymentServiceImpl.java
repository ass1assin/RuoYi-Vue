package com.ruoyi.payment.service.impl;

import com.ruoyi.payment.domain.PaymentRequest;
import com.ruoyi.payment.domain.PaymentResponse;
import com.ruoyi.payment.service.IPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentServiceImpl implements IPaymentService {

    @Value("${mock.payment.url:http://localhost:8082/mock-payment/unified-order}")
    private String mockPaymentUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {
        try {
            // 构造调用Mock支付平台的请求参数
            Map<String, Object> mockRequest = new HashMap<>();
            mockRequest.put("orderNo", paymentRequest.getOrderNo());
            mockRequest.put("amount", paymentRequest.getAmount());
            mockRequest.put("userId", paymentRequest.getUserId());
            mockRequest.put("subject", paymentRequest.getSubject());
            mockRequest.put("body", paymentRequest.getBody());

            // 调用Mock支付平台的统一下单接口
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(mockPaymentUrl, mockRequest, Map.class);
            
            Map<String, Object> responseBody = responseEntity.getBody();
            
            // 构造返回给前端的支付响应
            PaymentResponse paymentResponse = new PaymentResponse();
            
            if (responseBody != null && "SUCCESS".equals(responseBody.get("status"))) {
                paymentResponse.setSuccess(true);
                paymentResponse.setMessage("支付参数生成成功");
                paymentResponse.setPayUrl((String) responseBody.get("payUrl"));
                paymentResponse.setQrCode((String) responseBody.get("qrCode"));
            } else {
                paymentResponse.setSuccess(false);
                paymentResponse.setMessage("支付参数生成失败");
            }
            
            return paymentResponse;
        } catch (Exception e) {
            PaymentResponse paymentResponse = new PaymentResponse();
            paymentResponse.setSuccess(false);
            paymentResponse.setMessage("调用支付服务异常: " + e.getMessage());
            return paymentResponse;
        }
    }
}