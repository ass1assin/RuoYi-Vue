//package com.ruoyi.web.test.web.advice;
//
//import com.ruoyi.web.test.core.exception.DataSourceNotFoundException;
//import freemarker.template.TemplateNotFoundException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(DataSourceNotFoundException.class)
//    public ResponseEntity<String> handleDataSourceNotFound(
//            DataSourceNotFoundException ex
//    ) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(ex.getMessage());
//    }
//
//    @ExceptionHandler(TemplateNotFoundException.class)
//    public ResponseEntity<String> handleTemplateNotFound(
//            TemplateNotFoundException ex
//    ) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(ex.getMessage());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleGeneralException(Exception ex) {
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body("服务器错误: " + ex.getMessage());
//    }
//}
