package com.geok.gen.exception;

/**
 * Created by Stephen on 2017/3/22.
 * 自定义初始化异常
 */
public class InitializeException extends RuntimeException {

    public InitializeException() {
        super();
    }

    public InitializeException (String cause) {
        super(cause);
    }
}
