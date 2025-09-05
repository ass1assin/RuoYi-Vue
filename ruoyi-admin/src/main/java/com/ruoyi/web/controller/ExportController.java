//package com.ruoyi.web.controller;
//
//import com.alibaba.excel.EasyExcel;
//import com.ruoyi.web.DataExcelModel;
//import com.ruoyi.web.ExcelExportService;
//import com.ruoyi.web.ExportService;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/export")
//public class ExportController {
//
//    @Autowired
//    private ExportService exportService;
//
//    @Autowired
//    private ExcelExportService excelExportService;
//
//    @GetMapping("/report")
//    public void exportReport(HttpServletResponse response) throws Exception {
//        String fileName = "data_report_" + System.currentTimeMillis() + ".xlsx";
//        response.setContentType("application/vnd.ms-excel");
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//
//        // 多线程分页查询
//        List<Data> allData = exportService.exportAllData();
//
//        // EasyExcel导出
//        excelExportService.exportToExcel(allData, fileName);
//
//        // 流式写入Response（避免生成临时文件）
//        EasyExcel.write(response.getOutputStream(), DataExcelModel.class)
//                .sheet("数据报表")
//                .doWrite(allData.stream()
//                        .map(data -> new DataExcelModel(data.getId(), data.getContent()))
//                        .collect(Collectors.toList()));
//    }
//}
