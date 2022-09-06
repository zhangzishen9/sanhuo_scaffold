package com.sanhuo.io.protocol;

import com.sanhuo.io.enums.ProtocolEnum;
import jdk.internal.net.http.common.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Target;

/**
 * @author zhangzs
 * @description
 * @date 2022/9/6 12:12
 **/
@Slf4j
public class ProtocolConfiguration implements ImportBeanDefinitionRegistrar, ResourceLoaderAware, BeanFactoryAware {

    private ResourceLoader resourceLoader;

    private BeanFactory beanFactory;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }


    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //获取需要启动的协议
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableProtocol.class.getName()));
        ProtocolEnum[] protocolList = (ProtocolEnum[]) annotationAttributes.get("values");
        for (ProtocolEnum protocolType : protocolList) {
            Class<? extends ProtocolSupport> target = protocolType.getProtocol();
            try {
                //放进去容器
                ProtocolSupport protocolSupport = target.getDeclaredConstructor().newInstance();
                ((DefaultListableBeanFactory) beanFactory).registerSingleton(target.getSimpleName(), protocolSupport);
                log.info("do init protocol : {} success !", target.getName());
            } catch (Exception e) {
                log.error("do init protocol : {} error : {}", target.getName(), e.getMessage());
            }
        }
    }

}

