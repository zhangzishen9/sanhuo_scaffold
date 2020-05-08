package com.sanhuo.persistent.builder;

import com.sanhuo.persistent.exception.BuilderException;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.persistent.type.TypeAliasRegistry;
import com.sanhuo.persistent.type.TypeHandlerRegistry;
import lombok.Data;

/**
 * 构建器的基类，建造者模式
 */
@Data
public abstract class BaseBuilder {
    //存放启动的一些配置属性
    protected final Configuration configuration;
    //类型别名注册器
    private final TypeAliasRegistry typeAliasRegistry;
    //类型处理器注册器
    private final TypeHandlerRegistry typeHandlerRegistry;


    public BaseBuilder() {
        this.configuration = Configuration.init();
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
        this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
    }

    public BaseBuilder(Configuration configuration) {
        this.configuration = configuration;
        this.typeAliasRegistry = this.configuration.getTypeAliasRegistry();
        this.typeHandlerRegistry = this.configuration.getTypeHandlerRegistry();
    }

    //根据别名解析Class，然后创建实例
    protected Object createInstance(String alias) {
        Class<?> clazz = resolveClass(alias);
        if (clazz == null) {
            return null;
        }
        try {
            return resolveClass(alias).newInstance();
        } catch (Exception e) {
            throw new BuilderException("Error creating alias: [ " + alias + "] instance . Cause: " + e, e);
        }
    }

    //根据别名解析Class,其实是去查看 类型别名注册/事务管理器别名
    protected Class<?> resolveClass(String alias) {
        if (alias == null) {
            return null;
        }
        try {
            return resolveAlias(alias);
        } catch (Exception e) {
            throw new BuilderException("Error resolving class. Cause: " + e, e);
        }
    }

    protected Class<?> resolveAlias(String alias) {
        return typeAliasRegistry.resolveAlias(alias);
    }
}
