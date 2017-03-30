package com.geok.gen.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Stephen on 2017/3/21.
 *
 */
public class JDBCUtil {
    //配置用户密码
    private static String user;
    private static String password;
    //换成自己PostgreSQL数据库实例所在的ip地址，并设置自己的端口
    private static String url;
    private static String driver;
    //驱动是否已经被加载
    private static boolean isJDBCDriverLoaded = false;


    public static Connection getConnection() {
        if (!isJDBCDriverLoaded) {
            try {
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
            isJDBCDriverLoaded = true;
        }
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUser() {
        return user;
    }

    public static void setDateSource(String url, String user, String password) {
        JDBCUtil.url = url;
        JDBCUtil.user = user;
        JDBCUtil.password = password;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUrl() {
        return url;
    }


    public static String getDriver() {
        return driver;
    }

    public static void setDriver(String driver) {
        JDBCUtil.driver = driver;
    }
}