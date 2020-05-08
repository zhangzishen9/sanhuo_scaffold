package com.sanhuo.persistent.reflection.invoker;

import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker implements Invoker {
    /**
     * 如果方法只有一个参数，type就是参数类型,否则就是返回值
     */
    @Getter
    private Class<?> type;
    /**
     * 方法本身
     */
    @Getter
    private Method method;

    /**
     * 构造函数
     */
    public MethodInvoker(Method method) {
        this.method = method;
        //如果只有一个参数，返回参数类型，否则返回return的类型
        if (method.getParameterTypes().length == 1) {
            type = method.getParameterTypes()[0];
        } else {
            type = method.getReturnType();
        }
    }


    @Override
    public Object invoke(Object target, Object[] args) throws IllegalAccessException, InvocationTargetException {
        return method.invoke(target, args);
    }
}
