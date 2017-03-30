package com.geok.gen.core.config.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stephen on 2017/3/22.
 * 封装生成代码有关配置
 */
public class Context {
    private static final String DEFAULT_AUTHOR = "Administrator";

    private String author = DEFAULT_AUTHOR;
    private Map<String, String> suffixMap = new HashMap<>();
    private List<ContextTemplate> contextTemplates;
    private List<ContextDatabase> contextDatabases;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Map<String, String> getSuffixMap() {
        return suffixMap;
    }

    public List<ContextTemplate> getContextTemplates() {
        return contextTemplates;
    }

    public void setContextTemplates(List<ContextTemplate> contextTemplates) {
        this.contextTemplates = contextTemplates;
    }

    public List<ContextDatabase> getContextDatabases() {
        return contextDatabases;
    }

    public void setContextDatabases(List<ContextDatabase> contextDatabases) {
        this.contextDatabases = contextDatabases;
    }

    @Override
    public String toString() {
        return "Context{" +
                "author='" + author + '\'' +
                ", suffixMap=" + suffixMap +
                ", contextTemplates=" + contextTemplates +
                ", contextDatabases=" + contextDatabases +
                '}';
    }
}