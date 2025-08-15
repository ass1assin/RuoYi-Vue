package com.ruoyi.web.test.core.datasource.model;

import java.util.Map;

public class BoundSql {
    private final String sql;
    private final Map<String, Object> parameters;
    private final Object[] orderedParameters;

    public BoundSql(String sql, Map<String, Object> parameters, Object[] orderedParameters) {
        this.sql = sql;
        this.parameters = parameters;
        this.orderedParameters = orderedParameters;
    }

    public String getSql() {
        return sql;
    }

    public Object[] getParametersArray() {
        return orderedParameters;
    }
}
