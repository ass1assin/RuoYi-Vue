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
//        ////
//        // 检查函数常量参数
//        for (Object param : orderedParameters) {
//            if (param instanceof String) {
//                String s = (String) param;
//                if (s.startsWith("'") && s.endsWith("'")) {
//                    throw new IllegalArgumentException("函数格式字符串不应作为参数: " + s);
//                }
//            }
//        }
//        /////
//        // 添加参数验证
//        int paramCount = countParameters(sql);
//        if (orderedParameters.length != paramCount) {
//            throw new IllegalStateException("参数数量不匹配! SQL需要" + paramCount +
//                    "个参数，但提供了" + orderedParameters.length + "个");
//        }
    }

//    private int countParameters(String sql) {
//        int count = 0;
//        int index = 0;
//        while ((index = sql.indexOf('?', index)) != -1) {
//            count++;
//            index++;
//        }
//        return count;
//    }

    public String getSql() {
        return sql;
    }

    public Object[] getParametersArray() {
        return orderedParameters;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }
}
