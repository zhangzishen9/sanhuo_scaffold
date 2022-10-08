package com.sanhuo.app.http.invoke;

import com.sanhuo.app.http.invoke.helper.HttpMethodInvokeHelper;

/**
 * @author zhangzs
 * @description 方法具体实现
 * @date 2022/10/8 19:42
 **/
public interface HttpMethodInvoker {
    /**
     * 具体的方法实现
     *
     * @param helper 帮助类，用于处理参数。内置上下文
     * @param args    方法参数
     * @return
     */
    Object invoke(HttpMethodInvokeHelper helper, Object... args);
}
