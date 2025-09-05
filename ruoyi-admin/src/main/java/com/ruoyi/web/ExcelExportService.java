//package com.ruoyi.web;
//
//import com.alibaba.excel.EasyExcel;
//import lombok.Data;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class ExcelExportService {
//
//    public void exportToExcel(List<Data> dataList, String fileName) {
//        List<DataExcelModel> excelData = dataList.stream()
//                .map(data -> {
//                    DataExcelModel model = new DataExcelModel();
//                    model.setId(data.getId());
//                    model.setContent(data.getContent());
//                    return model;
//                })
//                .collect(Collectors.toList());
//
//        EasyExcel.write(fileName, DataExcelModel.class)
//                .sheet("数据报表")
//                .doWrite(excelData);
//    }
//}
