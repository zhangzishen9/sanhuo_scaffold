package com.sanhuo.app.http.invoke.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.SocketTimeoutException;
import java.util.Map;

/**
 * @author zhangzs
 * @description
 * @date 2022/8/5 18:08
 **/
@Slf4j
public class HttpTemplateWithTimeout {

    @Resource
    private RestTemplate restTemplateWithTimeout;

    /**
     * @param url          请求url
     * @param request      请求头、请求体
     * @param responseType 相应的返回结果类型
     * @return
     */
    public <T> ResponseEntity<T> postWithTimeout(String url, HttpEntity<T> request, Class<T> responseType) {
        StopWatch stopWatch = new StopWatch();
        ResponseEntity<T> result = null;
        try {
            stopWatch.start();
            result = restTemplateWithTimeout.postForEntity(url, request, responseType);
            stopWatch.stop();
            log.info("调用api接口正常返回,路径:{}，用时:{} ms", url, stopWatch.getTotalTimeMillis());
        } catch (Exception e) {
            stopWatch.stop();
            if (e.getCause() != null && SocketTimeoutException.class.equals(e.getCause().getClass())) {
                log.error("调用api接口超时,路径:{}，用时 {} ms ", url, stopWatch.getTotalTimeMillis());
            } else {
                log.error("调用api异常,路径:{}，用时 {} ms ,异常:{} ", url, stopWatch.getTotalTimeMillis(), e.getMessage());
            }
        }
        return result;
    }

    public <T> ResponseEntity<T> getWithTimeout(String url, Class<T> responseType, Map<String, Object> paramsMap) {
        StopWatch stopWatch = new StopWatch();
        ResponseEntity<T> result = null;
        try {
            stopWatch.start();
            result = restTemplateWithTimeout.getForEntity(url, responseType, paramsMap);
            stopWatch.stop();
            log.info("调用api接口正常返回,路径:{}，用时:{} ms", url, stopWatch.getTotalTimeMillis());
        } catch (Exception e) {
            stopWatch.stop();
            if (e.getCause() != null && SocketTimeoutException.class.equals(e.getCause().getClass())) {
                log.error("调用api接口超时,路径:{}，用时 {} ms ", url, stopWatch.getTotalTimeMillis());
            } else {
                log.error("调用api异常,路径:{}，用时 {} ms ,异常:{} ", url, stopWatch.getTotalTimeMillis(), e.getMessage());
            }
        }
        return result;
    }


}
