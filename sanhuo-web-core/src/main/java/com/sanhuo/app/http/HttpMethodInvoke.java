package com.sanhuo.app.http;

import com.alibaba.fastjson.JSONObject;
import com.sanhuo.commom.spring.SpringContextManager;
import com.sanhuo.io.http.HttpTemplateWithTimeout;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/19 13:54
 **/
@AllArgsConstructor
public class HttpMethodInvoke {

    private HttpTemplateWithTimeout template;

    private HttpClientMethodContext context;

    public HttpMethodInvoke(HttpClientMethodContext context) {
        this.context = context;
        this.template = SpringContextManager.getBean(HttpTemplateWithTimeout.class);
    }


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
        this.doParseMethodArgsForBody(args);
        ResponseEntity<String> responseEntity = template.postWithTimeout(this.context.getUrl(), this.createHttpEntity(true, args), String.class);
        //转会返回类
        Class returnType = this.context.getReturnType();
        return JSONObject.parseObject(responseEntity.getBody(), returnType);
    }


    private Object put(Object args) {
        return null;
    }


    private HttpEntity createHttpEntity(boolean isJson, Object... args) {
        return new HttpEntity(JSONObject.toJSONString(this.context.getBodyMap()), this.createHeaders(isJson, args));
    }


    private HttpHeaders createHeaders(boolean isJson, Object... args) {
        HttpHeaders headers = new HttpHeaders();
        if (isJson) {
            MediaType type = MediaType.parseMediaType("application/json");
            headers.setContentType(type);
        }
        for (Map.Entry<String, String> header : this.context.getHeaders().entrySet()) {
            String value = header.getValue();
            if (value.contains("${") && value.contains("}")) {
                Integer index = Integer.parseInt(value.substring(value.indexOf("${") + 2, value.indexOf("}")));
                value = args[index].toString();
            }
            headers.add(header.getKey(), value);
        }
        return headers;
    }

    private void doParseMethodArgsForBody(Object... args) {
        for (int i = 0; i < args.length; i++) {
            String paramname = this.context.getMethodParamList().get(0);
            if (this.context.getBodyMap().containsKey(paramname)) {
                this.context.getBodyMap().put(paramname, args[0]);
            }
        }
    }

}
