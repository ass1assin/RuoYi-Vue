package com.ruoyi.web.test.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;
@Configuration
public class DataSourceConfig {

    // 使用Ruoyi的DruidConfig中已定义的 slaveDataSource
    @Bean(name = "configJdbcTemplate")
    public JdbcTemplate configJdbcTemplate(
            @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        return new JdbcTemplate(slaveDataSource);
    }
}

