package com.ruoyi.web.controller.wechat;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/wechat/pay")
public class WechatPayController extends BaseController {

    @Value("${qrcode.payment.url}")
    private String qrCodeBaseUrl; // 支付二维码基础地址


    @PostMapping("/generateQRCode")
    public AjaxResult generateQRCode(@RequestBody Map<String, Object> params) {
        try {
            // 生成订单ID
            String orderId = UUID.randomUUID().toString().replace("-", "");
            BigDecimal amount = new BigDecimal(params.get("amount").toString());

            // 拼接支付链接
            String paymentUrl = qrCodeBaseUrl + "?orderId=" + orderId + "&amount=" + amount;

            // 使用工具类生成二维码
            String qrCodeUrl = QRCodeGenerator.generateQRCode(paymentUrl);

            // 保存订单信息到数据库
//            Order order = new Order();
//            order.setOrderId(orderId);
//            order.setAmount(amount);
//            order.setStatus("pending"); // 设置为待支付状态
//            orderService.saveOrder(order);

            // 返回二维码链接和订单ID
//            return AjaxResult.success(Map.of("qrCodeUrl", qrCodeUrl, "orderId", orderId));
            return AjaxResult.success(qrCodeUrl);
        } catch (Exception e) {
            return AjaxResult.error("生成支付二维码失败: " + e.getMessage());
        }
    }
}
