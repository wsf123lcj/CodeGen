package com.geok.gen.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Stephen on 2017/3/23.
 * 工具类
 */
public class StringUtil {
    public static final String REGEX = "[*]";
    private static final String HOLDER = "*";

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().equals("");
    }

    /**
     * 生成通用数据库url
     */
    public static String generateUrlPattern(String orgUrl) {
        String urlPattern;
        int index = orgUrl.lastIndexOf("/");
        int index2 = orgUrl.lastIndexOf("?");
        if (index2 < 0) {
            urlPattern = orgUrl.substring(0, index + 1) + HOLDER;
        } else {
            urlPattern = orgUrl.substring(0, index + 1) + HOLDER + orgUrl.substring(index2);
        }
        return urlPattern;
    }

    /**
     * 将表名中特殊符号去除并首字母大写
     */
    public static String firstUpperCaseCamelFromDB(String tableName) {
        String newTableName = camelName(tableName);
        if (newTableName.length() > 1) {
            return newTableName.substring(0, 1).toUpperCase() + newTableName.substring(1);
        }
        return newTableName.substring(0, 1).toUpperCase();
    }


    /**
     * 将字段处理为首字母小写的驼峰形式
     */
    public static String firstLowerCaseCamelFromDB(String columnName) {
        String newColumnName = camelName(columnName);
        if (newColumnName.length() > 1) {
            return newColumnName.substring(0, 1).toLowerCase() + newColumnName.substring(1);
        }
        return newColumnName.substring(0, 1).toLowerCase();
    }

    /**
     * 首字母大写
     */
    public static String firstUpperCase(String str) {
        if (str.length() > 1) {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
        return str.substring(0, 1).toUpperCase();
    }

    /**
     * 首字母小写
     */
    public static String firstLowerCase(String str) {
        if (str.length() > 1) {
            return str.substring(0, 1).toLowerCase() + str.substring(1);
        }
        return str.substring(0, 1).toLowerCase();
    }

    /**
     * 过滤表名中特殊字符
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）_——+|{}【】‘；：”“’。，、？]";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);
        return matcher.replaceAll("").trim();
    }

    /**
     * 数据库名转为驼峰Java名
     */
    public static String camelName(String dbName) {
        dbName = dbName.toLowerCase();
        String[] words = dbName.split("_");
        String result = toUppercase4FirstLetter(words);
        return stringFilter(result);
    }

    private static String toUppercase4FirstLetter(String... words){
        StringBuffer buffer = new StringBuffer();
        if(words != null && words.length > 0){
            for(int i=0;i<words.length;i++){
                String word = words[i];
                String firstLetter = word.substring(0, 1);
                String others = word.substring(1);
                String upperLetter;
                if(i != 0){
                    upperLetter = firstLetter.toUpperCase();
                } else {
                    upperLetter = firstLetter;
                }
                buffer.append(upperLetter).append(others);
            }
            return buffer.toString();
        }
        return "";
    }
}
