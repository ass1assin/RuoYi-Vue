package com.ruoyi.web.test.core.datasource;


import com.ruoyi.web.test.core.datasource.model.BoundSql;
import com.ruoyi.web.test.core.datasource.model.SqlTemplate;
import com.ruoyi.web.test.core.exception.TemplateNotFoundException;
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

    @Autowired
    public SqlTemplateEngine(@Qualifier("configJdbcTemplate") JdbcTemplate configJdbcTemplate) {
        this.configJdbcTemplate = configJdbcTemplate;

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
        } catch (Exception e) {
            throw new RuntimeException("加载SQL模板失败", e);
        }
    }

    public BoundSql parseTemplate(String templateId, Map<String, Object> params) {
        SqlTemplate template = templateRepo.get(templateId);
        if (template == null) {
            throw new TemplateNotFoundException("SQL模板未找到: " + templateId);
        }

        // 1. 预处理SQL：移除函数内的占位符并转义XML特殊字符
        String processedSql = preprocessSql(template.getRawSql());

        // 修改创建SqlSource的方式
        SqlSource sqlSource;
        if (processedSql.contains("<") || processedSql.contains(">")) {
            // 使用原始SQL文本，避免XML解析
            sqlSource = new RawSqlSource(mybatisConfig, processedSql, Map.class);
        } else {
            sqlSource = new RawSqlSource(mybatisConfig, processedSql, Map.class);
        }

        org.apache.ibatis.mapping.BoundSql mybatisBoundSql = sqlSource.getBoundSql(params);

        // 过滤掉函数中的常量参数
        List<ParameterMapping> filteredMappings = new ArrayList<>();
        Map<String, Object> realParams = new HashMap<>();

        for (ParameterMapping mapping : mybatisBoundSql.getParameterMappings()) {
            String property = mapping.getProperty();

            // 只处理真实参数（非函数常量）
            if (params.containsKey(property)) {
                filteredMappings.add(mapping);
                realParams.put(property, params.get(property));
            }
        }

        // 构建有序参数数组
        Object[] orderedParams = filteredMappings.stream()
                .map(mapping -> realParams.get(mapping.getProperty()))
                .toArray();

        return new BoundSql(
                mybatisBoundSql.getSql(),  // 原始SQL
                realParams,
                orderedParams
        );
    }

    private String preprocessSql(String rawSql) {
        // 处理 TO_DATE/TO_CHAR 函数
        String sql = rawSql.replaceAll(
                "TO_(DATE|CHAR)\\s*\\(\\s*#\\{(\\w+)\\}\\s*,\\s*'([^']+)'\\s*\\)",
                "TO_$1($2, '$3')"
        );

        // 通用函数处理正则
        Pattern funcPattern = Pattern.compile(
                "(TO_(DATE|CHAR)\\s*\\(\\s*)(#\\{\\w+\\})(\\s*,\\s*)'([^']+)'(\\)?)"
        );

        Matcher m = funcPattern.matcher(sql);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            // 重构函数调用：保留格式字符串为常量
            String replacement = m.group(1) + "?" + m.group(4) + "'" + m.group(5) + "'" + m.group(6);
            m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        m.appendTail(sb);

        return sql.replace("''", "'");
    }

    // 添加缺失的getTemplate方法
    public SqlTemplate getTemplate(String templateId) {
        return templateRepo.get(templateId);
    }
}
