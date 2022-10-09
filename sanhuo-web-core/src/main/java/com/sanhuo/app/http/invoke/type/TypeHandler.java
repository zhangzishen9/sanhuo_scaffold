package com.sanhuo.app.http.invoke.type;

import com.sanhuo.app.http.invoke.param.HttpMethodParam;

import java.lang.reflect.InvocationTargetException;

/**
 * @author zhangzs
 * @description 类型处理器
 * @date 2022/10/9 10:24
 **/
public interface TypeHandler<T> {

    /**
     * 获取参数值
     */
    String get(HttpMethodParam param, Object... args) throws Exception;


    default T getArg(HttpMethodParam param, Object... args) {
        return (T) args[param.getParamIndex()];
    }

}
