package com.sanhuo.app.http;

import org.springframework.cloud.openfeign.FeignClient;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/19 12:50
 **/
public class HttpClientAnalysis {

    public static Map<String,HttpClientMethodContext> doAnalysis(Class target){
        Map<String,HttpClientMethodContext> contextMap = new HashMap<>();
        //todo
        return contextMap;
    }

}
