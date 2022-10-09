package com.sanhuo.app.http.invoke.impl;

import com.alibaba.fastjson.JSONObject;
import com.sanhuo.app.http.invoke.HttpClientMethodContext;
import com.sanhuo.app.http.invoke.HttpMethodInvoker;
import com.sanhuo.app.http.invoke.helper.HttpEntityHelper;
import com.sanhuo.app.http.invoke.helper.HttpMethodInvokeHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

/**
 * @author zhangzs
 * @description 使用resttemplate实现
 * @date 2022/9/19 13:54
 **/
@AllArgsConstructor
public class ResttemplateInvoker extends HttpMethodInvoker {

    private HttpTemplateWithTimeout template;

    @Override
    public Object doInvoke(HttpClientMethodContext context, Object... args) {
        switch (context.getMethod()) {
            case POST:
                return this.post(context, args);
            case GET:
                return this.get(context, args);
            case PUT:
                return this.put(context, args);
            case DELETE:
                return this.delete(context, args);
            default:
                throw new RuntimeException("暂不支持GET/POST/PUT/DELETE以外的方法");
        }
    }

    private Object get(Object... args) {
        return null;
    }

    private Object post(HttpClientMethodContext context, Object... args) {
        HttpEntity<String> entity = new HttpEntityHelper(context, args).createEntity();
        ResponseEntity<String> responseEntity = template.postWithTimeout(context.getUrl(), entity, String.class);
        Class returnType = context.getReturnType();
        return JSONObject.parseObject(responseEntity.getBody(), returnType);
    }


    private Object put(HttpClientMethodContext context, Object... args) {
        return null;
    }


    private Object delete(HttpClientMethodContext context, Object... args) {
        return null;
    }

}
