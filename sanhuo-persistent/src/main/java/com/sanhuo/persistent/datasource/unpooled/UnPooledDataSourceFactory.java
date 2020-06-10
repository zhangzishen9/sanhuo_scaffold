package com.sanhuo.persistent.datasource.unpooled;

import com.sanhuo.persistent.builder.config.yml.pojo.datasource.DataSourceProperty;
import com.sanhuo.persistent.datasource.DataSourceFactory;
import com.sanhuo.persistent.reflection.Reflector;
import com.sanhuo.persistent.reflection.meta.MetaObject;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.commom.basic.StringUtil;

import javax.sql.DataSource;

/**
 * 没有池化的数据源工厂
 */
public class UnPooledDataSourceFactory implements DataSourceFactory {
    /**
     * 核心 数据源
     */
    protected DataSource dataSource;

    public UnPooledDataSourceFactory() {
        this.dataSource = new UnPooledDataSource();
    }

    @Override
    public void setProperties(Configuration configuration) {
        //数据源属性
        DataSourceProperty dataSourceProperty = configuration.getDataSourceProperty();
        MetaObject metaProperties = MetaObject.init(dataSourceProperty);
        MetaObject metaDataSource = MetaObject.init(dataSource);
        for (String key : Reflector.getClassFieldsName(dataSourceProperty.getClass())) {
            Object value = metaProperties.getValue(key);
            if (value instanceof String) {
                if (StringUtil.isNotBlank(value.toString())) {
                    metaDataSource.setValue(key, value);
                }
            }
            if (value != null) {
                metaDataSource.setValue(key, value);
            }

        }
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }


}
