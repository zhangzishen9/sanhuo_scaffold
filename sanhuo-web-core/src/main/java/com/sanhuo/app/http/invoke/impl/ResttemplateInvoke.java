package com.sanhuo.app.http.invoke.impl;

import com.alibaba.fastjson.JSONObject;
import com.sanhuo.app.http.invoke.HttpClientMethodContext;
import com.sanhuo.app.http.invoke.HttpMethodInvoke;
import com.sanhuo.app.http.invoke.helper.HttpMethodInvokeHelper;
import com.sanhuo.commom.spring.SpringContextManager;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;

/**
 * @author zhangzs
 * @description 使用resttemplate实现
 * @date 2022/9/19 13:54
 **/
@AllArgsConstructor
public class ResttemplateInvoke implements HttpMethodInvoke {

    private HttpTemplateWithTimeout template;

    private HttpClientMethodContext context;

    private HttpMethodInvokeHelper helper;

    public ResttemplateInvoke(HttpClientMethodContext context) {
        this.context = context;
        this.helper = new HttpMethodInvokeHelper(context);
        this.template = SpringContextManager.getBean(HttpTemplateWithTimeout.class);
    }


    @Override
    public Object invoke(Object... args) {
        switch (this.context.getMethod()) {
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
        ResponseEntity<String> responseEntity = template.postWithTimeout(this.context.getUrl(), this.helper.createHttpEntity(true, args), String.class);
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
