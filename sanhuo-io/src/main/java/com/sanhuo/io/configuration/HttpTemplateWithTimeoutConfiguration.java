package com.sanhuo.io.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangzs
 * @description
 * @date 2022/8/5 18:08
 **/
@Configuration
@Component
public class HttpTemplateWithTimeoutConfiguration {
// todo
//    @Value("${http.read.timeout.millis}")
    private Integer timeout = 150;

    @Bean
    public SimpleClientHttpRequestFactory httpRequestFactoryWithTimeout() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(timeout);
        factory.setConnectTimeout(timeout);
        return factory;
    }

    @Bean
    public RestTemplate restTemplateWithTimeout(SimpleClientHttpRequestFactory httpRequestFactoryWithTimeout) {
        return new RestTemplate(httpRequestFactoryWithTimeout);
    }


}
