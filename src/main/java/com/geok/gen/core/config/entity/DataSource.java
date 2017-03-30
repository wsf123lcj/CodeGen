package com.geok.gen.core.config.entity;

/**
 * Created by Stephen on 2017/3/22.
 * 用户配置的数据源
 */
public class DataSource {
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String URL = "url";
    public static final String DRIVER = "driver";
    public static final String DIALECT = "dialect";

    private String user;
    private String password;
    private String url;
    private String driver;
    private DialectEnum dialectEnum;

    public enum DialectEnum {
        ORACLE, POSTGRES, MYSQL, SQLSERVER;

        @Override
        public String toString() {
            return super.toString().toLowerCase();
        }

        public static DialectEnum parse(String name) {
            return DialectEnum.valueOf(name.toUpperCase());
        }
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public DialectEnum getDialectEnum() {
        return dialectEnum;
    }

    public void setDialectEnum(DialectEnum dialectEnum) {
        this.dialectEnum = dialectEnum;
    }

    @Override
    public String toString() {
        return "DataSource{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", url='" + url + '\'' +
                ", driver='" + driver + '\'' +
                ", dialectEnum='" + dialectEnum + '\'' +
                '}';
    }
}
