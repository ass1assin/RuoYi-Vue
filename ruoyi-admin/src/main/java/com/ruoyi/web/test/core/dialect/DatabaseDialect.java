package com.ruoyi.web.test.core.dialect;

public interface DatabaseDialect {
    /**
     * 处理分页
     * @param sql 原始SQL
     * @param offset 起始位置
     * @param limit 记录数
     * @return 分页SQL
     */
    String paginate(String sql, int offset, int limit);

    /**
     * 日期格式化函数
     * @param column 日期列名
     * @param format 格式字符串
     * @return 格式化后的表达式
     */
    String toChar(String column, String format);

    /**
     * 字符串转日期函数
     * @param value 日期字符串
     * @param format 格式字符串
     * @return 日期表达式
     */
    String toDate(String value, String format);

    /**
     * 当前时间函数
     * @return 当前时间表达式
     */
    String currentTimestamp();

    /**
     * 获取数据库类型
     * @return 数据库类型标识
     */
    String getDbType();

    /**
     * 获取数据库驱动类名
     * @return 驱动类名
     */
    default String getDriverClassName() {
        switch (getDbType().toUpperCase()) {
            case "MYSQL":
                return "com.mysql.cj.jdbc.Driver";
            case "ORACLE":
                return "oracle.jdbc.OracleDriver";
            case "POSTGRESQL":
                return "org.postgresql.Driver";
            default:
                throw new IllegalArgumentException("Unsupported database type: " + getDbType());
        }
    }
}
