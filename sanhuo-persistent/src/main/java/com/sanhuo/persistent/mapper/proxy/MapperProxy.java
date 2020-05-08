package com.sanhuo.persistent.mapper.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * mapper的代理类
 *
 * @author sanhuo
 * @date 2020/3/2 0002 下午 21:23
 */
public class MapperProxy implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理类注入成功");
        return null;
    }
}
