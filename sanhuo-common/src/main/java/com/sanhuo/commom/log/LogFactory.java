package com.sanhuo.commom.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 日志工厂
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/7/7:20:49
 */
public class LogFactory {

    public static Logger getLog(Class clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
