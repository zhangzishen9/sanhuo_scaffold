package com.sanhuo.persistent.session;

import com.sanhuo.persistent.binding.property.TableProperty;
import com.sanhuo.persistent.builder.config.yml.pojo.datasource.DataSourceProperty;
import com.sanhuo.persistent.enums.DataSourceType;
import com.sanhuo.persistent.mapping.MappedStatement;
import com.sanhuo.persistent.type.TypeAliasRegistry;
import com.sanhuo.persistent.type.TypeHandlerRegistry;
import com.sanhuo.persistent.type.TypeParsingRegistry;
import lombok.Getter;
import lombok.Setter;

import javax.sql.DataSource;
import java.util.*;

public class Configuration {
    //类型别名注册机
    @Getter
    @Setter
    protected final TypeAliasRegistry typeAliasRegistry;
    //类型处理器注册机
    @Getter
    @Setter
    protected final TypeHandlerRegistry typeHandlerRegistry;
    //java类型和jdbc类型默认转换注册机
    @Getter
    @Setter
    protected final TypeParsingRegistry typeParsingRegistry;

    private Configuration(TypeAliasRegistry typeAliasRegistry, TypeHandlerRegistry typeHandlerRegistry, TypeParsingRegistry typeParsingRegistry) {
        this.typeAliasRegistry = typeAliasRegistry;
        this.typeHandlerRegistry = typeHandlerRegistry;
        this.typeParsingRegistry = typeParsingRegistry;
    }

    /**
     * 数据源配置列表
     */
    @Getter
    @Setter
    private List<DataSourceProperty> dataSourceProperties;
    /**
     * 当前环境
     */
    @Getter
    @Setter
    private String profiles;
    /**
     * 生成datasource的类型 Pool(池化)/Unpool(非池化)
     */
    @Getter
    @Setter
    private DataSourceType type;
    /**
     * 数据源
     */
    @Getter
    @Setter
    private DataSource dataSource;

    /**
     * mapper和entity的映射关系
     */
    private final Map<Class, Class> mappedEntities = new HashMap<>();

    /**
     * 添加映射关系
     */
    public void addMappedEntity(Class mapper, Class entity) {
        this.mappedEntities.put(mapper, entity);
        //TODO 判断是否重复,是的话抛异常
    }

    /**
     * 实体类映射到数据库的对应属性
     */
    private final Map<Class, TableProperty> entityPropertyMap = new HashMap<>();

    /**
     * 实体解析
     *
     * @param entity        添加
     * @param tableProperty
     */
    public void addEntityParsing(Class<?> entity, TableProperty tableProperty) {
        this.entityPropertyMap.put(entity, tableProperty);
    }

    /**
     * 获取mapper对应的实体的解析
     *
     * @param entity
     */
    public TableProperty getEntityParsing(Class<?> entity) {
        if (this.entityPropertyMap.containsKey(entity)) {
            return this.entityPropertyMap.get(entity);
        }
        //todo 处理
        return null;
    }

    /**
     * 映射的语句
     */
    private final Map<String, MappedStatement> mappedStatements = new HashMap<>();

    /**
     * 方法映射
     *
     * @param id              方法签名
     * @param mappedStatement 解析方法后的对象 具体执行方法就靠这个
     */
    public void addMappedStatement(String id, MappedStatement mappedStatement) {
        this.mappedStatements.put(id, mappedStatement);
    }

    /**
     * 获取方法解析后的mappedStatement
     *
     * @param id 方法签名
     * @return
     */
    public MappedStatement getMappedStatement(String id) {
        if (this.mappedStatements.containsKey(id)) {
            return this.mappedStatements.get(id);
        }
        //todo 处理
        return null;
    }

    /**
     * 已经解析过的mapper
     */
    private final Set<String> loadedResource = new HashSet<>();

    /**
     * 判断该类是否已经解析过
     */
    public Boolean isResourceLoaded(String resource) {
        return this.loadedResource.contains(resource);
    }

    /**
     * 已经解析过
     */
    public void addLoadedResource(String resource) {
        this.loadedResource.add(resource);
    }

    /**
     * 返回profiles指定的数据源配置
     */
    public DataSourceProperty getDataSourceProperty() {
        return dataSourceProperties.stream().filter(dataSourceProperty -> dataSourceProperty.getId().equals(profiles))
                .findAny().orElse(null);

    }

    public static Configuration init() {
        return init(null, null, null);
    }

    public static Configuration init(TypeAliasRegistry typeAliasRegistry, TypeHandlerRegistry typeHandlerRegistry, TypeParsingRegistry typeParsingRegistry) {
        //如果传进来是null,将默认的赋值进去
        typeAliasRegistry = typeAliasRegistry == null ? new TypeAliasRegistry() : typeAliasRegistry;
        typeHandlerRegistry = typeHandlerRegistry == null ? new TypeHandlerRegistry() : typeHandlerRegistry;
        typeParsingRegistry = typeParsingRegistry == null ? new TypeParsingRegistry() : typeParsingRegistry;
        return new Configuration(typeAliasRegistry, typeHandlerRegistry, typeParsingRegistry);
    }


}
