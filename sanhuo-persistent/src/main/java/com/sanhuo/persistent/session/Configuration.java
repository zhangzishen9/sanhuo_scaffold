package com.sanhuo.persistent.session;

import com.sanhuo.persistent.builder.config.yml.pojo.datasource.DataSourceProperty;
import com.sanhuo.persistent.enums.DataSourceType;
import com.sanhuo.persistent.type.TypeAliasRegistry;
import com.sanhuo.persistent.type.TypeHandlerRegistry;
import lombok.Data;

import javax.sql.DataSource;
import java.util.List;

@Data
public class Configuration {
    //类型别名注册机
    protected final TypeAliasRegistry typeAliasRegistry;
    //类型处理器注册机
    protected final TypeHandlerRegistry typeHandlerRegistry;

    private Configuration(TypeAliasRegistry typeAliasRegistry, TypeHandlerRegistry typeHandlerRegistry) {
        this.typeAliasRegistry = typeAliasRegistry;
        this.typeHandlerRegistry = typeHandlerRegistry;
    }

    /**
     * 数据源配置列表
     */
    private List<DataSourceProperty> dataSourceProperties;
    /**
     * 当前环境
     */
    private String profiles;
    /**
     * 生成datasource的类型 Pool(池化)/Unpool(非池化)
     */
    private DataSourceType type;
    /**
     * 数据源
     */
    private DataSource dataSource;

    /**
     * 返回profiles指定的数据源配置
     */
    public DataSourceProperty getDataSourceProperty() {
        return dataSourceProperties.stream().filter(dataSourceProperty -> dataSourceProperty.getId().equals(profiles))
                .findAny().orElse(null);

    }

    public static Configuration init() {
        return init(null, null);
    }

    public static Configuration init(TypeAliasRegistry typeAliasRegistry, TypeHandlerRegistry typeHandlerRegistry) {
        //如果传进来是null,将默认的赋值进去
        typeAliasRegistry = typeAliasRegistry == null ? new TypeAliasRegistry() : typeAliasRegistry;
        typeHandlerRegistry = typeHandlerRegistry == null ? new TypeHandlerRegistry() : typeHandlerRegistry;
        return new Configuration(typeAliasRegistry, typeHandlerRegistry);
    }

}
