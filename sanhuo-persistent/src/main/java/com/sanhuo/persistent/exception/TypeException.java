package com.sanhuo.persistent.exception;

/**
 * 类型异常
 */
public class TypeException extends PersistenceException {

    public TypeException() {
        super();
    }

    public TypeException(String message) {
        super(message);
    }

    public TypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public TypeException(Throwable cause) {
        super(cause);
    }
}
