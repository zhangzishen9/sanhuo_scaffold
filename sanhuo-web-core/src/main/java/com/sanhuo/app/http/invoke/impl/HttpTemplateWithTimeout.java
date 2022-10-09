package com.sanhuo.app.http.invoke.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.net.SocketTimeoutException;
import java.util.function.Supplier;

/**
 * @author zhangzs
 * @description
 * @date 2022/8/5 18:08
 **/
@Slf4j
@AllArgsConstructor
public class HttpTemplateWithTimeout {

    private RestTemplate restTemplateWithTimeout;

    /**
     * post请求
     *
     * @param url          请求url
     * @param request      请求头、请求体
     * @param responseType 相应的返回结果类型
     * @return
     */
    public <T> ResponseEntity<T> postWithTimeout(String url, HttpEntity<T> request, Class<T> responseType) {
        return this.exec(url, () -> restTemplateWithTimeout.postForEntity(url, request, responseType));
    }

    /**
     * put请求
     *
     * @param url          请求url
     * @param request      请求头、请求体
     * @param responseType 相应的返回结果类型
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> putWithTimeout(String url, HttpEntity<T> request, Class<T> responseType) {
        return this.exec(url, () -> restTemplateWithTimeout.exchange(url, HttpMethod.PUT, request, responseType));
    }

    /**
     * delete <br/>
     * 暂时不支持请求体，因为rest下的delete方法基本只用上#{@link org.springframework.web.bind.annotation.PathVariable}
     *
     * @param url          参数url
     * @param responseType 返回结果类型
     */
    public <T> ResponseEntity<T> deleteWithTimeout(String url, Class<T> responseType) {
        return this.exec(url, () -> restTemplateWithTimeout.exchange(url, HttpMethod.DELETE, null, responseType));
    }

    /**
     * get 方法
     *
     * @param url          请求url
     * @param responseType 相应的返回结果类型
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> getWithTimeout(String url, Class<T> responseType) {
        return this.exec(url, () -> restTemplateWithTimeout.getForEntity(url, responseType));
    }

    /**
     * 执行方法，捕获异常，打印请求时长、异常日志
     *
     * @param url      请求url，用于打印时长
     * @param supplier 具体执行的方法
     * @param <T>
     * @return
     */
    public <T> ResponseEntity<T> exec(String url, Supplier<ResponseEntity<T>> supplier) {
        StopWatch stopWatch = new StopWatch();
        ResponseEntity<T> result = null;
        try {
            stopWatch.start();
            result = supplier.get();
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
