package com.geok.gen.core.vm.assist;

import com.geok.gen.core.config.entity.*;
import com.geok.gen.core.db.entity.OriginalColumnStructure;
import com.geok.gen.core.db.entity.OriginalPrimaryKeyStructure;
import com.geok.gen.core.db.entity.OriginalTableStructure;
import com.geok.gen.core.db.resolver.DatabaseTypeResolver;
import com.geok.gen.util.StringUtil;
import com.geok.gen.core.vm.model.Model;
import com.geok.gen.core.vm.model.ModelField;
import com.geok.gen.core.vm.model.ModelPrimaryKey;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Stephen on 2017/3/23.
 * 辅助生成模板文件需要的模板类
 */
public class ModelMaker {
    private static final String FILE_SUFFIX = ".java";
    private static final String BIG_DECIMAL = "BigDecimal";
    private static final String TIMESTAMP = "Timestamp";
    private static final String TIME = "Time";
    private static final String DATE = "Date";
    private static final String CLOB = "Clob";
    private static final String BLOB = "Blob";
    private static final String ROWID = "RowId";
    private static final String NCLOB = "NClob";
    private static final String RESULTSET = "ResultSet";

    /**
     * 整合数据库结构与用户配置为模型对象
     * @param oTable 封装数据库表的结构信息
     * @return 模型对象
     */
    public Model make(OriginalTableStructure oTable, ContextDatabase cDatabase, ContextTable cTable, ContextTemplate cTemplate) {
        Context context = Configurations.getInstance().getContext();
        Model model = new Model();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String tableName = oTable.getName();
        String javaTableName;
        if (cTable != null) {
            javaTableName = cTable.getEntityName();
        } else {
            javaTableName = null;
        }
        if (StringUtil.isEmpty(javaTableName)) {
            if (cDatabase.getCamelCaseName()) {
                javaTableName = StringUtil.firstUpperCaseCamelFromDB(tableName);
            } else {
                javaTableName = StringUtil.firstUpperCase(tableName);
            }
        }
        String className = javaTableName + cTemplate.getSuffix();
        String classVarName = StringUtil.firstLowerCase(className);
        String packagePath = cTemplate.getTargetPackage();
        String targetProClassPath = cTemplate.getTargetProAbsClassPath();
        if (!targetProClassPath.endsWith("\\")) {
            targetProClassPath += "\\";
        }
        String fileName = className + FILE_SUFFIX;
        String dir = targetProClassPath + convertPackage2path(packagePath);
        String fileAbsPath = dir + "\\" + fileName;
        //公用
        model.setTemplateId(cTemplate.getId());
        model.setJavaTableName(javaTableName);
        model.setJavaTableVarName(StringUtil.firstLowerCase(javaTableName));
        model.setOrgDbName(cDatabase.getNativeDbName());
        model.setAuthor(context.getAuthor());
        model.setOrgTableName(oTable.getName());
        model.setGenerateDate(date);
        column2field(model, oTable.getOriginalColumnStructureMap());
        primaryKey2pKey(model, oTable.getOriginalPrimaryKeyStructureMap());
        model.setClassNameSuffix(className);
        model.setClassVarNameSuffix(classVarName);
        model.setFileName(fileName);
        model.setPackagePath(packagePath);
        model.setClassPackagePath(packagePath + "." + className);
        model.setFileAbsDir(dir);
        model.setFileAbsPath(fileAbsPath);
        makePojoImportPackages(model);
        model.getSuffixMap().putAll(context.getSuffixMap());
        model.setVmName(cTemplate.getVmTplName());
        makeUniqueModelPrimaryKeyMap(model);
//        System.out.println(model);
        return model;
    }

    /**
     * 封装模型主键
     */
    private List<ModelPrimaryKey> primaryKey2pKey(Model model, Map<String, OriginalPrimaryKeyStructure> originalPrimaryKeyStructureMap) {
        List<ModelPrimaryKey> modelPrimaryKeys = model.getModelPrimaryKeys();
        for (Map.Entry<String, OriginalPrimaryKeyStructure> primaryKeyEntry: originalPrimaryKeyStructureMap.entrySet()) {
            ModelPrimaryKey modelPrimaryKey = new ModelPrimaryKey();
            modelPrimaryKey.setpKeyName(StringUtil.firstLowerCaseCamelFromDB(primaryKeyEntry.getValue().getName()));
            String srcType = primaryKeyEntry.getValue().getOriginalTableStructure().getOriginalColumnStructureMap().get(primaryKeyEntry.getValue().getName()).getType();
            String javaType = getPrimaryKeyJavaType(primaryKeyEntry.getValue());
            if (javaType == null) {
                continue;
            }
            modelPrimaryKey.setpKeyJavaType(javaType);
            modelPrimaryKey.setpKeyJavaTypeForSimple(javaType.substring(javaType.lastIndexOf(".") + 1));
            modelPrimaryKey.setpKeyType(srcType);
            modelPrimaryKeys.add(modelPrimaryKey);
        }
        return modelPrimaryKeys;
    }

    private List<ModelField> column2field(Model model, Map<String, OriginalColumnStructure> originalColumnStructureMap) {
        List<ModelField> modelFields = model.getModelFieldList();
        for (Map.Entry<String, OriginalColumnStructure> columnEntry : originalColumnStructureMap.entrySet()) {
            ModelField modelField = new ModelField();
            modelField.setFieldName(StringUtil.firstLowerCaseCamelFromDB(columnEntry.getValue().getName()));
            modelField.setFieldSetterGetter(StringUtil.firstUpperCaseCamelFromDB(modelField.getFieldName()));
            String javaType = getColumnJavaType(columnEntry.getValue());
            if (javaType == null) {
                continue;
            }
            modelField.setFieldType(javaType);
            modelField.setFieldTypeForSimple(javaType.substring(javaType.lastIndexOf(".") + 1));
            boolean isArray = columnEntry.getValue().isArray();
            modelField.setArray(isArray);
            if (isArray) {
                modelField.setArraySuffix("[]");
            }
            modelFields.add(modelField);
        }
        return modelFields;
    }

    private String getPrimaryKeyJavaType(OriginalPrimaryKeyStructure opks) {
        DatabaseTypeResolver dtResolver = DatabaseTypeResolver.getInstance();
        return dtResolver.resolvePrimaryKey(opks);
    }

    private String getColumnJavaType(OriginalColumnStructure ocs) {
        DatabaseTypeResolver dtResolver = DatabaseTypeResolver.getInstance();
        return dtResolver.resolveColumn(ocs);
    }

    /**
     * 根据包名生成文件的绝对路径
     */
    private String makeFilePath(String pkg, String fileName) {
        String projectPath = System.getProperty("user.dir");
        String absFilePath = projectPath + "\\src\\" + convertPackage2path(pkg);
        return absFilePath + fileName;
    }

    /**
     * 包路径转为文件路径
     */
    private String convertPackage2path(String pkg) {
        final String SYMBOL = ".";
        StringBuilder sb = new StringBuilder();
        StringTokenizer tokenizer = new StringTokenizer(pkg, SYMBOL);
        int count = tokenizer.countTokens();
        int start = 0;
        while (tokenizer.hasMoreElements()) {
            sb.append(tokenizer.nextToken());
            start++;
            if (start < count) {
                sb.append("\\");
            }
        }
        return sb.toString();
    }

    private Map<String, ModelPrimaryKey> makeUniqueModelPrimaryKeyMap(Model model) {
        Map<String, ModelPrimaryKey> uniqueMap = model.getUniqueModelPrimaryKeyMap();
        for (ModelPrimaryKey primaryKey : model.getModelPrimaryKeys()) {
            if (!uniqueMap.containsKey(primaryKey.getpKeyJavaType())) {
                uniqueMap.put(primaryKey.getpKeyJavaType(), primaryKey);
            }
        }
        return uniqueMap;
    }


    /**
     * 生成pojo需要的包集合
     */
    private Set<String> makePojoImportPackages(Model model) {
        Set<String> importPackages = model.getImportPackages();
        for (ModelField modelField : model.getModelFieldList()) {
            String simpleType = modelField.getFieldTypeForSimple();
            if (BIG_DECIMAL.equals(simpleType)) {
                importPackages.add(modelField.getFieldType());
            }
            if (TIMESTAMP.equals(simpleType)) {
                importPackages.add(modelField.getFieldType());
            }
            if (TIME.equals(simpleType)) {
                importPackages.add(modelField.getFieldType());
            }
            if (DATE.equals(simpleType)) {
                importPackages.add(modelField.getFieldType());
            }
            if (CLOB.equals(simpleType)) {
                importPackages.add(modelField.getFieldType());
            }
            if (BLOB.equals(simpleType)) {
                importPackages.add(modelField.getFieldType());
            }
            if (ROWID.equals(simpleType)) {
                importPackages.add(modelField.getFieldType());
            }
            if (NCLOB.equals(simpleType)) {
                importPackages.add(modelField.getFieldType());
            }
            if (RESULTSET.equals(simpleType)) {
                importPackages.add(modelField.getFieldType());
            }
        }
        return importPackages;
    }
}