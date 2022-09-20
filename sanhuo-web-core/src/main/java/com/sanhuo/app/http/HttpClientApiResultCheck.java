package com.sanhuo.app.http;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/20 13:10
 **/
public interface HttpClientApiResultCheck {
    /**
     * 校验接口返回是否成功
     */
    void checkApiIfSuccess(String httpResultJson);
}
