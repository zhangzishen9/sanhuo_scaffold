package com.sanhuo.app.http.invoke.helper;

import com.sanhuo.app.http.invoke.HttpClientMethodContext;
import com.sanhuo.app.http.invoke.param.HttpMethodParam;
import com.sanhuo.app.http.invoke.param.ParamTypeEnum;
import com.sanhuo.commom.basic.StringUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;

import javax.xml.transform.Source;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhangzs
 * @description 通过context生成请求体的帮助类
 * @date 2022/10/9 12:19
 **/
@Slf4j
public class HttpEntityHelper {

    public static Pattern pattern = Pattern.compile("s*\\{\\s*(.+?)\\s*\\}");
    private HttpClientMethodContext context;

    private Object[] args;


    /**
     * #{@link  org.springframework.web.bind.annotation.PathVariable}
     */
    private static final Map<String, String> pathVariableMap = new HashMap<>();

    /**
     * #{@link  org.springframework.web.bind.annotation.RequestParam}
     */
    private static final Map<String, String> paramMap = new HashMap<>();

    public HttpEntityHelper(HttpClientMethodContext context, Object[] args) {
        for (HttpMethodParam param : context.getParams()) {
            //解析pathVariable
            if (ParamTypeEnum.PATH_VARIABLE == param.getParamType()) {
                String value = null;
                try {
                    value = param.getTypeHandler().get(param, args);
                } catch (Exception e) {
                    log.error("get {} error", param.getName());
                }
                pathVariableMap.put(param.getAlias(), value);
            }
        }
    }

    /**
     * 创建url
     */
    public String createUrl() {
        String url = "";
        Matcher matcher = pattern.matcher(this.context.getUrl());
        while (matcher.find()) {
            String key = matcher.group(1);
            String value = pathVariableMap.get(key);
            if (StringUtil.isBlank(value)) {
                //todo
                log.error("not containt key :{}", key);
            }
            url = matcher.replaceFirst(value);
            matcher = pattern.matcher(url);
        }
        return url;
    }

    public <T> HttpEntity<T> createEntity() {
        return null;
    }


    public <T> void createHeader(){

    }


}
