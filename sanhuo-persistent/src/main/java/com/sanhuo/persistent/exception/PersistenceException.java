package com.sanhuo.persistent.exception;

/**
 * 该模块所有异常的父类
 */
public class PersistenceException extends RuntimeException  {

    public PersistenceException() {
        super();
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }
}
