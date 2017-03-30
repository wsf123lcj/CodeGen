package com.geok.gen.exception;

/**
 * Created by Stephen on 2017/3/22.
 * 自定义xml配置不完整异常
 */
public class ConfigMissingException extends RuntimeException {

    public ConfigMissingException() {
        super();
    }

    public ConfigMissingException(String cause) {
        super(cause);
    }
}
