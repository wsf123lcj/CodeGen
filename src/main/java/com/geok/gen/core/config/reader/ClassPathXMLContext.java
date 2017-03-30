package com.geok.gen.core.config.reader;

import com.geok.gen.core.config.entity.*;
import com.geok.gen.core.vm.Package;
import com.geok.GeneratorExecutor;
import com.geok.gen.util.PathUtil;
import com.geok.gen.util.StringUtil;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.geok.gen.exception.ConfigMissingException;
import com.geok.gen.exception.XMLFormatException;
import com.geok.gen.util.FieldReflector;

import java.util.*;

import static com.geok.GeneratorExecutor.DEBUG_UNIVERSAL_DIR;
import static com.geok.GeneratorExecutor.DEBUG_UNIVERSAL_XML;

/**
 * Created by sifanW on 2017/3/22.
 * xml配置文件解析器
 */
public class ClassPathXMLContext {
    private static final String GENERATOR = "generator";
    private static final String DATASOURCE = "dataSource";
    private static final String CONTEXT = "context";
    private static final String TEMPLATES = "templates";
    private static final String TEMPLATE = "template";
    private static final String DB_SET = "db-set";
    private static final String DB = "db";
    private static final String TABLE = "table";
    private Element rootElement;
    private Configurations configurations;
    private Map<String, Package> packageMap = new HashMap<>();

    public ClassPathXMLContext(String configXMLName) {
        try {
            parseXML2Document(configXMLName);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        configurations = Configurations.getInstance();
    }

    /**
     * 运行解析器，进行文件解析，返回配置对象
     */
    public Configurations parse() {
        parseDataSourceConfig();
        parseContextConfig();
        return configurations;
    }

    /**
     * 解析dataSource的配置信息
     */
    private void parseDataSourceConfig() {
        DataSource dataSource = configurations.getDataSource();
        Element dataSourceElement = rootElement.element(DATASOURCE);
        throwMissingException(dataSourceElement, DATASOURCE);

        Element dialectElement = dataSourceElement.element(DataSource.DIALECT);
        throwMissingException(dialectElement, DataSource.DIALECT);
        dataSource.setDialectEnum(DataSource.DialectEnum.parse(dialectElement.getText()));

        Element userElement = dataSourceElement.element(DataSource.USER);
        throwMissingException(userElement, DataSource.USER);
        dataSource.setUser(userElement.getText());

        Element passwordElement = dataSourceElement.element(DataSource.PASSWORD);
        throwMissingException(passwordElement, DataSource.PASSWORD);
        dataSource.setPassword(passwordElement.getText());

        Element urlElement = dataSourceElement.element(DataSource.URL);
        throwMissingException(urlElement, DataSource.URL);
        String url = urlElement.getText();
        switch (dataSource.getDialectEnum()) {
            case POSTGRES:
                StringUtil.generateUrlPattern(url);
                break;
            case ORACLE:
                break;
                default:
        }
        dataSource.setUrl(url);

        Element driverElement = dataSourceElement.element(DataSource.DRIVER);
        throwMissingException(driverElement, DataSource.DRIVER);
        dataSource.setDriver(driverElement.getText());

    }

    /**
     * 解析context元素的配置信息
     */
    private void parseContextConfig() {
        //解析<config/>标签属性
        Element contextElement = rootElement.element(CONTEXT);
        throwMissingException(contextElement, CONTEXT);
        Context context = configurations.getContext();
        Map<String, String> contextAttrMap = new HashMap<>();
        Iterator<Attribute> attrIterator = contextElement.attributeIterator();
        while (attrIterator.hasNext()) {
            Attribute attribute = attrIterator.next();
            contextAttrMap.put(attribute.getName(), attribute.getValue());
        }
        FieldReflector<Context> reflector = new FieldReflector<>(Context.class);
        context = reflector.doReflect(contextAttrMap, context);
        context.setContextTemplates(parseContextTemplates(contextElement));
        context.setContextDatabases(parseContextDatabases(contextElement));
    }

    /**
     * 解析templates元素配置
     */
    private List<ContextTemplate> parseContextTemplates(Element contextElement) {
        Element templatesElement = contextElement.element(TEMPLATES);
        throwMissingException(templatesElement, TEMPLATES);
        List<Element> templateElements = templatesElement.elements(TEMPLATE);
        if (templateElements == null || templateElements.size() == 0) {
            System.exit(0);
        }
        FieldReflector<ContextTemplate> reflector = new FieldReflector<>(ContextTemplate.class);
        List<ContextTemplate> contextTemplates = new ArrayList<>();
        for (Element element : templateElements) {
            ContextTemplate contextTemplate = new ContextTemplate();
            Iterator<Attribute> attributeIterator = element.attributeIterator();
            Map<String, String> map = new HashMap<>();
            while (attributeIterator.hasNext()) {
                Attribute attribute = attributeIterator.next();
                map.put(attribute.getName(), attribute.getValue());
            }
            contextTemplate = reflector.doReflect(map, contextTemplate);
            contextTemplates.add(contextTemplate);
            Package p = new Package();
            packageMap.put(contextTemplate.getId(), p);
            configurations.getContext().getSuffixMap().put(contextTemplate.getId(), contextTemplate.getSuffix());
        }
        return contextTemplates;
    }

    /**
     * 解析db-set元素配置
     */
    private List<ContextDatabase> parseContextDatabases(Element contextElement) {
        Element databaseElement = contextElement.element(DB_SET);
        throwMissingException(databaseElement, DB_SET);
        List<Element> databaseElements = databaseElement.elements(DB);
        if (databaseElements == null || databaseElements.size() == 0) {
            System.exit(0);
        }
        FieldReflector<ContextDatabase> reflector = new FieldReflector<>(ContextDatabase.class);
        List<ContextDatabase> contextDatabases = new ArrayList<>();
        for (Element dbElement : databaseElements) {
            ContextDatabase contextDatabase = new ContextDatabase();
            Iterator<Attribute> attributeIterator = dbElement.attributeIterator();
            Map<String, String> map = new HashMap<>();
            while (attributeIterator.hasNext()) {
                Attribute attribute = attributeIterator.next();
                map.put(attribute.getName(), attribute.getValue());
            }
            contextDatabase = reflector.doReflect(map, contextDatabase);
            List<Element> dbElements = dbElement.elements(TABLE);
            if (dbElements != null && dbElements.size() != 0) {
                List<ContextTable> contextTables = new ArrayList<>();
                FieldReflector<ContextTable> ref = new FieldReflector<>(ContextTable.class);
                for (Element ele : dbElements) {
                    ContextTable contextTable = new ContextTable();
                    Map<String, String> m = new HashMap<>();
                    Iterator<Attribute> iterator = ele.attributeIterator();
                    while (iterator.hasNext()) {
                        Attribute attribute = iterator.next();
                        if (attribute.getName() != null && !attribute.getName().trim().equals("") && attribute.getValue() != null
                                && !attribute.getValue().trim().equals("")) {
                            m.put(attribute.getName(), attribute.getValue());
                        }
                    }
                    contextTable = ref.doReflect(m, contextTable);
                    contextTables.add(contextTable);
                }
                contextDatabase.setContextTables(contextTables);
            }
            contextDatabases.add(contextDatabase);
        }
        return contextDatabases;
    }

    /**
     * 将编译路径下的xml配置文件加载为dom4j的Document对象
     * @param configXMLName xml配置文件名
     * @throws DocumentException must be handled
     */
    private void parseXML2Document(String configXMLName) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document;
        String absXMLPath;
        if (GeneratorExecutor.DEBUG) {
            absXMLPath = DEBUG_UNIVERSAL_DIR + "\\" + DEBUG_UNIVERSAL_XML;
        } else {
            absXMLPath = PathUtil.getProjectPath() + "\\" + configXMLName;
        }
        document = reader.read(absXMLPath);
        rootElement = document.getRootElement();
        System.out.println(rootElement.getName());
        if (!GENERATOR.equals(rootElement.getName())) {
            throw new XMLFormatException(configXMLName + " file format error!!!");
        }
    }

    /**
     * 解析时候抛异常
     * @param element 要判断是否存在的元素
     * @param name 元素名
     */
    private void throwMissingException(Element element, String name) {
        if (element == null) {
            throw new ConfigMissingException("Element <" + name + "/> is required!!!");
        }
    }

    public Configurations getConfigurations() {
        return configurations;
    }

    public Map<String, Package> getPackageMap() {
        return packageMap;
    }
}