package com.ruoyi.web.test.web.controller;


import com.ruoyi.web.test.core.datasource.DynamicDataSourceManager;
import com.ruoyi.web.test.core.datasource.model.DataSourceConfig;
import com.ruoyi.web.test.core.exception.DataSourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/admin/datasources")
public class DataSourceAdminController {
    private final DynamicDataSourceManager dsManager;

    public DataSourceAdminController(DynamicDataSourceManager dsManager) {
        this.dsManager = dsManager;
    }

    @PostMapping
    public ResponseEntity<String> addDataSource(
            @Valid @RequestBody DataSourceConfig config
    ) {
        dsManager.addDataSource(config);
        return ResponseEntity.ok("数据源添加成功");
    }

    @PostMapping("/test/{name}")
    public ResponseEntity<String> testConnection(@PathVariable String name) {
        try {
            JdbcTemplate jdbc = dsManager.getJdbcTemplate(name);
            jdbc.execute("SELECT 1");
            return ResponseEntity.ok("连接测试成功");
        } catch (DataSourceNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("连接失败: " + e.getMessage());
        }
    }
}
