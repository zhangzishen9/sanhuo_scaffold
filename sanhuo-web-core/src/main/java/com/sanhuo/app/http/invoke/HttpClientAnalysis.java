package com.sanhuo.app.http.invoke;

import com.google.common.collect.ImmutableList;
import com.sanhuo.app.http.annotation.Header;
import com.sanhuo.app.http.annotation.Headers;
import com.sanhuo.app.http.annotation.HttpClient;
import com.sanhuo.app.http.invoke.HttpClientMethodContext;
import com.sanhuo.commom.basic.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/19 12:50
 **/
@Slf4j
public class HttpClientAnalysis {

    private static final List<Class<? extends Annotation>> HTTP_API_ANNOTATIONS = ImmutableList.of(GetMapping.class, PutMapping.class, DeleteMapping.class, PostMapping.class);

    public static Map<String, HttpClientMethodContext> doAnalysis(Class target) {
        Map<String, HttpClientMethodContext> result = new HashMap<String, HttpClientMethodContext>();
        HttpClient httpClient = (HttpClient) target.getAnnotation(HttpClient.class);
        //获取api前缀
        String prefix = httpClient.value();
        //获取通用请求头
        Headers headers = (Headers) target.getAnnotation(Headers.class);
        Map<String, String> headersMap = getHeadersMap(headers);
        Method[] methods = target.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            HttpClientMethodContext context = new HttpClientMethodContext();
            //解析headers
            Map<String, String> methodHeaderMap = getHeadersMap(method.getAnnotation(Headers.class));
            methodHeaderMap.putAll(headersMap);
            context.setHeaders(methodHeaderMap);
            context.setReturnType(method.getReturnType());
            for (Annotation annotation : annotations) {
                if (HTTP_API_ANNOTATIONS.contains(annotation.getClass())) {
                    RequestMapping requestMapping = annotation.getClass().getAnnotation(RequestMapping.class);
                    context.setMethod(requestMapping.method()[0]);
                    context.setUrl(prefix + requestMapping.path()[0]);
                    doAnalysisParam(method, context);
                    result.put(method.getName(), context);
                }
            }
        }
        return result;
    }


    private static void doAnalysisParam(Method method, HttpClientMethodContext context) {
        Parameter[] parameters = method.getParameters();
        Map<String, Object> bodyMap = new HashMap<>();
        Map<String, Object> paramMap = new HashMap<>();
        Map<String, Object> pathVariableMap = new HashMap<>();
        List<String> methodParamList = new ArrayList<>();

        for (Parameter parameter : parameters) {
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            String name = requestParam == null ? parameter.getName() : requestParam.name();
            methodParamList.add(parameter.getName());
            //是否是body
            RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
            if (requestBody != null) {
                Class paramType = parameter.getType();
                PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(paramType);
                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    if ("class".equals(propertyDescriptor.getName())) {
                        continue;
                    }
                    bodyMap.put(propertyDescriptor.getName(), null);
                }
                continue;
            }
            //是否是PathVariable
            PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
            if (pathVariable != null) {
                pathVariableMap.put(parameter.getName(), pathVariable.name());
            }

            paramMap.put(name, null);
        }
        context.setParamMap(paramMap);
        context.setBodyMap(bodyMap);
    }


    /**
     * 解析方法参数
     * #{@link PathVariable}
     *
     * @return
     */
    private static Map<String, String> getPathValueMap() {

    }

    /**
     * 解析请求头
     *
     * @param headers
     * @return
     */
    private static Map<String, String> getHeadersMap(Headers headers) {
        Map<String, String> headersMap = new HashMap<>();
        if (headers != null) {
            Header[] values = headers.values();
            for (Header header : values) {
                if (StringUtil.isBlank(header.values())) {
                    if (StringUtil.isBlank(header.name()) || StringUtil.isBlank(header.value())) {
                        log.warn("haederParam name or value is blank ,pleade check");
                        continue;
                    }
                    headersMap.put(header.name(), header.value());
                } else {
                    String headerValue = header.values();
                    int index = headerValue.indexOf("=");
                    if (index == -1) {
                        log.warn("please check header format,need 'name = value'");
                    }
                    String name = headerValue.substring(0, index);
                    String paramValue = headerValue.substring(index - 1);
                    headersMap.put(name, paramValue);
                }
            }
        }
        return headersMap;
    }

}