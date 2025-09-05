package com.ruoyi.web.test.core.datasource;

import com.ruoyi.web.test.core.datasource.model.DataSourceConfig;
import com.ruoyi.web.test.core.util.TestEncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class DataSourceInitializer {
    private final DynamicDataSourceManager dsManager;
    private final JdbcTemplate configJdbcTemplate;

    @Autowired
    public DataSourceInitializer(DynamicDataSourceManager dsManager,
                                 @Qualifier("configJdbcTemplate") JdbcTemplate configJdbcTemplate) {
        this.dsManager = dsManager;
        this.configJdbcTemplate = configJdbcTemplate;
    }

//    @PostConstruct
    public void initDataSources() {
        System.out.println("===== 开始初始化医院数据源（测试模式） =====");

        // 直接使用明文密码查询
        String sql = "SELECT hospital_code AS name, db_type, jdbc_url, username, password " +
                "FROM hospital_datasource WHERE status = 1";

        List<DataSourceConfig> configs = configJdbcTemplate.query(sql, (rs, rowNum) -> {
            DataSourceConfig config = new DataSourceConfig();
            config.setName(rs.getString("name"));
            config.setDbType(rs.getString("db_type"));
            config.setJdbcUrl(rs.getString("jdbc_url"));
            config.setUsername(rs.getString("username"));
            // 解密密码
            String encryptedPwd = rs.getString("password");
            String password = TestEncryptUtils.decrypt(encryptedPwd);
            config.setPassword(password);
            return config;
        });

        if (configs.isEmpty()) {
            System.out.println("警告: 未找到启用的医院数据源配置");
            return;
        }

        for (DataSourceConfig config : configs) {
            try {
                System.out.println("添加数据源: " + config.getName());
                dsManager.addDataSource(config);
                System.out.println("成功添加数据源: " + config.getName() +
                        " | URL: " + config.getJdbcUrl());
            } catch (Exception e) {
                System.err.println("添加数据源失败: " + config.getName() + " - " + e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("===== 医院数据源初始化完成 =====");
    }
}
