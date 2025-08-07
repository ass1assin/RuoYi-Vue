package com.ruoyi.contract.test;

//import com.ruoyi.contract.ContractModuleConfig;
import com.ruoyi.contract.utils.GenerateContract;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
//@SpringBootTest
public class test {

//    @Autowired
//    private GenerateContract generateContract;

//    @Test
    public static void main(String[] args) throws Exception{
        GenerateContract generateContract = new GenerateContract();
        Map<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("startTime","2024-06-09");
        stringObjectHashMap.put("endTime","2024-06-19");
        System.out.println(generateContract.generateHtmlTemplate(stringObjectHashMap));
    }
//      @Test
//      public static void main(String[] args) throws Exception{
//          ConfigurableApplicationContext context = SpringApplication.run(ContractModuleConfig.class, args);
//          // 2. 从 Spring 容器中获取 Bean
//          GenerateContract generateContract = context.getBean(GenerateContract.class);
//          // 3. 执行测试逻辑
//          Map<String, Object> data = new HashMap<>();
//          data.put("startTime", "2023-10-01");
//          String html = generateContract.generateHtmlTemplate(data);
//          System.out.println(html);
//      }
}
