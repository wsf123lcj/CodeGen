package com.geok.gen.core.vm.model;

/**
 * Created by Stephen on 2017/3/23.
 * 封装模板字段有关数据
 */
public class ModelField {
    private String fieldName;
    private String fieldSetterGetter;
    private String fieldType;
    private String fieldTypeForSimple;
    private boolean isArray;
    private String arraySuffix = "";

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldSetterGetter() {
        return fieldSetterGetter;
    }

    public void setFieldSetterGetter(String fieldSetterGetter) {
        this.fieldSetterGetter = fieldSetterGetter;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldTypeForSimple() {
        return fieldTypeForSimple;
    }

    public void setFieldTypeForSimple(String fieldTypeForSimple) {
        this.fieldTypeForSimple = fieldTypeForSimple;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public String getArraySuffix() {
        return arraySuffix;
    }

    public void setArraySuffix(String arraySuffix) {
        this.arraySuffix = arraySuffix;
    }

    @Override
    public String toString() {
        return "ModelField{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldSetterGetter='" + fieldSetterGetter + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", fieldTypeForSimple='" + fieldTypeForSimple + '\'' +
                ", isArray=" + isArray +
                ", arraySuffix='" + arraySuffix + '\'' +
                '}';
    }
}
