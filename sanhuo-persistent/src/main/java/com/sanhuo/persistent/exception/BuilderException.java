package com.sanhuo.persistent.exception;

/**
 * 构建异常
 */
public class BuilderException extends PersistenceException{
    public BuilderException() {
        super();
    }

    public BuilderException(String message) {
        super(message);
    }

    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public BuilderException(Throwable cause) {
        super(cause);
    }
}
