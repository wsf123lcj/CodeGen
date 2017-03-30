package com.geok.gen.core.db.resolver;

import com.geok.gen.core.config.entity.ContextDatabase;
import com.geok.gen.core.config.entity.DataSource;
import com.geok.gen.core.db.entity.OriginalCatalogStructure;
import com.geok.gen.core.db.entity.OriginalTableStructure;
import com.geok.gen.util.StringUtil;

import java.sql.SQLException;
import java.util.Map;


/**
 * Created by sifanW on 2017/3/21.
 * 获取数据库多个指定库的结构
 */
public class PostgresDatabaseStructureResolver {
    private PostgresCatalogStructureResolver cResolver;

    public static PostgresDatabaseStructureResolver newInstance(DataSource dataSource, ContextDatabase cDatabase) {
        return new PostgresDatabaseStructureResolver(dataSource, cDatabase);
    }

    /**
     * 私有化构造方法
     */
    private PostgresDatabaseStructureResolver(DataSource dataSource, ContextDatabase cDatabase) {
        if (cDatabase == null) {
            throw new IllegalArgumentException("cDatabase can not be null!!!");
        }
        if (!checkDataSource(dataSource)) {
            throw new IllegalArgumentException("url or user or password or driver is illegal!!!");
        }
        String urlPattern = dataSource.getUrl();
        String newUrl = "";
        switch (dataSource.getDialectEnum()) {
            case POSTGRES:
                newUrl = urlPattern.replaceAll(StringUtil.REGEX, cDatabase.getNativeDbName());
                break;
            case ORACLE:
                newUrl = urlPattern;
                break;
                default:
        }
        OriginalCatalogStructure originalCatalogStructure = new OriginalCatalogStructure();
        originalCatalogStructure.setName(cDatabase.getNativeDbName());
        cResolver = new PostgresCatalogStructureResolver(originalCatalogStructure, dataSource, newUrl);
    }

    public OriginalTableStructure getTableStructure(String tableName) throws SQLException {
        return cResolver.getOriginalTableByName(tableName);
    }

    public Map<String, OriginalTableStructure> getAllTableStructure() throws SQLException {
        return cResolver.getAllOriginalTables();
    }


    /**
     * 判断参数是否合法
     * @return 是否合法
     */
    private boolean checkDataSource(DataSource dataSource) {
        String url, user, password, driver;
        url = dataSource.getUrl();
        user = dataSource.getUser();
        password = dataSource.getPassword();
        driver = dataSource.getDriver();
        return !(url == null || "".equals(url) || user == null || "".equals(user) || password == null || driver == null || "".equals(driver));
    }
}