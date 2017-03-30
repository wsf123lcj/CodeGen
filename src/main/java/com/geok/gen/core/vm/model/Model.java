package com.geok.gen.core.vm.model;

import java.util.*;

/**
 * Created by Stephen on 2017/3/23.
 * 封装需要动态呈现在模板的数据
 */
public class Model {
    private String templateId;
    private String orgDbName;
    private String fileName;
    private String javaTableName;
    private String javaTableVarName;
    private String classNameSuffix;
    private String classVarNameSuffix;
    private String packagePath;
    private String classPackagePath;
    private String fileAbsDir;
    private String fileAbsPath;
    private String vmDir;
    private String vmName;
    private String orgTableName;
    private String generateDate;
    private String author;
    private List<ModelField> modelFieldList = new ArrayList<>();
    private Set<String> importPackages = new HashSet<>();
    private List<ModelPrimaryKey> modelPrimaryKeys = new ArrayList<>();
    //<template/>中suffix属性的映射，键:id；值:suffix
    private Map<String, String> suffixMap = new HashMap<>();
    private Map<String, ModelPrimaryKey> uniqueModelPrimaryKeyMap = new HashMap<>();

    public ModelPrimaryKey newModelPrimaryKey() {
        return new ModelPrimaryKey();
    }

    public ModelField newModelField() {
        return new ModelField();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getOrgDbName() {
        return orgDbName;
    }

    public void setOrgDbName(String orgDbName) {
        this.orgDbName = orgDbName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getJavaTableName() {
        return javaTableName;
    }

    public void setJavaTableName(String javaTableName) {
        this.javaTableName = javaTableName;
    }

    public String getJavaTableVarName() {
        return javaTableVarName;
    }

    public void setJavaTableVarName(String javaTableVarName) {
        this.javaTableVarName = javaTableVarName;
    }

    public String getClassNameSuffix() {
        return classNameSuffix;
    }

    public void setClassNameSuffix(String classNameSuffix) {
        this.classNameSuffix = classNameSuffix;
    }

    public String getClassVarNameSuffix() {
        return classVarNameSuffix;
    }

    public void setClassVarNameSuffix(String classVarNameSuffix) {
        this.classVarNameSuffix = classVarNameSuffix;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getClassPackagePath() {
        return classPackagePath;
    }

    public void setClassPackagePath(String classPackagePath) {
        this.classPackagePath = classPackagePath;
    }

    public String getFileAbsDir() {
        return fileAbsDir;
    }

    public void setFileAbsDir(String fileAbsDir) {
        this.fileAbsDir = fileAbsDir;
    }

    public String getFileAbsPath() {
        return fileAbsPath;
    }

    public void setFileAbsPath(String fileAbsPath) {
        this.fileAbsPath = fileAbsPath;
    }

    public String getVmDir() {
        return vmDir;
    }

    public void setVmDir(String vmDir) {
        this.vmDir = vmDir;
    }

    public String getVmName() {
        return vmName;
    }

    public void setVmName(String vmName) {
        this.vmName = vmName;
    }

    public String getOrgTableName() {
        return orgTableName;
    }

    public void setOrgTableName(String orgTableName) {
        this.orgTableName = orgTableName;
    }

    public String getGenerateDate() {
        return generateDate;
    }

    public void setGenerateDate(String generateDate) {
        this.generateDate = generateDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<ModelField> getModelFieldList() {
        return modelFieldList;
    }

    public Set<String> getImportPackages() {
        return importPackages;
    }

    public List<ModelPrimaryKey> getModelPrimaryKeys() {
        return modelPrimaryKeys;
    }

    public Map<String, String> getSuffixMap() {
        return suffixMap;
    }


    public Map<String, ModelPrimaryKey> getUniqueModelPrimaryKeyMap() {
        return uniqueModelPrimaryKeyMap;
    }

    @Override
    public String toString() {
        return "Model{" +
                "templateId='" + templateId + '\'' +
                ", orgDbName='" + orgDbName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", javaTableName='" + javaTableName + '\'' +
                ", javaTableVarName='" + javaTableVarName + '\'' +
                ", classNameSuffix='" + classNameSuffix + '\'' +
                ", classVarNameSuffix='" + classVarNameSuffix + '\'' +
                ", packagePath='" + packagePath + '\'' +
                ", classPackagePath='" + classPackagePath + '\'' +
                ", fileAbsDir='" + fileAbsDir + '\'' +
                ", fileAbsPath='" + fileAbsPath + '\'' +
                ", vmDir='" + vmDir + '\'' +
                ", vmName='" + vmName + '\'' +
                ", orgTableName='" + orgTableName + '\'' +
                ", generateDate='" + generateDate + '\'' +
                ", author='" + author + '\'' +
                ", modelFieldList=" + modelFieldList +
                ", importPackages=" + importPackages +
                ", modelPrimaryKeys=" + modelPrimaryKeys +
                ", suffixMap=" + suffixMap +
                ", uniqueModelPrimaryKeyMap=" + uniqueModelPrimaryKeyMap +
                '}';
    }
}