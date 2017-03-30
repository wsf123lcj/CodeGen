package com.geok.gen.core.db.resolver;

import com.geok.gen.core.config.entity.Configurations;
import com.geok.gen.core.config.entity.DataSource;
import com.geok.gen.core.config.reader.ClassPathXMLContext;
import com.geok.gen.core.db.entity.OriginalColumnStructure;
import com.geok.gen.core.db.entity.OriginalPrimaryKeyStructure;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stephen on 2017/3/23.
 * 解析Postgresql的数据类型
 */
public class DatabaseTypeResolver {
    private static final String MAP = "Map";
    private static final String TYPE = "type";
    private static final String JAVA_TYPE = "javaType";
    private static final String IS_ARRAY = "isArray";
    private static final String NUMBER = "number";
    private static final String TIMESTAMP = "timestamp";
    private static DatabaseTypeResolver typeResolver;
    private String currentDbMap;
    private String currentDb;
    private Map<String, JType> jTypeMap;
    private Element rootElement;

    private DatabaseTypeResolver() {
        DataSource.DialectEnum dialectEnum = Configurations.getInstance().getDataSource().getDialectEnum();
        currentDbMap = dialectEnum.toString() + MAP;
        currentDb = dialectEnum.toString();
        try {
            parseXML2Document();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static DatabaseTypeResolver getInstance() {
        if(typeResolver == null) {
            typeResolver = new DatabaseTypeResolver();
        }
        return typeResolver;
    }

    public String resolvePrimaryKey(OriginalPrimaryKeyStructure opks) {
        Map<String, OriginalColumnStructure> map = opks.getOriginalTableStructure().getOriginalColumnStructureMap();
        String keyName = opks.getName();
        return resolveColumn(map.get(keyName));
    }

    /**
     * 解析数据库字段类型为java类型
     */
    public String resolveColumn(OriginalColumnStructure ocs) {
        String dbType = ocs.getType().toLowerCase();
        if (NUMBER.equals(dbType)) {
            if (!ocs.isHasBeenSettled()) {
                ocs.setArray(false);
                ocs.setHasBeenSettled(true);
            }
            return resolveOraNumber(ocs);
        } else if (dbType.contains(TIMESTAMP)) {
            if (!ocs.isHasBeenSettled()) {
                ocs.setArray(false);
                ocs.setHasBeenSettled(true);
            }
            return jTypeMap.get(TIMESTAMP).getJavaType();
        }
        JType jType = jTypeMap.get(dbType);
        if (jType == null) {
            return null;
        }
        if (!ocs.isHasBeenSettled()) {
            ocs.setArray(jType.isArray());
            ocs.setHasBeenSettled(true);
        }
        return jType.getJavaType();
    }



    /**
     * 将编译路径下的xml配置文件加载为dom4j的Document对象
     * @throws DocumentException must be handled
     */
    private void parseXML2Document() throws DocumentException {
        rootElement = new SAXReader().read(ClassPathXMLContext.class.getClassLoader().getResourceAsStream("Mapping.xml")).getRootElement();
        resolvePostgresTypeFromXML();
    }

    private void resolvePostgresTypeFromXML() {
        jTypeMap = new HashMap<>(30);
        List<Element> typeElements = rootElement.element(currentDbMap).elements(TYPE);
        for (Element typeElement : typeElements) {
            JType jType = new JType();
            jType.setDbType(typeElement.attribute(currentDb).getValue());
            jType.setJavaType(typeElement.attribute(JAVA_TYPE).getValue());
            jType.setArray(Boolean.parseBoolean(typeElement.attribute(IS_ARRAY).getValue()));
            jTypeMap.put(jType.getDbType(), jType);
        }
    }

    private String resolveOraNumber(OriginalColumnStructure ocs) {
        int size = ocs.getSize();
        int digits = ocs.getDigits();
        if (digits <= 0) {
            if (size == 1) {
                return "java.lang.Boolean";
            } else if (size >= 2 && size <= 9) {
                return "java.lang.Integer";
            } else if (size >= 10 && size <= 18) {
                return "java.lang.Integer";
            } else {
                return "java.math.BigDecimal";
            }
        } else if (digits > 0 && digits <= 7) {
            if (size <= 18) {
                return "java.lang.Double";
            } else {
                return "java.math.BigDecimal";
            }
        }
        return "java.math.BigDecimal";
    }

    /**
     * 封装数据库类型映射的实体类
     */
    public class JType {
        private String dbType;
        private String javaType;
        private boolean isArray = false;

        public String getDbType() {
            return dbType;
        }

        public void setDbType(String dbType) {
            this.dbType = dbType;
        }

        public String getJavaType() {
            return javaType;
        }

        public void setJavaType(String javaType) {
            this.javaType = javaType;
        }

        public boolean isArray() {
            return isArray;
        }

        public void setArray(boolean array) {
            isArray = array;
        }

        @Override
        public String toString() {
            return "JType{" +
                    "dbType='" + dbType + '\'' +
                    ", javaType='" + javaType + '\'' +
                    ", isArray=" + isArray +
                    '}';
        }
    }
}