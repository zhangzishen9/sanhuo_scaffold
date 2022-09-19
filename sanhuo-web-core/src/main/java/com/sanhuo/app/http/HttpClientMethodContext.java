package com.sanhuo.app.http;

import lombok.*;
import org.springframework.http.HttpMethod;

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

    private HttpMethod method;

    private Map<String, String> headers;

    private List<String> params;
}
