package com.sanhuo.app.http.proxy;

import com.sanhuo.app.http.invoke.HttpClientAnalysis;
import com.sanhuo.app.http.invoke.HttpClientMethodContext;
import com.sanhuo.app.http.invoke.HttpMethodInvoker;
import com.sanhuo.app.http.invoke.helper.HttpMethodInvokeHelper;
import com.sanhuo.app.http.invoke.impl.ResttemplateInvoker;
import com.sanhuo.commom.spring.SpringContextManager;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * mapper的代理类,具体在这里调用SqlSession
 *
 * @author sanhuo
 * @date 2020/3/2 0002 下午 21:23
 */
public class HttpClientProxy implements InvocationHandler {

    private final String id;
    private final Class target;

    private final Map<String, HttpClientMethodContext> methodContextMap;


    public HttpClientProxy(Class target) {
        this.id = target.getName() + ".$proxy";
        this.target = target;
        this.methodContextMap = HttpClientAnalysis.doAnalysis(target);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HttpMethodInvoker invoker = SpringContextManager.getBean(HttpMethodInvoker.class);
        return invoker.invoke(this.methodContextMap.get(method.getName()),args);
    }

    @Override
    public String toString() {
        return id;
    }
}
