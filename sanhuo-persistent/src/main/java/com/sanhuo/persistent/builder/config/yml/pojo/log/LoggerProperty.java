package com.sanhuo.persistent.builder.config.yml.pojo.log;

import lombok.Data;

/**
 * <p>
 * 日志配置
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/7/15:21:35
 */
@Data
public class LoggerProperty {

    /**
     * 是否允许打印日志,默认true
     */
    private Boolean enable = true;
}
