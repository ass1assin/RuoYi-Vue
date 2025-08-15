package com.ruoyi.web.test.core.service;


import java.util.List;
import java.util.Map;

public interface MedicalDataSourceService {
    /**
     * 执行SQL模板查询
     * @param templateId SQL模板ID
     * @param params 参数Map
     * @return 查询结果列表
     */
    List<Map<String, Object>> executeQuery(String templateId, Map<String, Object> params);
    /**
     * 动态执行SQL（开发环境调试用）
     * @param datasource 数据源名称
     * @param sql 原始SQL
     * @param params 参数Map
     * @return 查询结果
     */
    List<Map<String, Object>> dynamicQuery(String datasource, String sql, Map<String, Object> params);

}
