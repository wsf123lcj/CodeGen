package com.geok.gen.exception;

/**
 * Created by Stephen on 2017/3/22.
 * 自定义数据库名不存在异常
 */
public class CatalogNotExistsException extends RuntimeException {

    public CatalogNotExistsException() {
        super();
    }

    public CatalogNotExistsException (String catalogName) {
        super(catalogName);
    }
}
