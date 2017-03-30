package com.geok.gen.core.db.entity;

/**
 * Created by Stephen on 2017/3/22.
 * 列实体类
 */
public class OriginalColumnStructure {
    private OriginalCatalogStructure originalCatalogStructure;
    private OriginalTableStructure originalTableStructure;
    private String name;
    private String type;
    private Integer size;
    private Integer digits;
    private Integer nullable;
    private boolean isPrimaryKey = false;
    private boolean isArray = false;
    private boolean hasBeenSettled = false;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getDigits() {
        return digits;
    }

    public void setDigits(Integer digits) {
        this.digits = digits;
    }

    public Integer getNullable() {
        return nullable;
    }

    public void setNullable(Integer nullable) {
        this.nullable = nullable;
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public boolean isHasBeenSettled() {
        return hasBeenSettled;
    }

    public void setHasBeenSettled(boolean hasBeenSettled) {
        this.hasBeenSettled = hasBeenSettled;
    }

    @Override
    public String toString() {
        return "OriginalColumnStructure{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size=" + size +
                ", digits=" + digits +
                ", nullable=" + nullable +
                ", isPrimaryKey=" + isPrimaryKey +
                ", isArray=" + isArray +
                ", hasBeenSettled=" + hasBeenSettled +
                '}';
    }
}
