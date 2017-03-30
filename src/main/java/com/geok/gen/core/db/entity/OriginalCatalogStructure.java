package com.geok.gen.core.db.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stephen on 2017/3/22.
 * 数据库实体类
 */
public class OriginalCatalogStructure {
    private String name;
    private Map<String, OriginalTableStructure> originalTableStructureMap = new HashMap<>();

    public OriginalTableStructure newTable() {
        return new OriginalTableStructure();
    }

    public void putTable(OriginalTableStructure originalTableStructure) {
        originalTableStructureMap.put(originalTableStructure.getName(), originalTableStructure);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, OriginalTableStructure> getOriginalTableStructureMap() {
        return originalTableStructureMap;
    }
}
