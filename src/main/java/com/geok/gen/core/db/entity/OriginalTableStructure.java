package com.geok.gen.core.db.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stephen on 2017/3/22.
 * 表实体类
 */
public class OriginalTableStructure {
    private OriginalCatalogStructure originalCatalogStructure;
    private String name;
    private Map<String, OriginalPrimaryKeyStructure> originalPrimaryKeyStructureMap = new HashMap<>();
    private Map<String, OriginalColumnStructure> originalColumnStructureMap = new HashMap<>();

    public OriginalPrimaryKeyStructure newPrimaryKey() {
        return new OriginalPrimaryKeyStructure();
    }

    public OriginalColumnStructure newColumn() {
        return new OriginalColumnStructure();
    }

    public boolean isPrimaryKey(String columnName) {
        return originalPrimaryKeyStructureMap.containsKey(columnName);
    }

    public String getPrimaryKeyType(String primaryKeyName) {
        for (Map.Entry<String, OriginalColumnStructure> entry : originalColumnStructureMap.entrySet()) {
            if (entry.getValue().getName().equals(primaryKeyName)) {
                return entry.getValue().getType();
            }
        }
        return null;
    }

    public void putPrimaryKey(OriginalPrimaryKeyStructure originalPrimaryKeyStructure) {
        originalPrimaryKeyStructureMap.put(originalPrimaryKeyStructure.getName(), originalPrimaryKeyStructure);
    }

    public void putColumn(OriginalColumnStructure originalColumnStructure) {
        originalColumnStructureMap.put(originalColumnStructure.getName(), originalColumnStructure);
    }

    public OriginalCatalogStructure getOriginalCatalogStructure() {
        return originalCatalogStructure;
    }

    public void setOriginalCatalogStructure(OriginalCatalogStructure originalCatalogStructure) {
        this.originalCatalogStructure = originalCatalogStructure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, OriginalPrimaryKeyStructure> getOriginalPrimaryKeyStructureMap() {
        return originalPrimaryKeyStructureMap;
    }

    public Map<String, OriginalColumnStructure> getOriginalColumnStructureMap() {
        return originalColumnStructureMap;
    }


    @Override
    public String toString() {
        return "OriginalTableStructure{" +
                ", name='" + name + '\'' +
                ", originalPrimaryKeyStructureMap=" + originalPrimaryKeyStructureMap +
                ", originalColumnStructureMap=" + originalColumnStructureMap +
                '}';
    }
}
