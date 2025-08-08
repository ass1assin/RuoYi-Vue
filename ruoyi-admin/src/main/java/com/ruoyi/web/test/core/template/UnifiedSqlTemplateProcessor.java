package com.ruoyi.web.test.core.template;

import com.ruoyi.web.test.core.dialect.DatabaseDialect;
import com.ruoyi.web.test.core.dialect.DialectRegistry;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.function.Function;
import java.util.function.BiFunction;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
@Component
public class UnifiedSqlTemplateProcessor {

    public String processTemplate(String template, DatabaseDialect dialect) {
        // 1. 处理统一函数语法
        Pattern funcPattern = Pattern.compile("\\{([a-z_]+):([^:]+):([^}]+)\\}");
        Matcher matcher = funcPattern.matcher(template);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            String funcName = matcher.group(1);
            String arg1 = matcher.group(2).trim();
            String arg2 = matcher.group(3).trim();

            switch (funcName) {
                case "date":
                    matcher.appendReplacement(sb, dialect.toChar(arg1, arg2));
                    break;
                case "to_date":
                    matcher.appendReplacement(sb, dialect.toDate(arg1, arg2));
                    break;
                default:
                    matcher.appendReplacement(sb, matcher.group(0));
            }
        }
        matcher.appendTail(sb);
        String processed = sb.toString();

        // 2. 移除分页占位符
        processed = processed.replace("{pagination}", "");

        // 3. 处理数据库特定语法
        processed = replaceDbSpecificSyntax(processed, dialect);

        return processed;
    }
    private String replaceDbSpecificSyntax(String sql, DatabaseDialect dialect) {
        String dbType = dialect.getDbType().toUpperCase();

        switch (dbType) {
            case "ORACLE":
                return sql.replace("`", "\"")
                        .replace(":var", "#{var}")
                        .replace("STR_TO_DATE", "TO_DATE")
                        .replace("DATE_FORMAT", "TO_CHAR");
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
