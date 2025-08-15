package com.ruoyi.web.test.core.template;

import com.ruoyi.web.test.core.dialect.DatabaseDialect;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
@Component
public class UnifiedSqlTemplateProcessor {

    private static final Pattern FUNCTION_PATTERN = Pattern.compile(
            "\\{([a-z_]+):([^:}]+):([^}]+)\\}"
    );

    public String processTemplate(String template, DatabaseDialect dialect) {
        // 1. 处理统一函数语法
        Matcher matcher = FUNCTION_PATTERN.matcher(template);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String funcName = matcher.group(1);
            String arg = matcher.group(2).trim();
            String format = matcher.group(3).trim();

            String replacement = processFunction(funcName, arg, format, dialect, matcher.group(0));
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        String processed = sb.toString();

        // 2. 移除分页占位符
        processed = processed.replace("{pagination}", "");

        // 3. 处理数据库特定语法
        return replaceDbSpecificSyntax(processed, dialect);
    }

    private String processFunction(String funcName, String arg, String format,
                                   DatabaseDialect dialect, String original) {
        // 判断是否是参数格式 #{param}
        boolean isParam = arg.startsWith("#{") && arg.endsWith("}");
        String paramName = isParam ? arg.substring(2, arg.length() - 1) : null;

        switch (funcName) {
            case "date":
                if (isParam) {
                    return dialect.toChar(":" + paramName, format);
                } else {
                    return dialect.toChar(arg, format);
                }
            case "to_date":
                if (isParam) {
                    return dialect.toDate(":" + paramName, format);
                } else {
                    return dialect.toDate(arg, format);
                }
            default:
                return original;
        }
    }

    private String replaceDbSpecificSyntax(String sql, DatabaseDialect dialect) {
        String dbType = dialect.getDbType().toUpperCase();

        // 移除所有反斜杠转义
        sql = sql.replace("\\", "");

        switch (dbType) {
            case "ORACLE":
                return sql.replace("`", "\"")
                        .replace(":var", "#{var}");
            case "MYSQL":
                return sql.replace("\"", "`")
                        .replace("TRUNC(", "DATE(")
                        .replace("TO_DATE", "STR_TO_DATE")
                        .replace("TO_CHAR", "DATE_FORMAT");
            case "POSTGRESQL":
                return sql.replace("TRUNC(", "DATE_TRUNC('day', ")
                        .replace("TO_DATE", "TO_DATE")
                        .replace("TO_CHAR", "TO_CHAR");
            default:
                return sql;
        }
    }
}
