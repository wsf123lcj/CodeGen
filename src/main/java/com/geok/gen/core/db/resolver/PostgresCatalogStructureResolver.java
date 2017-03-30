package com.geok.gen.core.db.resolver;

import com.geok.gen.core.config.entity.Configurations;
import com.geok.gen.core.config.entity.DataSource;
import com.geok.gen.exception.CatalogNotExistsException;
import com.geok.gen.exception.InitializeException;
import com.geok.gen.core.db.entity.OriginalCatalogStructure;
import com.geok.gen.core.db.entity.OriginalColumnStructure;
import com.geok.gen.core.db.entity.OriginalPrimaryKeyStructure;
import com.geok.gen.core.db.entity.OriginalTableStructure;
import com.geok.gen.util.JDBCUtil;

import java.sql.*;
import java.util.Map;

/**
 * Created by sifanW on 2017/3/21.
 * 获取单个数据库结构
 TABLE_CAT String => 表类别（可为 null）
 TABLE_SCHEM String => 表模式（可为 null）
 TABLE_NAME String => 表名称
 COLUMN_NAME String => 列名称
 DATA_TYPE int => 来自 java.sql.Types 的 SQL 类型
 TYPE_NAME String => 数据源依赖的类型名称，对于 UDT，该类型名称是完全限定的
 COLUMN_SIZE int => 列的大小。
 BUFFER_LENGTH 未被使用。
 DECIMAL_DIGITS int => 小数部分的位数。对于 DECIMAL_DIGITS 不适用的数据类型，则返回 Null。
 NUM_PREC_RADIX int => 基数（通常为 10 或 2）
 NULLABLE int => 是否允许使用 NULL。
 columnNoNulls - 可能不允许使用 NULL 值
 columnNullable - 明确允许使用 NULL 值
 columnNullableUnknown - 不知道是否可使用 null
 REMARKS String => 描述列的注释（可为 null）
 COLUMN_DEF String => 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
 SQL_DATA_TYPE int => 未使用
 SQL_DATETIME_SUB int => 未使用
 CHAR_OCTET_LENGTH int => 对于 char 类型，该长度是列中的最大字节数
 ORDINAL_POSITION int => 表中的列的索引（从 1 开始）
 IS_NULLABLE String => ISO 规则用于确定列是否包括 null。
 YES --- 如果参数可以包括 NULL
 NO --- 如果参数不可以包括 NULL
 空字符串 --- 如果不知道参数是否可以包括 null
 SCOPE_CATLOG String => 表的类别，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）
 SCOPE_SCHEMA String => 表的模式，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）
 SCOPE_TABLE String => 表名称，它是引用属性的作用域（如果 DATA_TYPE 不是 REF，则为 null）
 SOURCE_DATA_TYPE short => 不同类型或用户生成 Ref 类型、来自 java.sql.Types 的 SQL 类型的源类型（如果 DATA_TYPE 不是 DISTINCT 或用户生成的 REF，则为 null）
 IS_AUTOINCREMENT String => 指示此列是否自动增加
 YES --- 如果该列自动增加
 NO --- 如果该列不自动增加
 空字符串 --- 如果不能确定该列是否是自动增加参数
 COLUMN_SIZE 列表示给定列的指定列大小。对于数值数据，这是最大精度。对于字符数据，这是字符长度。对于日期时间数据类型，这是 String 表示形式的字符长度
 （假定允许的最大小数秒组件的精度）。对于二进制数据，这是字节长度。对于 ROWID 数据类型，这是字节长度。对于列大小不适用的数据类型，则返回 Null。

 参数：
 catalog - 类别名称；它必须与存储在数据库中的类别名称匹配；该参数为 "" 表示获取没有类别的那些描述；为 null 则表示该类别名称不应该用于缩小搜索范围
 schemaPattern - 模式名称的模式；它必须与存储在数据库中的模式名称匹配；该参数为 "" 表示获取没有模式的那些描述；为 null 则表示该模式名称不应该用于缩小搜索范围
 tableNamePattern - 表名称模式；它必须与存储在数据库中的表名称匹配
 columnNamePattern - 列名称模式；它必须与存储在数据库中的列名称匹配
 */
public class PostgresCatalogStructureResolver {
    private static final String TABLE = "TABLE";
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String TABLE_CAT = "TABLE_CAT";
    private static final String TABLE_SCHEM = "TABLE_SCHEM";
    private static final String KEY_SEQ = "KEY_SEQ";
    private static final String PK_NAME = "PK_NAME";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String TYPE_NAME = "TYPE_NAME";
    private static final String COLUMN_SIZE = "COLUMN_SIZE";
    private static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";
    private static final String NULLABLE = "NULLABLE";
    private OriginalCatalogStructure orgCatalog;
    private DatabaseMetaData metaData;
    private boolean isConnected = false;

    /**
     * 默认构造方法
     */
    protected PostgresCatalogStructureResolver(OriginalCatalogStructure orgCatalog) {
        this.orgCatalog = orgCatalog;
    }

    /**
     * 连接数据库的URL、用户名、密码、驱动
     */
    protected PostgresCatalogStructureResolver(DataSource dataSource, String newUrl) {
        setDataSource(dataSource, newUrl);
    }

    /**
     * 连接数据库的URL、用户名、密码、驱动
     */
    protected PostgresCatalogStructureResolver(OriginalCatalogStructure orgCatalog, DataSource dataSource, String newUrl) {
        this.orgCatalog = orgCatalog;
        setDataSource(dataSource, newUrl);
    }


    /**
     * 获取数据库所有表结构
     * @throws SQLException must be handled
     */
    protected Map<String, OriginalTableStructure> getAllOriginalTables() throws SQLException {
        connect();
        String param1 = null, param2 = null, param3 = "%";
        switch (Configurations.getInstance().getDataSource().getDialectEnum()) {
            case POSTGRES:
                param1 = orgCatalog.getName();
                break;
            case ORACLE:
                param2 = orgCatalog.getName();
                break;
            case MYSQL:
                break;
            case SQLSERVER:
                break;
            default:
                System.out.println("未知数据库类型");
                System.exit(1);
                break;
        }
        ResultSet tableSet;
        try {
            tableSet = metaData.getTables(param1, param2, param3, new String[]{TABLE});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CatalogNotExistsException(orgCatalog.getName() + " does not exists in database!!");
        }
        while (tableSet.next()) {
            OriginalTableStructure originalTableStructure = orgCatalog.newTable();
            originalTableStructure.setOriginalCatalogStructure(orgCatalog);
            originalTableStructure.setName(tableSet.getString(TABLE_NAME));
            getPrimaryKeys(originalTableStructure);
            getColumns(originalTableStructure);
            orgCatalog.putTable(originalTableStructure);
        }
        tableSet.close();
        return orgCatalog.getOriginalTableStructureMap();
    }

    /**
     * 根据指定的表名获取表结构
     * @throws SQLException must be handled
     */
    protected OriginalTableStructure getOriginalTableByName(String tableName) throws SQLException {
        connect();
        OriginalTableStructure originalTableStructure = orgCatalog.newTable();
        originalTableStructure.setName(tableName);
        getPrimaryKeys(originalTableStructure);
        getColumns(originalTableStructure);
        return originalTableStructure;
    }


    /**
     * 获取表主键结构
     * @param originalTableStructure 当前表对象
     * @throws SQLException must be handled
     */
    private void getPrimaryKeys(OriginalTableStructure originalTableStructure) throws SQLException {
        ResultSet keySet = metaData.getPrimaryKeys(null, null, originalTableStructure.getName());
        while (keySet.next()) {
            OriginalPrimaryKeyStructure originalPrimaryKeyStructure = originalTableStructure.newPrimaryKey();
            originalPrimaryKeyStructure.setOriginalCatalogStructure(orgCatalog);
            originalPrimaryKeyStructure.setCat(keySet.getString(TABLE_CAT));
            originalPrimaryKeyStructure.setSchem(keySet.getString(TABLE_SCHEM));
            originalPrimaryKeyStructure.setTableName(keySet.getString(TABLE_NAME));
            originalPrimaryKeyStructure.setName(keySet.getString(COLUMN_NAME));
            originalPrimaryKeyStructure.setKeySeq(keySet.getInt(KEY_SEQ));
            originalPrimaryKeyStructure.setPkName(keySet.getString(PK_NAME));
            originalPrimaryKeyStructure.setOriginalTableStructure(originalTableStructure);
            originalTableStructure.putPrimaryKey(originalPrimaryKeyStructure);
        }
        keySet.close();
    }

    /**
     * 获取表主键以外结构
     * @param originalTableStructure 当前表对象
     * @throws SQLException must be handled
     */
    private void getColumns(OriginalTableStructure originalTableStructure) throws SQLException {
        ResultSet columnSet = metaData.getColumns(orgCatalog.getName(), "%", originalTableStructure.getName(), "%");
        while(columnSet.next()) {
            String columnName = columnSet.getString(COLUMN_NAME);
            OriginalColumnStructure originalColumnStructure = originalTableStructure.newColumn();
            originalColumnStructure.setName(columnName);
            originalColumnStructure.setType(columnSet.getString(TYPE_NAME));
            originalColumnStructure.setSize(columnSet.getInt(COLUMN_SIZE));
            originalColumnStructure.setDigits(columnSet.getInt(DECIMAL_DIGITS));
            originalColumnStructure.setNullable(columnSet.getInt(NULLABLE));
            originalColumnStructure.setOriginalCatalogStructure(orgCatalog);
            originalColumnStructure.setOriginalTableStructure(originalTableStructure);
            more(originalColumnStructure);
            originalTableStructure.putColumn(originalColumnStructure);
        }
        columnSet.close();
    }

    /**
     * 确定某字段为主键
     */
    private void more(OriginalColumnStructure originalColumnStructure) {
        for (Map.Entry<String, OriginalPrimaryKeyStructure> primaryKeyEntry : originalColumnStructure.getOriginalTableStructure().getOriginalPrimaryKeyStructureMap().entrySet()) {
            OriginalPrimaryKeyStructure originalPrimaryKeyStructure = primaryKeyEntry.getValue();
            if (originalPrimaryKeyStructure.getName().equals(originalColumnStructure.getName())) {
                originalColumnStructure.setPrimaryKey(true);
            }
        }
    }

    /**
     * 连接到数据库
     */
    private void connect() throws SQLException {
        if (!isConnected) {
            Connection connection = JDBCUtil.getConnection();
            if (connection == null) {
                throw new InitializeException("connection == null, jdbc connection open failed!!!");
            }
            if (orgCatalog.getName() == null || "".equals(orgCatalog.getName().trim())) {
                throw new NullPointerException("catalogs == null, catalogs has not been assigned!!!");
            }
            metaData = connection.getMetaData();
            isConnected = true;
        }
    }


    /**
     * 设置连接属性
     */
    protected void setDataSource(DataSource dataSource, String newUrl) {
        JDBCUtil.setDateSource(newUrl, dataSource.getUser(), dataSource.getPassword());
        JDBCUtil.setDriver(dataSource.getDriver());
    }


    public OriginalCatalogStructure getOrgCatalog() {
        return orgCatalog;
    }

    public void setOrgCatalog(OriginalCatalogStructure orgCatalog) {
        this.orgCatalog = orgCatalog;
    }
}