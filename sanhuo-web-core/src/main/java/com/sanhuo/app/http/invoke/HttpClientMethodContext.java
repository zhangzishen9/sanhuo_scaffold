package com.sanhuo.app.http.invoke;

import com.sanhuo.app.http.invoke.param.HttpMethodParam;
import com.sanhuo.app.http.invoke.resultcheck.HttpClientApiResultCheck;
import lombok.*;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/19 13:47
 **/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HttpClientMethodContext {

    /**
     * 请求url
     */
    private String url;

    /**
     * 请求方法
     */
    private RequestMethod method;

    /**
     * headers 列表
     */
    private Map<String, String> headers;

    /**
     * 参数列表
     */
    private List<HttpMethodParam> params;

    /**
     * 方法参数
     */
    private List<String> methodParamList;

    /**
     * 结果返回值
     */
    private Class returnType;

    /**
     * 结果校验
     */
    private HttpClientApiResultCheck resultCheck;


}
