package com.geok;

import com.geok.gen.core.config.entity.*;
import com.geok.gen.core.vm.ModelGroup;
import com.geok.gen.core.config.reader.ClassPathXMLContext;
import com.geok.gen.core.db.entity.OriginalTableStructure;
import com.geok.gen.core.db.resolver.PostgresDatabaseStructureResolver;
import com.geok.gen.util.FileUtil;
import com.geok.gen.util.PathUtil;
import com.geok.gen.core.vm.model.Model;
import com.geok.gen.core.vm.assist.ModelMaker;
import com.geok.gen.core.vm.assist.VMEngine;
import com.geok.gen.core.vm.assist.VmFileResolver;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Stephen on 2017/3/22.
 * 代码生成器
 */
public class GeneratorExecutor {
    private ClassPathXMLContext xmlContext;
    public static final String DEBUG_UNIVERSAL_DIR = "D:\\Work\\idea_j2ee\\generator\\out\\artifacts\\generator_jar";
    public static final String DEBUG_UNIVERSAL_XML = "configuration.xml";
    public static final boolean DEBUG = false;

    public GeneratorExecutor(String configXMLName) {
        xmlContext = new ClassPathXMLContext(configXMLName);
    }

    public void generate() {
        System.out.println("------ start running ------");
        Configurations configurations = xmlContext.parse();
        DataSource.DialectEnum dialectEnum = configurations.getDataSource().getDialectEnum();
        System.out.println("...");
        System.out.println("...generating " + dialectEnum + " java code...");
        System.out.println("...");
        switch (dialectEnum) {
            case POSTGRES:
                generateCode(configurations);
                break;
            case ORACLE:
                generateCode(configurations);
                break;
            default:
                throw new UnsupportedOperationException("暂不支持除postgres与oracle以外的数据库!!!");
        }
    }

    private void generateCode(Configurations configurations) {
        List<ModelGroup> modelGroups = new ArrayList<>();
        ModelMaker modelMaker = new ModelMaker();
        List<Model> models = new ArrayList<>();
        List<ContextDatabase> contextDatabases = configurations.getContext().getContextDatabases();
        for (ContextDatabase contextDatabase : contextDatabases) {
            PostgresDatabaseStructureResolver resolver = PostgresDatabaseStructureResolver.newInstance(configurations.getDataSource(), contextDatabase);
            List<ContextTable> contextTables = contextDatabase.getContextTables();
            if (contextTables == null || contextTables.size() == 0) {
                try {
                    Map<String, OriginalTableStructure> originalTableStructureMap = resolver.getAllTableStructure();
                    for (Map.Entry<String, OriginalTableStructure> oTableEntry : originalTableStructureMap.entrySet()) {
                        ModelGroup modelGroup = new ModelGroup();
                        for (ContextTemplate template : configurations.getContext().getContextTemplates()) {
                            Model model = modelMaker.make(oTableEntry.getValue(), contextDatabase, null, template);
                            models.add(model);
                            modelGroup.put(model);
                        }
                        modelGroups.add(modelGroup);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                for (ContextTable contextTable : contextTables) {
                    try {
                        ModelGroup modelGroup = new ModelGroup();
                        OriginalTableStructure oTable = resolver.getTableStructure(contextTable.getNativeTableName());
                        for (ContextTemplate template : configurations.getContext().getContextTemplates()) {
                            Model model = modelMaker.make(oTable, contextDatabase, contextTable, template);
                            models.add(model);
                            modelGroup.put(model);
                        }
                        modelGroups.add(modelGroup);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        for (ModelGroup modelGroup : modelGroups) {
            try {
                confirmImportPackages(modelGroup);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        VMEngine vmEngine;
        if (DEBUG) {
            vmEngine = new VMEngine(DEBUG_UNIVERSAL_DIR);
        } else {
            vmEngine = new VMEngine(PathUtil.getProjectPath());
        }
        for (Model model : models) {
            StringWriter writer = vmEngine.templateMergeModel(model);
            FileUtil.write(model, writer);
        }
        System.out.println("------ file generation finished ------");
    }

    /**
     * 确认每个模板需要import的包
     * @param modelGroup 一个表对应的一组模板
     * @throws UnsupportedEncodingException
     */
    private void confirmImportPackages(ModelGroup modelGroup) throws UnsupportedEncodingException {
        List<VmFileResolver> vmFileResolvers = new ArrayList<>();
        List<ContextTemplate> contextTemplates = xmlContext.getConfigurations().getContext().getContextTemplates();
        for (ContextTemplate cTemplate : contextTemplates) {
            String dir;
            if (GeneratorExecutor.DEBUG) {
                dir = DEBUG_UNIVERSAL_DIR;
            } else {
                dir = PathUtil.getProjectPath();
            }
            VmFileResolver vmFileResolver = new VmFileResolver(dir + "\\" + cTemplate.getVmTplName());
            vmFileResolver.setTemplateId(cTemplate.getId());
            vmFileResolver.setSuffix(cTemplate.getSuffix());
            vmFileResolvers.add(vmFileResolver);
        }
        Map<String, Model> modelMap = modelGroup.getModelMap();
        for (Map.Entry<String, Model> entry : modelMap.entrySet()) {
            for (VmFileResolver resolver : vmFileResolvers) {
                String regex = "${model.suffixMap.get(\"" + entry.getValue().getTemplateId() + "\")}";
                if (resolver.contains(regex)) {
                    Model model1 = entry.getValue();
                    String key = modelGroup.getCommonPart() + resolver.getSuffix();
                    Model model2 = modelMap.get(key);
                    if (model2 != null && model1 != model2 && !model1.getPackagePath().equals(model2.getPackagePath())) {
                        model2.getImportPackages().add(model1.getClassPackagePath());
                    }
                }
            }
        }
    }



    public static void main(String[] args) {
        new GeneratorExecutor("configuration.xml").generate();
    }
}