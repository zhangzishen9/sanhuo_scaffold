package com.sanhuo.app.http.invoke.type;

import com.sanhuo.app.http.invoke.param.HttpMethodParam;

/**
 * @author zhangzs
 * @description
 * @date 2022/10/9 11:24
 **/
public class ByteHandler implements TypeHandler<Byte>{
    @Override
    public String get(HttpMethodParam param, Object... args) throws Exception {
        return this.getArg(param,args).toString();
    }
}
