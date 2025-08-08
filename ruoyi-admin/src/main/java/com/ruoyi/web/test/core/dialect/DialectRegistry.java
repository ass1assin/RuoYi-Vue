package com.ruoyi.web.test.core.dialect;

import com.ruoyi.web.test.core.dialect.impl.MySQLDialect;
import com.ruoyi.web.test.core.dialect.impl.OracleDialect;
import com.ruoyi.web.test.core.dialect.impl.PostgreSQLDialect;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DialectRegistry {
    private static final Map<String, DatabaseDialect> registry = new ConcurrentHashMap<>();

    static {
        // 注册内置方言
        register(new OracleDialect());
        register(new MySQLDialect());
        register(new PostgreSQLDialect());
    }

    public static void register(DatabaseDialect dialect) {
        registry.put(dialect.getDbType().toUpperCase(), dialect);
    }

    public static DatabaseDialect getDialect(String dbType) {
        DatabaseDialect dialect = registry.get(dbType.toUpperCase());
        if (dialect == null) {
            throw new IllegalArgumentException("Unsupported database type: " + dbType);
        }
        return dialect;
    }

}
