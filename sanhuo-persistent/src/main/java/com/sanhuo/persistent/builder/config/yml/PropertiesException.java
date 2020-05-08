package com.sanhuo.persistent.builder.config.yml;

import com.sanhuo.persistent.exception.PersistenceException;

/**
 * 读取配置过程中的错误
 */
public class PropertiesException extends PersistenceException {
    public PropertiesException() {
        super();
    }

    public PropertiesException(String message) {
        super(message);
    }

}
