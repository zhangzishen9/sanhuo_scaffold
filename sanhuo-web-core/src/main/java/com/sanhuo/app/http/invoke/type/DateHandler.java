package com.sanhuo.app.http.invoke.type;

import com.sanhuo.app.http.invoke.param.HttpMethodParam;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangzs
 * @description
 * @date 2022/10/9 11:24
 **/
public class DateHandler implements TypeHandler<Date>{
    @Override
    public String get(HttpMethodParam param, Object... args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(this.getArg(param,args));
    }


}
