package com.sanhuo.persistent.exception;

/**
 * 抛异常工具类 用到的 异常 (套娃?)
 */
public class ErrorMessageException extends PersistenceException {


    public ErrorMessageException() {
        super();
    }

    public ErrorMessageException(String message) {
        super(message);
    }

    public ErrorMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ErrorMessageException(Throwable cause) {
        super(cause);
    }

}
