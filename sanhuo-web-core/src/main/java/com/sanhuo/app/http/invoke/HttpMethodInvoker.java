package com.sanhuo.app.http.invoke;

import com.sanhuo.app.http.invoke.helper.HttpMethodInvokeHelper;
import com.sanhuo.app.http.invoke.resultcheck.HttpClientApiResultCheck;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangzs
 * @description 方法具体实现
 * @date 2022/10/8 19:42
 **/
@Slf4j
public abstract class HttpMethodInvoker {

    public Object invoke(HttpClientMethodContext context, Object... args) {
        Object result = null;
        try {
            result = doInvoke(context, args);
        } catch (Exception e) {
            log.error("实现http方法出错:{}", e.getMessage());
        }
        //校验结果
        HttpClientApiResultCheck check = context.getResultCheck();
        boolean isSuccess = check.checkApiIfSuccess(result);
        if (!isSuccess) {
            String msg = String.format("校验http结果出错:%s", check.errorMessage(result));
            log.error(msg);
            throw new RuntimeException(msg);
        }
        return result;
    }

    /**
     * 具体的方法实现
     *
     * @param context 上下文
     * @param args    方法参数
     * @return
     */
    protected abstract Object doInvoke(HttpClientMethodContext context, Object... args);
}
