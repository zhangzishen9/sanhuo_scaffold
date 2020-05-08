package com.sanhuo.persistent.binding.proxy;

import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 *  mapper代理类的factoryBean
 *
 * @author sanhuo
 * @date 2020/3/2 0002 下午 21:24
 */
public class MapperProxyFactory implements FactoryBean {

    private Class<?> mapper;

    /**
     * 接口
     *
     * @param mapper
     */
    public MapperProxyFactory(Class<?> mapper) {
        this.mapper = mapper;
    }

    /**
     * 注入代理类
     *
     * @return
     */
    @Override
    public Object getObject() {
        return Proxy.newProxyInstance(mapper.getClassLoader(),
                new Class[]{mapper}, new MapperProxy());

    }

    /**
     * 是否单例
     *
     * @return
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * 真实接口
     *
     * @return
     */
    @Override
    public Class<?> getObjectType() {
        return mapper;
    }
}
