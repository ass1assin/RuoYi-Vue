package com.ruoyi.mock.controller;

import com.ruoyi.mock.domain.PaymentQuery;
import com.ruoyi.mock.domain.PrepayResponse;
import com.ruoyi.mock.service.MockPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mock-pay")
public class MockPaymentController {

    @Autowired
    private MockPaymentService mockPaymentService;

    /**
     * 统一下单接口
     */
    @PostMapping("/unifiedorder")
    public Map<String, Object> unifiedOrder(@RequestBody Map<String, Object> params) {
        PrepayResponse response = mockPaymentService.createPrepayOrder(params);

        Map<String, Object> result = new HashMap<>();
        result.put("return_code", response.getReturnCode());
        result.put("result_code", response.getResultCode());
        result.put("prepay_id", response.getPrepayId());
        result.put("code_url", response.getCodeUrl());

        return result;
    }

    /**
     * 执行支付
     */
    @PostMapping("/execute-pay")
    public Map<String, Object> executePay(@RequestBody Map<String, Object> params) {
        String outTradeNo = (String) params.get("out_trade_no");
        String status = (String) params.get("status");

        boolean success = mockPaymentService.executePayment(outTradeNo, status);

        Map<String, Object> result = new HashMap<>();
        if (success) {
            result.put("return_code", "SUCCESS");
            result.put("result_code", "SUCCESS".equals(status) ? "SUCCESS" : "FAIL");
            result.put("result_msg", "SUCCESS".equals(status) ? "支付成功" : "支付失败");
        } else {
            result.put("return_code", "FAIL");
            result.put("return_msg", "操作失败");
        }

        return result;
    }

    /**
     * 查询订单
     */
    @GetMapping("/query")
    public PaymentQuery queryOrder(@RequestParam String out_trade_no) {
        return mockPaymentService.queryOrder(out_trade_no);
    }

    /**
     * 支付页面
     */
    @GetMapping("/page")
    public String payPage(@RequestParam String out_trade_no) {
        PaymentQuery query = mockPaymentService.queryOrder(out_trade_no);

        if (!"SUCCESS".equals(query.getReturnCode())) {
            return "订单不存在";
        }

        return "<!DOCTYPE html>" +
                "<html>" +
                "<head><title>模拟支付</title></head>" +
                "<body>" +
                "<h2>模拟支付页面</h2>" +
                "<p>订单号: " + out_trade_no + "</p>" +
                "<p>状态: " + query.getTradeState() + "</p>" +
                "<button onclick=\"pay('SUCCESS')\">模拟支付成功</button>" +
                "<button onclick=\"pay('FAIL')\">模拟支付失败</button>" +
                "<script>" +
                "function pay(status) {" +
                "  fetch('/mock-pay/execute-pay', {" +
                "    method: 'POST'," +
                "    headers: {'Content-Type': 'application/json'}," +
                "    body: JSON.stringify({out_trade_no: '" + out_trade_no + "', status: status})" +
                "  }).then(response => response.json())" +
                "    .then(data => {" +
                "      alert('操作结果: ' + (data.return_code === 'SUCCESS' ? '成功' : '失败'));" +
                "      if (data.result_code === 'SUCCESS') {" +
                "        window.location.href = 'http://your-frontend-url/pay-success';" +
                "      } else {" +
                "        window.location.href = 'http://your-frontend-url/pay-fail';" +
                "      }" +
                "    });" +
                "}" +
                "</script>" +
                "</body>" +
                "</html>";
    }
}
