package com.sanhuo.app.http.proxy;

import com.sanhuo.app.http.proxy.HttpClientProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Proxy;

/**
 * httpClientFactoryBean
 *
 * @author sanhuo
 * @date 2020/3/2 0002 下午 21:24
 */
public class HttpClientProxyFactory implements FactoryBean {

    private Class<?> httpClient;

    /**
     * 接口
     *
     * @param httpClient
     */
    public HttpClientProxyFactory(Class<?> httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 注入代理类
     *
     * @return
     */
    @Override
    public Object getObject() {
        return Proxy.newProxyInstance(httpClient.getClassLoader(),
                new Class[]{httpClient}, new HttpClientProxy(httpClient));

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
        return httpClient;
    }
}
