package com.ruoyi.web.test.core.service.impl;

import com.ruoyi.web.test.core.datasource.DynamicDataSourceManager;
import com.ruoyi.web.test.core.datasource.SqlTemplateEngine;
import com.ruoyi.web.test.core.datasource.model.BoundSql;
import com.ruoyi.web.test.core.datasource.model.DataSourceConfig;
import com.ruoyi.web.test.core.datasource.model.SqlTemplate;
import com.ruoyi.web.test.core.service.MedicalDataSourceService;
import com.ruoyi.web.test.core.util.DateParamConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
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
            DateParamConverter dateParamConverter
    ) {
        this.dsManager = dsManager;
        this.templateEngine = templateEngine;
        this.dateParamConverter = dateParamConverter;
    }

    @Override
    public List<Map<String, Object>> executeQuery(
            String templateId,
            Map<String, Object> params
    ) {
        //1.日期转换
        Map<String, Object> processedParams = dateParamConverter.convertDates(params);
        System.out.println("=== 解析模板: " + templateId + " ===");
        System.out.println("原始SQL: " + templateEngine.getTemplate(templateId).getRawSql());

        //2.sql解析
        BoundSql boundSql = templateEngine.parseTemplate(templateId, processedParams);

        System.out.println("生成SQL: " + boundSql.getSql());
        System.out.println("参数数量: " + boundSql.getParametersArray().length);
        System.out.println("参数: " + Arrays.toString(boundSql.getParametersArray()));

        //3.获取数据源
        //获取sql模板
        SqlTemplate template = templateEngine.getTemplate(templateId);
        // 使用hospital_code获取数据源
        JdbcTemplate jdbcTemplate = dsManager.getJdbcTemplate(template.getHospitalCode());

        // 4. 执行查询
        return jdbcTemplate.queryForList(
                boundSql.getSql(),
                boundSql.getParametersArray()
        );
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
        return jdbcTemplate.queryForList(sql, params);
    }

//    @Override
//    public void addDataSource(DataSourceConfig config) {
//
//    }

//    @Override
//    public void reloadTemplates() {
//
//    }

    // 新增：支持返回自定义对象
    public <T> List<T> executeQuery(String templateId, Map<String, Object> params, Class<T> clazz) {
        BoundSql boundSql = templateEngine.parseTemplate(templateId, params);
        SqlTemplate template = templateEngine.getTemplate(templateId);

        JdbcTemplate jdbcTemplate = dsManager.getJdbcTemplate(template.getSource());
        return jdbcTemplate.query(
                boundSql.getSql(),
                boundSql.getParametersArray(),
                new BeanPropertyRowMapper<>(clazz)
        );
    }
}
