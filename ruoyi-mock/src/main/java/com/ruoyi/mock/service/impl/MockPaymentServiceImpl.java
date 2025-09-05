package com.ruoyi.mock.service.impl;

import com.ruoyi.mock.domain.PaymentQuery;
import com.ruoyi.mock.domain.PrepayResponse;
import com.ruoyi.mock.service.MockPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class MockPaymentServiceImpl implements MockPaymentService {

    // 使用内存Map代替Redis
    private final Map<String, Map<String, Object>> orderStore = new ConcurrentHashMap<>();

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public PrepayResponse createPrepayOrder(Map<String, Object> params) {
        String outTradeNo = (String) params.get("out_trade_no");
        String notifyUrl = (String) params.get("notify_url");
        Integer totalFee = (Integer) params.get("total_fee");

        // 生成预支付信息
        String prepayId = "MOCK_" + System.currentTimeMillis();

        // 存储订单信息到内存Map
        Map<String, Object> orderInfo = new ConcurrentHashMap<>();
        orderInfo.put("out_trade_no", outTradeNo);
        orderInfo.put("total_fee", totalFee);
        orderInfo.put("notify_url", notifyUrl);
        orderInfo.put("status", "NOTPAY"); // 未支付
        orderInfo.put("prepay_id", prepayId);

        orderStore.put(outTradeNo, orderInfo);

        // 返回结果
        PrepayResponse response = new PrepayResponse();
        response.setReturnCode("SUCCESS");
        response.setResultCode("SUCCESS");
        response.setPrepayId(prepayId);
        response.setCodeUrl("http://localhost:8080/mock-pay/page?out_trade_no=" + outTradeNo);

        return response;
    }

    @Override
    public boolean executePayment(String outTradeNo, String status) {
        // 从内存Map获取订单信息
        Map<String, Object> order = orderStore.get(outTradeNo);

        if (order == null) {
            return false;
        }

        // 更新订单状态
        order.put("status", "SUCCESS".equals(status) ? "SUCCESS" : "FAIL");

        // 获取回调地址并发送回调
        String notifyUrl = (String) order.get("notify_url");
        if (notifyUrl != null) {
            // 准备回调数据
            Map<String, Object> callbackData = new ConcurrentHashMap<>();
            callbackData.put("out_trade_no", outTradeNo);
            callbackData.put("transaction_id", "MOCK_TX_" + System.currentTimeMillis());
            callbackData.put("total_fee", order.get("total_fee"));
            callbackData.put("trade_state", "SUCCESS".equals(status) ? "SUCCESS" : "FAIL");
            callbackData.put("sign", generateMockSign(callbackData));

            // 异步发送回调
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // 模拟处理延迟
                    ResponseEntity<String> response = restTemplate.postForEntity(
                            notifyUrl, callbackData, String.class);
                    System.out.println("回调发送结果: " + response.getStatusCode());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }

        return true;
    }

    @Override
    public PaymentQuery queryOrder(String outTradeNo) {
        Map<String, Object> order = orderStore.get(outTradeNo);

        PaymentQuery query = new PaymentQuery();
        if (order != null) {
            query.setReturnCode("SUCCESS");
            query.setResultCode("SUCCESS");
            query.setOutTradeNo(outTradeNo);
            query.setTradeState((String) order.get("status"));
        } else {
            query.setReturnCode("FAIL");
            query.setReturnMsg("订单不存在");
        }

        return query;
    }

    @Override
    public boolean verifySign(Map<String, Object> params) {
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

    public String generateMockSign(Map<String, Object> params) {
        // 简单模拟签名生成
        return "MOCK_SIGN_" + params.hashCode();
    }
}
