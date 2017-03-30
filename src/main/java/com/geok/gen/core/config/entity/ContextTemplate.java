package com.geok.gen.core.config.entity;

/**
 * Created by Stephen on 2017/3/24.
 */
public class ContextTemplate {
    private String id;
    private String targetProAbsClassPath;
    private String vmTplName;
    private String targetPackage;
    private String suffix = "";

    public String getTargetProAbsClassPath() {
        return targetProAbsClassPath;
    }

    public void setTargetProAbsClassPath(String targetProAbsClassPath) {
        this.targetProAbsClassPath = targetProAbsClassPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVmTplName() {
        return vmTplName;
    }

    public void setVmTplName(String vmTplName) {
        this.vmTplName = vmTplName;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public String toString() {
        return "ContextTemplate{" +
                "id='" + id + '\'' +
                ", targetProAbsClassPath='" + targetProAbsClassPath + '\'' +
                ", vmTplName='" + vmTplName + '\'' +
                ", targetPackage='" + targetPackage + '\'' +
                ", suffix='" + suffix + '\'' +
                '}';
    }
}