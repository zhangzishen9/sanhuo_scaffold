package com.sanhuo.app.http;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/19 12:50
 **/
public class HttpClientAnalysis {
    /**
     * 类级别的配置
     */
    private static final Map<String, HttpClientMethodContext> HTTP_CLINET_CLASS_CONTEXT = new ConcurrentHashMap<>();

    public static Map<String, HttpClientMethodContext> doAnalysis(Class target) {
        Map<String, HttpClientMethodContext> contextMap = new HashMap<>();
        //todo
        return contextMap;
    }

}
