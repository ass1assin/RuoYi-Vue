package com.ruoyi.web.test.core.datasource;


import com.ruoyi.web.test.core.datasource.model.DataSourceConfig;
import com.ruoyi.web.test.core.dialect.DatabaseDialect;
import com.ruoyi.web.test.core.dialect.DatabaseDialectAdapter;
import com.ruoyi.web.test.core.exception.DataSourceNotFoundException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DynamicDataSourceManager {
    private final Map<String, DataSource> dataSources = new ConcurrentHashMap<>();
    private final Map<String, DatabaseDialect> dialectMap = new ConcurrentHashMap<>();
    private final Map<String, DataSourceConfig> configCache = new ConcurrentHashMap<>();
    private final DatabaseDialectAdapter dialectAdapter; // 注入方言适配器

    @Autowired
    public DynamicDataSourceManager(DatabaseDialectAdapter dialectAdapter) {
        this.dialectAdapter = dialectAdapter;
    }
    public void addDataSource(DataSourceConfig config) {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getJdbcUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getDecryptedPassword());
        hikariConfig.setMaximumPoolSize(config.getMaxPoolSize());

        // 使用方言获取驱动类名
        DatabaseDialect dialect = dialectAdapter.getDialect(config.getDbType());
        hikariConfig.setDriverClassName(dialect.getDriverClassName());
        // 存储方言信息
        dialectMap.put(config.getName(), dialectAdapter.getDialect(config.getDbType()));
        try {
            DataSource dataSource = new HikariDataSource(hikariConfig);
            dataSources.put(config.getName(), dataSource);
            dialectMap.put(config.getName(), dialect);
            configCache.put(config.getName(), config);
            System.out.println("创建数据源 {} 成功: {}");
        } catch (Exception e) {
            System.out.println("创建数据源 {} 失败: {}");
            throw new RuntimeException("无法创建数据源: " + config.getName(), e);
        }
    }

    // 新增方法：获取数据源的方言
    public DatabaseDialect getDialect(String datasourceName) {
        DatabaseDialect dialect = dialectMap.get(datasourceName);
        if (dialect == null) {
            throw new DataSourceNotFoundException("数据源方言未配置: " + datasourceName);
        }
        return dialect;
    }

    public JdbcTemplate getJdbcTemplate(String datasourceName) {
        DataSource ds = dataSources.get(datasourceName);
        if (ds == null) {
            throw new DataSourceNotFoundException("数据源未找到: " + datasourceName);
        }
        return new JdbcTemplate(ds);
    }

}
