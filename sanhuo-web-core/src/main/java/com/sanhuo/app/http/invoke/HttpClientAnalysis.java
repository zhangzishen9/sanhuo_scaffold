package com.sanhuo.app.http.invoke;

import com.google.common.collect.ImmutableList;
import com.sanhuo.app.http.annotation.Header;
import com.sanhuo.app.http.annotation.Headers;
import com.sanhuo.app.http.annotation.HttpClient;
import com.sanhuo.app.http.annotation.ResultCheck;
import com.sanhuo.app.http.invoke.HttpClientMethodContext;
import com.sanhuo.app.http.invoke.helper.HeaderAnalysisHelper;
import com.sanhuo.app.http.invoke.helper.MethodParamAnalysisHelper;
import com.sanhuo.app.http.invoke.resultcheck.HttpClientApiResultCheck;
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
        Map<String, String> headersMap = new HeaderAnalysisHelper(headers).analaysis();
        Method[] methods = target.getMethods();
        HttpClientApiResultCheck targetResultCheck = createResultCheck((ResultCheck) target.getAnnotation(ResultCheck.class));
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            HttpClientMethodContext context = new HttpClientMethodContext();
            //解析headers，方法上的优先
            Map<String, String> methodHeaderMap = new HashMap<>(headersMap);
            methodHeaderMap.putAll(new HeaderAnalysisHelper(method.getAnnotation(Headers.class)).analaysis());
            context.setHeaders(methodHeaderMap);
            context.setReturnType(method.getReturnType());
            //结果校验类，方法上的优先
            HttpClientApiResultCheck methodResultCheck = createResultCheck((ResultCheck) method.getAnnotation(ResultCheck.class));
            context.setResultCheck(methodResultCheck != null ? methodResultCheck : targetResultCheck);
            for (Annotation annotation : annotations) {
                if (HTTP_API_ANNOTATIONS.contains(annotation.getClass())) {
                    RequestMapping requestMapping = annotation.getClass().getAnnotation(RequestMapping.class);
                    context.setMethod(requestMapping.method()[0]);
                    context.setUrl(prefix + requestMapping.path()[0]);
                    context.setParams(new MethodParamAnalysisHelper(method).analysis());

                    result.put(method.getName(), context);
                }
            }

        }
        return result;
    }

    private static HttpClientApiResultCheck createResultCheck(ResultCheck resultCheck) {
        if (resultCheck != null) {
            //生成结果检查类
            try {
                return resultCheck.values().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                log.error("初始化http结果检查类出错 : {}", e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
        return null;
    }


}
