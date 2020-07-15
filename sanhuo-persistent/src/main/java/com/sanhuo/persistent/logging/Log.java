package com.sanhuo.persistent.logging;

import com.sanhuo.persistent.session.Configuration;
import org.slf4j.Logger;

/**
 * <p>
 * 封装log42j的原始logger 增强 (装饰模式?)
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/7/15:21:01
 */
public class Log {

    /**
     * 原本的logger
     */
    private Logger logger;

    /**
     * 是否能打印日志
     */
    private Boolean enableLog;


    /**
     * 换行符
     */
    private static final String NEXT_LINE = "\r\n";

    /**
     * 占位符
     */
    private static final String PLACEHOLDER = "{}";

    public Log(Logger logger, Boolean enableLog) {
        this.logger = logger;
        this.enableLog = enableLog;

    }

    /**
     * 打印Sql
     *
     * @param sql
     * @param params
     */
    public void logSql(String sql, Object... params) {
        // 能打印再说
        if (this.enableLog) {
            StringBuffer logInfo = new StringBuffer(NEXT_LINE)
                    .append("execute sql : [")
                    .append(PLACEHOLDER)
                    .append("]")
                    .append(NEXT_LINE);
            if (params != null) {
                // 输出参数
                for (int index = 0; index < params.length; index++) {
                    logInfo.append("param[").append(index).append("] :")
                            .append(PLACEHOLDER).append(NEXT_LINE);
                }
            }
            this.logger.info(logInfo.toString(), sql, params);
        }
    }
}
