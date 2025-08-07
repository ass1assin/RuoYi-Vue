package com.ruoyi.web.test.core.datasource;


import com.ruoyi.web.test.core.datasource.model.DataSourceConfig;
import com.ruoyi.web.test.core.exception.DataSourceNotFoundException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
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

    public void addDataSource(DataSourceConfig config) {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getJdbcUrl());
        hikariConfig.setUsername(config.getUsername());
        hikariConfig.setPassword(config.getDecryptedPassword());
        hikariConfig.setMaximumPoolSize(config.getMaxPoolSize());

        switch (config.getDbType().toLowerCase()) {
            case "mysql":
                hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
                break;
            case "oracle":
                hikariConfig.setDriverClassName("oracle.jdbc.OracleDriver");
                break;
            case "postgresql":
                hikariConfig.setDriverClassName("org.postgresql.Driver");
                break;
            default:
                throw new IllegalArgumentException("不支持的数据库类型: " + config.getDbType());
        }

        try {
            DataSource dataSource = new HikariDataSource(hikariConfig);
            dataSources.put(config.getName(), dataSource);
            System.out.println("创建数据源 {} 成功: {}");
        } catch (Exception e) {
            System.out.println("创建数据源 {} 失败: {}");
            throw new RuntimeException("无法创建数据源: " + config.getName(), e);
        }
    }

    public JdbcTemplate getJdbcTemplate(String datasourceName) {
        DataSource ds = dataSources.get(datasourceName);
        if (ds == null) {
            throw new DataSourceNotFoundException("数据源未找到: " + datasourceName);
        }
        return new JdbcTemplate(ds);
    }
    // 添加获取所有数据源名称的方法
    public List<String> getAllDataSourceNames() {
        return new ArrayList<>(dataSources.keySet());
    }
}
