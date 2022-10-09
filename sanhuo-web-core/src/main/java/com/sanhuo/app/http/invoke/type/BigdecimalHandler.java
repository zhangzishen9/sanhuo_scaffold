package com.sanhuo.app.http.invoke.type;

import com.sanhuo.app.http.invoke.param.HttpMethodParam;

import java.math.BigDecimal;

/**
 * @author zhangzs
 * @description
 * @date 2022/10/9 11:01
 **/
public class BigdecimalHandler implements TypeHandler<BigDecimal> {
    @Override
    public String get(HttpMethodParam param, Object... args) throws Exception {
        return getArg(param, args).toString();
    }
}
