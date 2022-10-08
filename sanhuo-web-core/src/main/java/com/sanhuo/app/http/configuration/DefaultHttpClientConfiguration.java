package com.sanhuo.app.http.configuration;

import com.sanhuo.app.http.invoke.impl.HttpTemplateWithTimeout;
import com.sanhuo.app.http.invoke.impl.ResttemplateInvoker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangzs
 * @description
 * @date 2022/10/8 20:04
 **/
@Configuration
public class DefaultHttpClientConfiguration {

    @Bean
    public RestTemplate restTemplateWithTimeout() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(300);
        return new RestTemplate(factory);
    }

    @Bean
    public ResttemplateInvoker defaultHttpClientInvoke(RestTemplate restTemplateWithTimeout){
        return new ResttemplateInvoker(new HttpTemplateWithTimeout(restTemplateWithTimeout));
    }
}
