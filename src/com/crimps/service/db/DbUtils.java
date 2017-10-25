package com.crimps.service.db;

import com.crimps.ui.MainUI;
import com.crimps.utils.ConstantUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sqlite数据库服务
 *
 * @author crimps
 * @create 2017-10-19 10:13
 **/
public class DbUtils {
    private final String DB_CLASS_NAME = "org.sqlite.JDBC";
    private final String DB_URL = "jdbc:sqlite:" + MainUI.rootPath + "/resources/sqlite/";
    private Connection connection;

    public DbUtils(String dbName) throws Exception {
        Class.forName(DB_CLASS_NAME);
        connection = DriverManager.getConnection(DB_URL + dbName);
        connection.setAutoCommit(true);
    }

    /**
     * 关闭数据库连接
     */
    public void close() {
        try {
            if (null != connection) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("SQLException in close Connection " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 执行查询sql语句
     *
     * @param sqlStr sql语句
     * @return 查询结果
     */
    public List<Object> executeSelectSql(String sqlStr, Class<?> classType) {
        List<Object> resultList = new ArrayList<Object>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStr);
            while (resultSet.next()) {
                List<Field> fieldList = Arrays.asList(classType.getDeclaredFields());
                Object object = classType.newInstance();
                for (Field field : fieldList) {
                    String name = StringUtils.capitalize(field.getName());
                    Method method = classType.getMethod("set" + name, String.class);
                    method.invoke(object, resultSet.getString(field.getName()));
                }
                resultList.add(object);
            }
        } catch (Exception e) {
            System.out.println("SQLException in exectueSql " + e.getMessage());
            System.out.println(sqlStr);
            e.printStackTrace();
        } finally {
            try {
                if (null != resultSet) {
                    resultSet.close();
                }
                if (null != statement) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("SQLException in exectueSql finally :" + e.getMessage());
                e.printStackTrace();
            }
            return resultList;
        }
    }

    /**
     * 执行插入/更新语句
     *
     * @param sqlStr 插入/更新语句
     * @return 1:操作成功;2:未操作任何数据;-1:操作失败
     */
    public int executeUpdateSql(String sqlStr) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            return statement.executeUpdate(sqlStr);
        } catch (SQLException e) {
            System.out.println("SQLException in executeUpdateSql :" + e.getMessage());
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (null != statement) {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("SQLException in executeUpdateSql finally :" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 保存实体
     * @param tableName 表名
     * @param object 实体
     */
    public int save(String tableName, Object object) throws Exception{
        StringBuffer columns = new StringBuffer("");
        StringBuffer values = new StringBuffer("");
        List<Field> fieldList = new ArrayList<>();
        fieldList.addAll(Arrays.asList(object.getClass().getDeclaredFields()));
        fieldList.addAll(Arrays.asList(object.getClass().getSuperclass().getDeclaredFields()));
        for (Field field : fieldList) {
            String name = StringUtils.capitalize(field.getName());
            Method method = object.getClass().getMethod("get" + name);
            String value = (String)method.invoke(object);
            columns.append(field.getName()).append(",");
            values.append("\"").append(value).append("\"").append(",");
        }
        String sql = ConstantUtil.SQL_INSERT.replace(ConstantUtil.SQL_TABLE_NAME, tableName).
                replace(ConstantUtil.SQL_COLUMNS, StringUtils.removeEnd(columns.toString(), ",")).
                replace(ConstantUtil.SQL_VALUES, StringUtils.removeEnd(values.toString(), ","));
        System.out.println(sql);
        return executeUpdateSql(sql);
    }
}
