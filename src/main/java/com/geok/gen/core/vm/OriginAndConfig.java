package com.geok.gen.core.vm;

import com.geok.gen.core.config.entity.ContextTable;
import com.geok.gen.core.db.entity.OriginalTableStructure;

/**
 * Created by Stephen on 2017/3/27.
 */
public class OriginAndConfig {
    private OriginalTableStructure originalTableStructure;
    private ContextTable contextTable;

    public OriginalTableStructure getOriginalTableStructure() {
        return originalTableStructure;
    }

    public void setOriginalTableStructure(OriginalTableStructure originalTableStructure) {
        this.originalTableStructure = originalTableStructure;
    }

    public ContextTable getContextTable() {
        return contextTable;
    }

    public void setContextTable(ContextTable contextTable) {
        this.contextTable = contextTable;
    }

    @Override
    public String toString() {
        return "OriginAndConfig{" +
                "originalTableStructure=" + originalTableStructure +
                ", contextTable=" + contextTable +
                '}';
    }
}