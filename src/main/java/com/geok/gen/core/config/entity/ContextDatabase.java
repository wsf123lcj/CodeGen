package com.geok.gen.core.config.entity;

import java.util.List;

/**
 * Created by Stephen on 2017/3/24.
 */
public class ContextDatabase {
    private String nativeDbName;
    private Boolean camelCaseName = true;
    private List<ContextTable> contextTables;

    public String getNativeDbName() {
        return nativeDbName;
    }

    public void setNativeDbName(String nativeDbName) {
        this.nativeDbName = nativeDbName;
    }

    public Boolean getCamelCaseName() {
        return camelCaseName;
    }

    public void setCamelCaseName(Boolean camelCaseName) {
        this.camelCaseName = camelCaseName;
    }

    public List<ContextTable> getContextTables() {
        return contextTables;
    }

    public void setContextTables(List<ContextTable> contextTables) {
        this.contextTables = contextTables;
    }

    @Override
    public String toString() {
        return "ContextDatabase{" +
                "nativeDbName='" + nativeDbName + '\'' +
                ", camelCaseName=" + camelCaseName +
                ", contextTables=" + contextTables +
                '}';
    }
}