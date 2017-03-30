package com.geok.gen.core.vm.model;

/**
 * Created by Stephen on 2017/3/23.
 * 模型层的主键实体类
 */
public class ModelPrimaryKey {
    private String pKeyName;
    private String pKeyType;
    private String pKeyJavaType;
    private String pKeyJavaTypeForSimple;

    public String getpKeyName() {
        return pKeyName;
    }

    public void setpKeyName(String pKeyName) {
        this.pKeyName = pKeyName;
    }

    public String getpKeyType() {
        return pKeyType;
    }

    public void setpKeyType(String pKeyType) {
        this.pKeyType = pKeyType;
    }

    public String getpKeyJavaType() {
        return pKeyJavaType;
    }

    public void setpKeyJavaType(String pKeyJavaType) {
        this.pKeyJavaType = pKeyJavaType;
    }

    public String getpKeyJavaTypeForSimple() {
        return pKeyJavaTypeForSimple;
    }

    public void setpKeyJavaTypeForSimple(String pKeyJavaTypeForSimple) {
        this.pKeyJavaTypeForSimple = pKeyJavaTypeForSimple;
    }

    @Override
    public String toString() {
        return "ModelPrimaryKey{" +
                "pKeyName='" + pKeyName + '\'' +
                ", pKeyType='" + pKeyType + '\'' +
                ", pKeyJavaType='" + pKeyJavaType + '\'' +
                ", pKeyJavaTypeForSimple='" + pKeyJavaTypeForSimple + '\'' +
                '}';
    }
}
