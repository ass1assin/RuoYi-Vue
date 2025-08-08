package com.ruoyi.web.test.core.dialect;

import com.ruoyi.web.test.core.dialect.impl.MySQLDialect;
import com.ruoyi.web.test.core.dialect.impl.OracleDialect;
import com.ruoyi.web.test.core.dialect.impl.PostgreSQLDialect;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseDialectAdapter {
    private final Map<String, DatabaseDialect> dialectMap = new HashMap<>();

    public DatabaseDialectAdapter() {
        // 注册所有方言实现
        dialectMap.put("MYSQL", new MySQLDialect());
        dialectMap.put("ORACLE", new OracleDialect());
        dialectMap.put("POSTGRESQL", new PostgreSQLDialect());
    }

    public DatabaseDialect getDialect(String dbType) {
        String normalizedType = normalizeDbType(dbType);
        DatabaseDialect dialect = dialectMap.get(normalizedType);
        if (dialect == null) {
            throw new IllegalArgumentException("不支持的数据库类型: " + dbType);
        }
        return dialect;
    }

    private String normalizeDbType(String dbType) {
        if (dbType == null) return "MYSQL";
        String upper = dbType.toUpperCase();
        if (upper.contains("ORACLE")) return "ORACLE";
        if (upper.contains("POSTGRE")) return "POSTGRESQL";
        return "MYSQL";
    }

}
