package com.ruoyi.web.test.core.dialect.impl;

import com.ruoyi.web.test.core.dialect.DatabaseDialect;

public class PostgreSQLDialect implements DatabaseDialect {
    @Override
    public String paginate(String sql, int offset, int limit) {
        if (sql == null || sql.trim().isEmpty()) {
            return sql;
        }

        // 清理SQL
        String cleanSql = sql.trim().replaceAll(";$", "");

        // 检查是否已有LIMIT子句
        if (cleanSql.toUpperCase().contains("LIMIT")) {
            return cleanSql;
        }

        return cleanSql + " LIMIT " + limit + " OFFSET " + offset;
    }

    @Override
    public String toChar(String column, String format) {
        return "TO_CHAR(" + column + ", '" + format + "')";
    }

    @Override
    public String toDate(String value, String format) {
        return "TO_DATE('" + value + "', '" + format + "')";
    }

    @Override
    public String currentTimestamp() {
        return "CURRENT_TIMESTAMP";
    }

    @Override
    public String getDbType() {
        return "POSTGRESQL";
    }

    @Override
    public String getDriverClassName() {
        return "org.postgresql.Driver";
    }
}
