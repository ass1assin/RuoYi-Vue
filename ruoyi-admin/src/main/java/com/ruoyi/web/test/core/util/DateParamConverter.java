package com.ruoyi.web.test.core.util;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class DateParamConverter {
    // 仅处理日期部分（不含时间）
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 转换日期参数
     * @param params 原始参数Map
     * @return 转换后的参数Map（日期字符串转为Date对象）
     */
    public Map<String, Object> convertDates(Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();

        params.forEach((key, value) -> {
            if (isDateParam(key, value)) {
                result.put(key, formatDate((String) value));
            } else {
                result.put(key, value);
            }
        });

        return result;
    }

    /**
     * 判断是否为日期参数
     */
    private boolean isDateParam(String key, Object value) {
        if (!(value instanceof String)) return false;

        // 根据参数名判断（包含date/time关键词）
        String lowerKey = key.toLowerCase();
        return (lowerKey.contains("date") || lowerKey.contains("time"))
                && ((String) value).matches("\\d{4}-\\d{2}-\\d{2}.*");
    }


    /**
     * 格式化为 Oracle 兼容的日期字符串
     */
    private String formatDate(String dateStr) {
        try {
            // 1. 解析为Date对象确保格式正确
            Date date = new SimpleDateFormat(DATE_FORMAT).parse(dateStr);

            // 2. 格式化为Oracle需要的字符串
            return new SimpleDateFormat(DATE_FORMAT).format(date);
        } catch (ParseException e) {
            throw new IllegalArgumentException("无效的日期格式: " + dateStr);
        }
    }
}
