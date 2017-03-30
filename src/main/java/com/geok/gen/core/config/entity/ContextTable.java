package com.geok.gen.core.config.entity;

/**
 * Created by Stephen on 2017/3/24.
 */
public class ContextTable {

    private String nativeTableName;
    private String entityName;

    public String getNativeTableName() {
        return nativeTableName;
    }

    public void setNativeTableName(String nativeTableName) {
        this.nativeTableName = nativeTableName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String toString() {
        return "ContextTable{" +
                "nativeTableName='" + nativeTableName + '\'' +
                ", entityName='" + entityName + '\'' +
                '}';
    }
}