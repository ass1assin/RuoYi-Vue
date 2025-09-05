//package com.ruoyi.web;
//
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutorService;
//import java.util.stream.Collectors;
//
//@Service
//public class ExportService {
//
//    @Autowired
//    private DataService dataService;
//
//    @Autowired
//    private ExecutorService exportExecutor;
//
//    public List<Data> exportAllData() throws InterruptedException {
//        int totalPages = dataService.getTotalPages(10000); // 每页1万条
//
//        List<CompletableFuture<List<Data>>> futures = new ArrayList<>();
//
//        for (int i = 1; i <= totalPages; i++) {
//            int page = i;
//            futures.add(
//                    CompletableFuture.supplyAsync(
//                    () -> dataService.queryDataPage(page, 10000),
//                    exportExecutor
//            ));
//        }
//
//        return futures.stream()
//                .map(CompletableFuture::join)
//                .flatMap(List::stream)
//                .collect(Collectors.toList());
//    }
//}
