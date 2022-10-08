package com.sanhuo.app.http.invoke.impl;

import com.alibaba.fastjson.JSONObject;
import com.sanhuo.app.http.invoke.HttpClientMethodContext;
import com.sanhuo.app.http.invoke.HttpMethodInvoker;
import com.sanhuo.app.http.invoke.helper.HttpMethodInvokeHelper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

/**
 * @author zhangzs
 * @description 使用resttemplate实现
 * @date 2022/9/19 13:54
 **/
@AllArgsConstructor
public class ResttemplateInvoker implements HttpMethodInvoker {

    private HttpTemplateWithTimeout template;


    public ResttemplateInvoker(HttpTemplateWithTimeout template) {
       this.template = template;
    }


    @Override
    public Object invoke(HttpMethodInvokeHelper helper, Object... args) {
        switch (helper.getContext().getMethod()) {
            case POST:
                return this.post(args);
        }
        return null;
    }

    private Object get(Object... args) {
        return null;
    }

    private Object post(Object... args) {
        this.helper.doParseMethodArgsForBody(args);
        ResponseEntity<String> responseEntity = template.postWithTimeout(this..getUrl(), this.helper.createHttpEntity(true, args), String.class);
        //转会返回类
        Class returnType = this.context.getReturnType();
        return JSONObject.parseObject(responseEntity.getBody(), returnType);
    }


    private Object put(Object args) {
        return null;
    }


    private Object delete(Object args) {
        return null;
    }

}
