package com.sanhuo.persistent.datasource.pooled;

import com.sanhuo.persistent.builder.config.yml.pojo.datasource.DataSourceProperty;
import com.sanhuo.persistent.datasource.unpooled.UnPooledDataSourceFactory;
import com.sanhuo.persistent.reflection.Reflector;
import com.sanhuo.persistent.reflection.meta.MetaObject;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.commom.basic.StringUtil;

/**
 * 有连接池的数据源工厂
 * 继承 无连接池数据源工厂
 */
public class PooledDataSourceFactory extends UnPooledDataSourceFactory {

    public PooledDataSourceFactory() {
        this.dataSource = new PooledDataSource();
    }

    @Override
    public void setProperties(Configuration configuration) {
        //数据源属性
        DataSourceProperty dataSourceProperty = configuration.getDataSourceProperty();
        MetaObject metaProperties = MetaObject.init(dataSourceProperty);
        MetaObject metaPoolDatasource = MetaObject.init(dataSource);
        MetaObject metaUnPoolDataSource = MetaObject.init(((PooledDataSource) dataSource).getDataSource());
        for (String key : Reflector.getClassFieldsName(dataSourceProperty.getClass())) {
            Object value = metaProperties.getValue(key);
            if (value instanceof String) {
                if (StringUtil.isNotBlank(value.toString())) {
                    metaPoolDatasource.setValue(key, value);
                    metaUnPoolDataSource.setValue(key, value);
                }
            }
            if (value != null) {
                metaPoolDatasource.setValue(key, value);
                metaUnPoolDataSource.setValue(key, value);
            }

        }
    }
}
