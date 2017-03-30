package com.geok.gen.core.vm;

import com.geok.gen.core.vm.model.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stephen on 2017/3/27.
 *
 */
public class Package {
    private Map<Model, OriginAndConfig> modelOriginAndConfigMap = new HashMap<>();

    public Map<Model, OriginAndConfig> getModelOriginAndConfigMap() {
        return modelOriginAndConfigMap;
    }

    public void setModelOriginAndConfigMap(Map<Model, OriginAndConfig> modelOriginAndConfigMap) {
        this.modelOriginAndConfigMap = modelOriginAndConfigMap;
    }

    @Override
    public String toString() {
        return "Package{" +
                "modelOriginAndConfigMap=" + modelOriginAndConfigMap +
                '}';
    }
}