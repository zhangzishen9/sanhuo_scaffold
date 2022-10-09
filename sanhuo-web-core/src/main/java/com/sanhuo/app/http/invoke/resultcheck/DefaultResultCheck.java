package com.sanhuo.app.http.invoke.resultcheck;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/20 14:10
 **/
public class DefaultResultCheck implements HttpClientApiResultCheck<String> {

    @Override
    public Boolean checkApiIfSuccess(String httpResultJson) {
        //do nothing
        return true;
    }

    @Override
    public String errorMessage(String httpResult) {
        return "";
    }
}
