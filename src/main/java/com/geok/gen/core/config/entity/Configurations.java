package com.geok.gen.core.config.entity;

/**
 * Created by Stephen on 2017/3/22.
 * xml配置文件的所有配置
 */
public class Configurations {
    private DataSource dataSource;
    private Context context;
    private static Configurations configurations;

    private Configurations(){
        dataSource = new DataSource();
        context = new Context();
    }

    public static Configurations getInstance() {
        if (configurations == null) {
            configurations = new Configurations();
        }
        return configurations;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public String toString() {
        return "Configurations{" +
                "dataSource=" + dataSource +
                ", context=" + context +
                '}';
    }
}
