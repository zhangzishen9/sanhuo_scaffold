package com.sanhuo.app.http.invoke.type;

import com.alibaba.fastjson.JSONObject;
import com.sanhuo.app.http.invoke.param.HttpMethodParam;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author zhangzs
 * @description 对象内部属性的处理
 * @date 2022/10/9 10:27
 **/
public class InnerObjectHandler implements TypeHandler<Object> {
    @Override
    public String get(HttpMethodParam param, Object... args) throws Exception {
        JSONObject json;
        if (param.getParentObject() == null) {
            Object arg = this.getArg(param, args);
            json = JSONObject.parseObject(JSONObject.toJSONString(arg));
            param.setParentObject(json);
        } else {
            json = param.getParentObject();
        }
        return json.get(param.getName()).toString();
    }
}
