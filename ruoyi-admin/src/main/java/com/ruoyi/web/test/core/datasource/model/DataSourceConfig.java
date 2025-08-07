////package com.ruoyi.web.test.core.datasource.model;
////
////
////import com.fasterxml.jackson.annotation.JsonIgnore;
////import com.ruoyi.web.test.core.util.AESUtil;
////import lombok.Data;
////import javax.validation.constraints.NotBlank;
////
////@Data
////public class DataSourceConfig {
////    @NotBlank private String name;
////    @NotBlank private String dbType;
////    @NotBlank private String jdbcUrl;
////    @NotBlank private String username;
////
////    @JsonIgnore
////    private String encryptedPassword;
////
////    private Integer maxPoolSize = 10;
////    private String sqlTemplatePath;
////
////    public void setPassword(String plainPassword) {
////        this.encryptedPassword = AESUtil.encrypt(plainPassword);
////    }
////
////    public String getDecryptedPassword() {
////        return AESUtil.decrypt(encryptedPassword);
////    }
////}
//package com.ruoyi.web.test.core.datasource.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.ruoyi.web.test.core.util.AESUtil;
//import lombok.Data;
//import javax.validation.constraints.NotBlank;
//
//@Data
//public class DataSourceConfig {
//    @NotBlank private String name;
//    @NotBlank private String dbType;
//    @NotBlank private String jdbcUrl;
//    @NotBlank private String username;
//
//    @JsonIgnore
//    private String encryptedPassword;
//
//    private Integer maxPoolSize = 10;
//    private String sqlTemplatePath;
//
//    // 移除了setPassword方法，因为数据库存储的是加密密码
//
//    public String getDecryptedPassword() {
//        if (encryptedPassword == null) {
//            throw new IllegalStateException("加密密码未设置");
//        }
//        return AESUtil.decrypt(encryptedPassword);
//    }
//}
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
