package com.sanhuo.app.http.invoke.type;

import com.sanhuo.app.http.invoke.param.HttpMethodParam;

/**
 * @author zhangzs
 * @description
 * @date 2022/10/9 10:27
 **/
public class StringHandler implements TypeHandler<String> {

    @Override
    public String get(HttpMethodParam param, Object... args) throws Exception {
        return this.getArg(param, args);
    }
}
