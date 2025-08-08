package com.ruoyi.web.test.core.datasource;


import com.ruoyi.web.test.core.datasource.model.BoundSql;
import com.ruoyi.web.test.core.datasource.model.SqlTemplate;
import com.ruoyi.web.test.core.dialect.DatabaseDialect;
import com.ruoyi.web.test.core.dialect.DatabaseDialectAdapter;
import com.ruoyi.web.test.core.exception.TemplateNotFoundException;
import com.ruoyi.web.test.core.template.UnifiedSqlTemplateProcessor;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class SqlTemplateEngine {
    private final JdbcTemplate configJdbcTemplate;
    private final Map<String, SqlTemplate> templateRepo = new ConcurrentHashMap<>();
    private final Configuration mybatisConfig = new Configuration();
    // 添加依赖
    private final UnifiedSqlTemplateProcessor templateProcessor;
    private final DynamicDataSourceManager dsManager;

    @Autowired
    public SqlTemplateEngine(@Qualifier("configJdbcTemplate") JdbcTemplate configJdbcTemplate,
                             DatabaseDialectAdapter dialectAdapter,
                             UnifiedSqlTemplateProcessor templateProcessor,
                             DynamicDataSourceManager dsManager) {
        this.configJdbcTemplate = configJdbcTemplate;
        this.templateProcessor = templateProcessor;
        this.dsManager = dsManager;

        // 初始化MyBatis配置
        mybatisConfig.setDefaultScriptingLanguage(XMLLanguageDriver.class);
        mybatisConfig.getTypeAliasRegistry().registerAlias("map", Map.class);
    }

    @PostConstruct
    public void loadTemplates() {
        try {
            // 确保查询包含hospital_code字段
            String sql = "SELECT id, template_key, description, sql_content, hospital_code " +
                    "FROM sql_template";

            List<SqlTemplate> templates = configJdbcTemplate.query(
                    sql,
                    (rs, rowNum) -> {
                        SqlTemplate t = new SqlTemplate();
                        t.setId(rs.getString("template_key"));
                        t.setDescription(rs.getString("description"));
                        t.setRawSql(rs.getString("sql_content"));
                        t.setHospitalCode(rs.getString("hospital_code")); // 关键字段
                        return t;
                    });

            templates.forEach(t -> templateRepo.put(t.getId(), t));
            System.out.println("Loaded " + templates.size() + " SQL templates");

            mybatisConfig.setDefaultScriptingLanguage(XMLLanguageDriver.class);
            mybatisConfig.getTypeAliasRegistry().registerAlias("map", Map.class);

            // 禁用字符串转义
            Properties variables = new Properties();
            variables.setProperty("escape", "");
            mybatisConfig.setVariables(variables);
        } catch (Exception e) {
            throw new RuntimeException("加载SQL模板失败", e);
        }
    }

    public BoundSql parseTemplate(String templateId, Map<String, Object> params) {
        SqlTemplate template = templateRepo.get(templateId);
        if (template == null) {
            throw new TemplateNotFoundException("SQL模板未找到: " + templateId);
        }
        String hospitalCode = template.getHospitalCode();
        // 获取数据源方言
        DatabaseDialect dialect = dsManager.getDialect(hospitalCode);
        // 处理统一模板
        String unifiedSql = templateProcessor.processTemplate(template.getRawSql(), dialect);
        // 打印处理后的SQL
        System.out.println("统一模板处理后SQL: " + unifiedSql);
        // 使用MyBatis处理参数化SQL
        SqlSource sqlSource = new RawSqlSource(mybatisConfig, unifiedSql, Map.class);
        org.apache.ibatis.mapping.BoundSql mybatisBoundSql = sqlSource.getBoundSql(params);

        // 过滤掉函数中的常量参数
        List<ParameterMapping> filteredMappings = new ArrayList<>();
        Map<String, Object> realParams = new HashMap<>();

        // 特殊处理函数中的参数
        Map<String, String> functionParams = new HashMap<>();
        params.forEach((key, value) -> {
            if (key.startsWith("func_")) {
                functionParams.put(key, value.toString());
            } else {
                realParams.put(key, value);
            }
        });

        for (ParameterMapping mapping : mybatisBoundSql.getParameterMappings()) {
            String property = mapping.getProperty();
            if (realParams.containsKey(property)) {
                filteredMappings.add(mapping);
            } else if (functionParams.containsKey("func_" + property)) {
                // 函数参数特殊处理
                filteredMappings.add(mapping);
                realParams.put(property, functionParams.get("func_" + property));
            }
        }

        // 构建有序参数数组
        Object[] orderedParams = filteredMappings.stream()
                .map(mapping -> realParams.get(mapping.getProperty()))
                .toArray();

        return new BoundSql(
                mybatisBoundSql.getSql().replace("''", "'"),   // 原始SQL
                realParams,
                orderedParams
        );
    }

    // 添加缺失的getTemplate方法
    public SqlTemplate getTemplate(String templateId) {
        return templateRepo.get(templateId);
    }
}
