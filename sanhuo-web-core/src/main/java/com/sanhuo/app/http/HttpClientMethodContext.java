package com.sanhuo.app.http;

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

    private String url;

    private RequestMethod method;

    private Map<String, String> headers;

    private Map<String, Object> bodyMap;

    private Map<String, Object> paramMap;

    private List<String> methodParamList;

    private Class returnType;


}
