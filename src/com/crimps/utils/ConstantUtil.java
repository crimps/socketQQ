package com.crimps.utils;

/**
 * 常量实体
 *
 * @author crimps
 * @create 2017-10-24 15:20
 **/
public class ConstantUtil {

    /**
     * sql表名标识
     */
    public final static String SQL_TABLE_NAME = "#TABLENAME#";

    /**
     * sql列标识
     */
    public final static String SQL_COLUMNS = "#COLUMNS#";

    /**
     * sql值标识
     */
    public final static String SQL_VALUES = "#VALUES#";

    /**
     * sql插入语句模板
     */
    public final static String SQL_INSERT = "INSERT INTO #TABLENAME# (#COLUMNS#) VALUES (#VALUES#);";
}
