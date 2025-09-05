import com.ruoyi.framework.config.DruidConfig;
import com.ruoyi.mock.domain.PaymentQuery;
import com.ruoyi.mock.domain.PrepayResponse;
import com.ruoyi.mock.service.MockPaymentService;
import com.ruoyi.mock.service.impl.MockPaymentServiceImpl;
import com.ruoyi.mq.config.RabbitMQConfig;
import com.ruoyi.order.service.TestMQService;
import com.ruoyi.web.test.config.DataSourceConfig;
import com.ruoyi.web.test.core.datasource.SqlTemplateEngine;
import com.ruoyi.web.test.core.datasource.model.SqlTemplate;
import com.ruoyi.web.test.core.service.MedicalDataSourceService;
import com.ruoyi.web.test.core.util.TestEncryptUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest(classes = com.ruoyi.RuoYiApplication.class) // 替换为实际主启动类
class ConfigDbTest {

//    @Autowired
//    private TestMQService testMQService;

//    @Autowired
//    private MockPaymentServiceImpl mockPaymentService;
     private MockPaymentServiceImpl mockPaymentService;

    @BeforeEach
    public void setUp() {
        mockPaymentService = new MockPaymentServiceImpl();
    }

    @Test
    public void testCreatePrepayOrder() {
        // 准备测试数据
        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_no", "test_order_001");
        params.put("total_fee", 100);
        params.put("body", "测试商品");
        params.put("notify_url", "http://localhost:8080/api/pay/callback");

        // 执行测试
        PrepayResponse response = mockPaymentService.createPrepayOrder(params);

        // 验证结果
        assertNotNull(response);
        assertEquals("SUCCESS", response.getReturnCode());
        assertEquals("SUCCESS", response.getResultCode());
        assertNotNull(response.getPrepayId());
        assertNotNull(response.getCodeUrl());

        // 打印成功信息（可选）
        System.out.println("testCreatePrepayOrder 测试通过!");
    }

    @Test
    public void testQueryOrder() {
        // 先创建一个订单
        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_no", "test_order_002");
        params.put("total_fee", 200);
        params.put("notify_url", "http://localhost:8080/api/pay/callback");
        mockPaymentService.createPrepayOrder(params);

        // 查询订单
        PaymentQuery query = mockPaymentService.queryOrder("test_order_002");

        // 验证结果
        assertNotNull(query);
        assertEquals("SUCCESS", query.getReturnCode());
        assertEquals("NOTPAY", query.getTradeState());
    }

    @Test
    public void testExecutePayment() {
        // 先创建一个订单
        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_no", "test_order_003");
        params.put("total_fee", 300);
        params.put("notify_url", "http://localhost:8080/api/pay/callback");
        mockPaymentService.createPrepayOrder(params);

        // 执行支付
        boolean result = mockPaymentService.executePayment("test_order_003", "SUCCESS");

        // 验证结果
        assertTrue(result);

        // 查询订单状态
        PaymentQuery query = mockPaymentService.queryOrder("test_order_003");
        assertEquals("SUCCESS", query.getTradeState());
    }

    @Test
    public void testVerifySign() {
        // 准备测试数据
        Map<String, Object> params = new HashMap<>();
        params.put("out_trade_no", "test_order_004");
        params.put("total_fee", 400);

        // 生成签名（使用排除 sign 的逻辑）
        String sign = mockPaymentService.generateMockSign(params);
        params.put("sign", sign);

        // 验证签名
        boolean isValid = mockPaymentService.verifySign(params);

        // 验证结果
        assertTrue(isValid);
    }

//    @Test
//    void testRabbitMQConnection() {
//        long startTime = System.currentTimeMillis();
//        testMQService.sendTestMessage("这是我的第三条测试消息！");
//        long endTime = System.currentTimeMillis();
//        System.out.println("测试执行时间: " + (endTime - startTime) + "ms");
//
//        // 断言执行时间应在合理范围内
//        assertTrue((endTime - startTime) < 2000, "测试执行时间过长");
//    }

}
