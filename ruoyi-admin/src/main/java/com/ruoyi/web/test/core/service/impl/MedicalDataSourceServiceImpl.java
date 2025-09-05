package com.ruoyi.web.test.core.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.web.test.core.datasource.DynamicDataSourceManager;
import com.ruoyi.web.test.core.datasource.SqlTemplateEngine;
import com.ruoyi.web.test.core.datasource.model.BoundSql;
import com.ruoyi.web.test.core.datasource.model.SqlTemplate;
import com.ruoyi.web.test.core.service.MedicalDataSourceService;
import com.ruoyi.web.test.core.util.DateParamConverter;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MedicalDataSourceServiceImpl implements MedicalDataSourceService {
    private final DynamicDataSourceManager dsManager;
    private final SqlTemplateEngine templateEngine;
    private final DateParamConverter dateParamConverter;

    public MedicalDataSourceServiceImpl(
            DynamicDataSourceManager dsManager,
            SqlTemplateEngine templateEngine,
            DateParamConverter dateParamConverter) {
        this.dsManager = dsManager;
        this.templateEngine = templateEngine;
        this.dateParamConverter = dateParamConverter;
    }

    @Override
    public List<Map<String, Object>> executeQuery(
            String templateId,
            Map<String, Object> params
    ) {
        // 1. 日期转换
        Map<String, Object> processedParams = dateParamConverter.convertDates(params);

        System.out.println("=== 解析模板: " + templateId + " ===");
        SqlTemplate template = templateEngine.getTemplate(templateId);
        System.out.println("原始SQL: " + template.getRawSql());
      // 2. 特殊处理函数参数
        Map<String, Object> funcParams = new HashMap<>();
        processedParams.forEach((key, value) -> {
            if (key.startsWith("func_")) {
                funcParams.put(key, value);
            }
        });

        // 3. 合并参数
        Map<String, Object> allParams = new HashMap<>(processedParams);
        allParams.putAll(funcParams);

        // 4. 解析模板/////
        BoundSql boundSql = templateEngine.parseTemplate(templateId, allParams);

        String sql = boundSql.getSql();

        System.out.println("生成SQL: " + sql);
        System.out.println("参数数量: " + boundSql.getParametersArray().length);
        System.out.println("参数: " + Arrays.toString(boundSql.getParametersArray()));

        // 3. 分页处理
        if (params.containsKey("page") && params.containsKey("size")) {
            int page = (int) params.get("page");
            int size = (int) params.get("size");
            int offset = (page - 1) * size;
            sql = dsManager.getDialect(template.getHospitalCode())
                    .paginate(sql, offset, size);
        }

        // 4. 获取数据源并执行查询
        JdbcTemplate jdbcTemplate = dsManager.getJdbcTemplate(template.getHospitalCode());
        System.out.println("最终SQL: " + sql);

        return jdbcTemplate.queryForList(sql, boundSql.getParametersArray());
    }

    @Override
    @Profile("dev")
    public List<Map<String, Object>> dynamicQuery(
            String datasource,
            String sql,
            Map<String, Object> params
    ) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(
                dsManager.getJdbcTemplate(datasource)
        );
        Long userId = SecurityUtils.getUserId();
        synchronized (userId.toString().intern()){

        }
        return jdbcTemplate.queryForList(sql, params);
    }

}
