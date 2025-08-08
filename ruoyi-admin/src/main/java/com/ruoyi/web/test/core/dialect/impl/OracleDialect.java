package com.ruoyi.web.test.core.dialect.impl;

import com.ruoyi.web.test.core.dialect.DatabaseDialect;

public class OracleDialect implements DatabaseDialect {
    @Override
    public String paginate(String sql, int offset, int limit) {
        if (sql == null || sql.trim().isEmpty()) {
            return sql;
        }

        // 清理SQL
        String cleanSql = sql.trim().replaceAll(";$", "");

        // 检查是否已有ROWNUM条件
        if (cleanSql.toUpperCase().contains("ROWNUM")) {
            return cleanSql;
        }

        return "SELECT * FROM ("
                + "SELECT inner_query.*, ROWNUM AS rn FROM ("
                + cleanSql
                + ") inner_query WHERE ROWNUM <= " + (offset + limit)
                + ") WHERE rn > " + offset;
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
        return "SYSDATE";
    }

    @Override
    public String getDbType() {
        return "ORACLE";
    }

    @Override
    public String getDriverClassName() {
        return "oracle.jdbc.OracleDriver";
    }
}
