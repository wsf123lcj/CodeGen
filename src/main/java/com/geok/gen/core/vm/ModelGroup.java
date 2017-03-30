package com.geok.gen.core.vm;

import com.geok.gen.core.vm.model.Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stephen on 2017/3/27.
 *
 */
public class ModelGroup {
    private String commonPart;
    private Map<String, Model> modelMap = new HashMap<>();

    public void put(Model model) {
        modelMap.put(model.getClassNameSuffix(), model);
        commonPart = model.getJavaTableName();
    }

    public String getCommonPart() {
        return commonPart;
    }

    public void setCommonPart(String commonPart) {
        this.commonPart = commonPart;
    }

    public Map<String, Model> getModelMap() {
        return modelMap;
    }

    @Override
    public String toString() {
        return "ModelGroup{" +
                "commonPart='" + commonPart + '\'' +
                ", modelMap=" + modelMap +
                '}';
    }
}