package com.geok.gen.util;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;


/**
 * Created by sifanW on 2017/3/22.
 * 通过反射给实体类复制
 */
public class FieldReflector<T> {
    private Class<T> targetClass;

    public FieldReflector(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    /**
     * 用户不提供实体类，反射器帮助新建泛型对象的重载方法
     */
    public T doReflect(Map<String, String> map) {
        T t;
        try {
            t = targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
        return doReflect(map, t);
    }

    /**
     * 用户提供实体类，反射器利用所给对象封装数据的重载方法
     */
    public T doReflect(Map<String, String> map, T t) {
        Field[] fields = targetClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String value = map.get(fieldName);
            if (value == null) {
                continue;
            }
            boolean isAccessible = field.isAccessible();
            field.setAccessible(true);
            try {
                setFields(field, t, value);
            } catch (IllegalAccessException | ParseException e) {
                e.printStackTrace();
            }
            field.setAccessible(isAccessible);
        }
        return t;
    }

    /**
     * 设置新建T对象的各字段的值，如果需要的话
     * @param field 类中的字段
     * @param value 从数据库读取到的相应值
     */
    private void setFields(Field field, Object t, String value) throws IllegalArgumentException, IllegalAccessException, ParseException {
        String fieldType = field.getType().toString();
        if (fieldType.contains("byte") || fieldType.contains("Byte")) {
            field.set(t, Byte.parseByte(value));
        } else if (fieldType.contains("short") || fieldType.contains("Short")) {
            field.set(t, Short.parseShort(value));
        } else if (fieldType.contains("int") || fieldType.contains("Integer")) {
            field.set(t, Integer.parseInt(value.trim()));
        } else if (fieldType.contains("long") || fieldType.contains("Long")) {
            field.set(t, Long.parseLong(value));
        } else if (fieldType.contains("float") || fieldType.contains("Float")) {
            field.set(t, Float.parseFloat(value));
        } else if (fieldType.contains("double") || fieldType.contains("Double")) {
            field.set(t, Double.parseDouble(value));
        } else if (fieldType.contains("char") || fieldType.contains("Character")) {
            field.set(t, value.charAt(0));
        } else if (fieldType.contains("String")) {
            field.set(t, value);
        } else if (fieldType.contains("Boolean") || fieldType.contains("boolean")) {
            field.set(t, Boolean.valueOf(value));
        } else if (fieldType.contains("date") || fieldType.contains("Date")) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            field.set(t, dateFormat.parse(value));
        } else {
            System.out.println("unknown data type!!!");
        }
    }

    public Class<T> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<T> targetClass) {
        this.targetClass = targetClass;
    }
}
