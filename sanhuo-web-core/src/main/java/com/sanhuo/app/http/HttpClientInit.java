package com.sanhuo.app.http;

import com.sanhuo.app.annotation.SanhuoApplication;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * 在springboot启动后执行
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 19:14
 */
@Component
@Order(2)
public class HttpClientInit implements ImportBeanDefinitionRegistrar {

    private static final String BACK_PACKAGE = SanhuoApplication.PACKAGE_SCAN_PREFIX + ".http";


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        HttpClientScanner scanner = new HttpClientScanner(beanDefinitionRegistry);
        scanner.doScan(BACK_PACKAGE);
    }


}
