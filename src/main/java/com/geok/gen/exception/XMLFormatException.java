package com.geok.gen.exception;

/**
 * Created by Stephen on 2017/3/22.
 * 自定义xml格式错误异常
 */
public class XMLFormatException extends RuntimeException {

    public XMLFormatException() {
        super();
    }

    public XMLFormatException(String cause) {
        super(cause);
    }
}
