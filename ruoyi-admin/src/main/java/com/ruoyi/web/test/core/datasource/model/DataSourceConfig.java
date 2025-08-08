package com.ruoyi.web.test.core.datasource.model;

import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class DataSourceConfig {
    @NotBlank private String name;
    @NotBlank private String dbType;
    @NotBlank private String jdbcUrl;
    @NotBlank private String username;

    // 直接存储明文密码（仅用于测试）
    private String password;

    private Integer maxPoolSize = 10;

    // 简化方法
    public String getDecryptedPassword() {
        return password; // 直接返回明文密码
    }
}
