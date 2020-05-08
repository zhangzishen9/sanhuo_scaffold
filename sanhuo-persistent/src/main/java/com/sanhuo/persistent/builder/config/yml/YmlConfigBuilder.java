package com.sanhuo.persistent.builder.config.yml;

import com.sanhuo.persistent.builder.BaseBuilder;
import com.sanhuo.persistent.builder.config.yml.pojo.Persistend;
import com.sanhuo.persistent.builder.config.yml.pojo.datasource.DataSourceElement;
import com.sanhuo.persistent.datasource.DataSourceFactory;
import com.sanhuo.persistent.enums.DataSourceType;
import com.sanhuo.persistent.exception.ExceptionUtil;
import com.sanhuo.persistent.exception.ExceptionMessageConstant;
import com.sanhuo.persistent.session.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Y1ML配置构建器，建造者模式,继承BaseBuilder
 */
@Slf4j
public class YmlConfigBuilder extends BaseBuilder {
    /**
     * 初始化
     */
    public YmlConfigBuilder(Properties properties) {
        // 第一步解析配置文件
        this.environmentsElement(properties);
        this.dataSourceElement(this.configuration);
    }


    /**
     * 解析配置
     */
    private void environmentsElement(Properties properties) {
        Persistend persistend = properties.getPersistend();
        DataSourceElement dataSourceElement = persistend.getDataSource();
        //获取数据源配置列表
        this.configuration.setDataSourceProperties(dataSourceElement.getDataSourceProperties());
        //默认使用哪个数据源配置
        this.configuration.setProfiles(dataSourceElement.getProfiles());
        //当前数据库类型
        this.configuration.setType(dataSourceElement.getType());
        //没有数组库环境配置则报错
        if (CollectionUtils.isEmpty(configuration.getDataSourceProperties())) {
            ExceptionUtil.throwException(PropertiesException.class, ExceptionMessageConstant.DATASOURCE_PROPERTIES_DEFECT);
        }
        List<String> dataSourcesIds = configuration.getDataSourceProperties().stream().map(datasource -> datasource.getId()).collect(Collectors.toList());
        //判断数组库环境列表id中是否存在profiles设置的环境
        if (!dataSourcesIds.contains(dataSourceElement.getProfiles())) {
            ExceptionUtil.throwException(PropertiesException.class, ExceptionMessageConstant.DATASOURCE_ENVIRONMENT_UN_EXIST, dataSourceElement.getProfiles());
        }
    }


    private DataSourceFactory dataSourceElement(Configuration configuration) {
        DataSourceFactory factory = null;
        if (configuration != null) {
            try {
                DataSourceType type = configuration.getType();
                //根据type="POOL"或者"UNPOOL"解析返回对应的DataSourceFactory
                factory = (DataSourceFactory) resolveClass(type.name()).newInstance();
                factory.setProperties(configuration);
                //生成数据源
                this.configuration.setDataSource(factory.getDataSource());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return factory;
    }



}
