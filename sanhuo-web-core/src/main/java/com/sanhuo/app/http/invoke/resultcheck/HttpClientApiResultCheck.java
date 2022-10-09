package com.sanhuo.app.http.invoke.resultcheck;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/20 13:10
 **/
public interface HttpClientApiResultCheck<T> {
    /**
     * 校验接口返回是否成功
     */
    Boolean checkApiIfSuccess(T httpResult);

    /**
     * 抛出的异常
     */
    String errorMessage(T httpResult);
}
