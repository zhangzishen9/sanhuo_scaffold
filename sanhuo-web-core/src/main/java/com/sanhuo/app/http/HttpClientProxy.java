package com.sanhuo.app.http;

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
        return new HttpMethodInvoke(this.methodContextMap.get(method.getName())).invoke(args);
    }

    @Override
    public String toString() {
        return id;
    }
}
