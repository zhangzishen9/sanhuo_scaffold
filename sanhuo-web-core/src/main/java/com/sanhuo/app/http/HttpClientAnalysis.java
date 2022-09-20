package com.sanhuo.app.http;

import com.google.common.collect.ImmutableList;
import com.sanhuo.app.http.annotation.Header;
import com.sanhuo.app.http.annotation.Headers;
import com.sanhuo.app.http.annotation.HttpClient;
import com.sanhuo.app.http.annotation.HttpClientConfig;
import jdk.nashorn.internal.ir.annotations.Immutable;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/19 12:50
 **/
public class HttpClientAnalysis {

    private static List<Class> HTTP_API_ANNOTATIONS = ImmutableList.of(GetMapping.class, PutMapping.class, DeleteMapping.class, PostMapping.class);

    public static Map<String, HttpClientMethodContext> doAnalysis(Class target) {
        Map<String, HttpClientMethodContext> result = new HashMap<>();
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
        List<String> methodParamList = new ArrayList<>();

        for (Parameter parameter : parameters) {
            RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
            String name = requestParam == null ? parameter.getName() : requestParam.name();
            methodParamList.add(parameter.getName());
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
            paramMap.put(name, null);
        }
        context.setParamMap(paramMap);
        context.setBodyMap(bodyMap);
    }

    private static Map<String, String> getHeadersMap(Headers headers) {
        Map<String, String> headersMap = new HashMap<>();
        if (headers != null) {
            Header[] values = headers.values();
            for (Header header : values) {
                headersMap.put(header.name(), header.value());
            }
        }
        return headersMap;
    }

}
