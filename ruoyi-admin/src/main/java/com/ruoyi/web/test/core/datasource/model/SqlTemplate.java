package com.ruoyi.web.test.core.datasource.model;

import lombok.Data;

@Data
public class SqlTemplate {
    private String id;
    private String description;
    private String source;
    private String rawSql;
    private String hospitalCode; // 新增关键字段
}
