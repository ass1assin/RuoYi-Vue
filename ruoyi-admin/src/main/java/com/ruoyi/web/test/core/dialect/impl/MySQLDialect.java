package com.ruoyi.web.test.core.dialect.impl;

import com.ruoyi.web.test.core.dialect.DatabaseDialect;

public class MySQLDialect implements DatabaseDialect {
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

        return cleanSql + " LIMIT " + offset + ", " + limit;
    }

    @Override
    public String toChar(String column, String format) {
        String mysqlFormat = format
                .replace("YYYY", "%Y")
                .replace("MM", "%m")
                .replace("DD", "%d")
                .replace("HH24", "%H")
                .replace("MI", "%i")
                .replace("SS", "%s");
        return "DATE_FORMAT(" + column + ", '" + mysqlFormat + "')";
    }

    @Override
    public String toDate(String value, String format) {
        String mysqlFormat = format
                .replace("YYYY", "%Y")
                .replace("MM", "%m")
                .replace("DD", "%d")
                .replace("HH24", "%H")
                .replace("MI", "%i")
                .replace("SS", "%s");
        return "STR_TO_DATE('" + value + "', '" + mysqlFormat + "')";
    }

    @Override
    public String currentTimestamp() {
        return "NOW()";
    }

    @Override
    public String getDbType() {
        return "MYSQL";
    }

    @Override
    public String getDriverClassName() {
        return "com.mysql.cj.jdbc.Driver";
    }
}
