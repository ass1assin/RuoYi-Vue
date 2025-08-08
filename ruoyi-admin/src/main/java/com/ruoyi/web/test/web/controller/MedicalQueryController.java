package com.ruoyi.web.test.web.controller;


import com.ruoyi.web.test.core.service.MedicalDataSourceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/query")
public class MedicalQueryController {
    private final MedicalDataSourceService dataService;

    public MedicalQueryController(MedicalDataSourceService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/execute")
    public List<Map<String, Object>> executeQuery(
            @RequestBody QueryRequest request
    ) {
        return dataService.executeQuery(
                request.getTemplateId(),
                request.getParams()
        );
    }

    public static class QueryRequest {
        private String templateId;
        private Map<String, Object> params;
        private PageRequest page; // 分页参数
        public String getTemplateId() {
            return templateId;
        }

        public void setTemplateId(String templateId) {
            this.templateId = templateId;
        }

        public Map<String, Object> getParams() {
            return params;
        }

        public void setParams(Map<String, Object> params) {
            this.params = params;
        }

        public PageRequest getPage() {
            return page;
        }

        public void setPage(PageRequest page) {
            this.page = page;
        }
    }
}
