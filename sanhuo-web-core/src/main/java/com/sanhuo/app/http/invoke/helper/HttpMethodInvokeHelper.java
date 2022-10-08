package com.sanhuo.app.http.invoke.helper;

import com.alibaba.fastjson.JSONObject;
import com.sanhuo.app.http.invoke.HttpClientMethodContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Map;

/**
 * @author zhangzs
 * @description 帮助类
 * @date 2022/10/8 19:46
 **/
@AllArgsConstructor
public class HttpMethodInvokeHelper {

    private HttpClientMethodContext context;


    public HttpEntity createHttpEntity(boolean isJson, Object... args) {
        return new HttpEntity(JSONObject.toJSONString(this.context.getBodyMap()), this.createHeaders(isJson, args));
    }


    public HttpHeaders createHeaders(boolean isJson, Object... args) {
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

    public void doParseMethodArgsForBody(Object... args) {
        for (int i = 0; i < args.length; i++) {
            String paramname = this.context.getMethodParamList().get(0);
            if (this.context.getBodyMap().containsKey(paramname)) {
                this.context.getBodyMap().put(paramname, args[0]);
            }
        }
    }

}
