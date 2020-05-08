package com.sanhuo.persistent.reflection.invoker;

import java.lang.reflect.InvocationTargetException;

public interface Invoker {
    /**
     * 调用方法
     *
     * @param target 具体的对象
     * @param args   方法的参数
     * @return
     */
    Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException;

}
