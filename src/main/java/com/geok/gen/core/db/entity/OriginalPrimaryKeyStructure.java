package com.geok.gen.core.db.entity;

/**
 * Created by Stephen on 2017/3/22.
 * JDBC查询出来的主键结果集一般有6列：
 * TABLE_CAT 表目录一般会是NULL
 * TABLE_SCHEM 表索引,主键所在表的架构,通常会设置为dbmd.getUserName()
 * TABLE_NAME 包含主键的表名称
 * COLUMN_NAME 主键名称
 * KEY_SEQ 主键序列
 * PK_NAME 约束名称
 */
public class OriginalPrimaryKeyStructure {
    private OriginalCatalogStructure originalCatalogStructure;
    private OriginalTableStructure originalTableStructure;
    private String cat;
    private String schem;
    private String tableName;
    private String name;
    private Integer keySeq;
    private String pkName;

    public OriginalCatalogStructure getOriginalCatalogStructure() {
        return originalCatalogStructure;
    }

    public void setOriginalCatalogStructure(OriginalCatalogStructure originalCatalogStructure) {
        this.originalCatalogStructure = originalCatalogStructure;
    }

    public OriginalTableStructure getOriginalTableStructure() {
        return originalTableStructure;
    }

    public void setOriginalTableStructure(OriginalTableStructure originalTableStructure) {
        this.originalTableStructure = originalTableStructure;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getSchem() {
        return schem;
    }

    public void setSchem(String schem) {
        this.schem = schem;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getKeySeq() {
        return keySeq;
    }

    public void setKeySeq(Integer keySeq) {
        this.keySeq = keySeq;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    @Override
    public String toString() {
        return "OriginalPrimaryKeyStructure{" +
                ", cat='" + cat + '\'' +
                ", schem='" + schem + '\'' +
                ", tableName='" + tableName + '\'' +
                ", name='" + name + '\'' +
                ", keySeq=" + keySeq +
                ", pkName='" + pkName + '\'' +
                '}';
    }
}