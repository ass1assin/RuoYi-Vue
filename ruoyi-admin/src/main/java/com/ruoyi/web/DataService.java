//package com.ruoyi.web;
//
//import com.github.pagehelper.IPage;
//import com.github.pagehelper.Page;
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class DataService {
//
//    @Autowired
//    private DataMapper dataMapper;
//
//    @DS("slave") // 从库读取
//    public List<Data> queryDataPage(int page, int pageSize) {
//        Page<Data> pageParam = new Page<>(page, pageSize);
//        IPage<Data> dataPage = dataMapper.selectPage(pageParam, null);
//        return dataPage.getRecords();
//    }
//}
