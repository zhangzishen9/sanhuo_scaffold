package com.sanhuo.persistent.datasource;

import com.sanhuo.persistent.session.Configuration;

import javax.sql.DataSource;

/**
 * 数据源的父类
 */
public interface DataSourceFactory {

    //设置属性,被YMLConfigBuilder所调用
    void setProperties(Configuration configuration);

    //生产数据源,直接得到javax.sql.DataSource
    DataSource getDataSource();
}
