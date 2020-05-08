package com.sanhuo.persistent.builder.config.yml.pojo.datasource;

import lombok.Data;

@Data
public class DataSourceProperty {
    /**
     * 数据库id,区分不同的环境/生存(product)/测试(dev)
     */
    private String id;
    /**
     * 数据库驱动
     */
    private String driver;
    /**
     * 数据库连接
     */
    private String url;
    /**
     * 数据库连接用户名
     */
    private String username;
    /**
     * 数据库连接密码
     */
    private String password;
    /**
     * 是否自动提交
     */
    private Boolean autoCommit;

    /**
     * 最大使用连接数
     */
    protected Integer maxActiveConnections;

    /**
     * 最大空闲连接数
     */

    protected Integer maxIdleConnections;

    /**
     * 从池里取出的最大允许时间( 执行sql的时间?)
     */
    protected Integer maxCheckoutTime;

    /**
     * 等待时间
     */

    protected Integer poolTimeToWait;


}
