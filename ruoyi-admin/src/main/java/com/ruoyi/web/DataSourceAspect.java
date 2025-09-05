//package com.ruoyi.web;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.stereotype.Component;
//
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Aspect
//@Component
//public class DataSourceAspect {
//    private AtomicInteger counter = new AtomicInteger(0);
//
//    @Before("@annotation(ds)")
//    public void switchDataSource(JoinPoint point, DS ds) {
//        String key = ds.value();
//        if (key.startsWith("slave")) {
//            int index = counter.incrementAndGet() % 2 + 1; // 轮询选择 slave1/slave2
//            DynamicDataSourceContextHolder.push("slave" + index);
//        } else {
//            DynamicDataSourceContextHolder.push(key);
//        }
//    }
//}
