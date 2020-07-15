package com.sanhuo.persistent.logging;

import com.sanhuo.persistent.session.Configuration;
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

    /**
     * 返回装饰后的log对象
     *
     * @param clazz
     * @return
     */
    public Log getLog(Class clazz, Boolean enable) {
        Logger logger = LoggerFactory.getLogger(clazz);
        Log log = new Log(logger, enable);
        return log;
    }


}
