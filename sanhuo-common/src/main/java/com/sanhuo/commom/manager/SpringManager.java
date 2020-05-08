package com.sanhuo.commom.manager;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 容器管理工具
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 19:34
 */
@Component
public class SpringManager {

    private static ApplicationContext applicationContext;

    public SpringManager(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> void registerSingleton(Object singletonBean) {
        if (singletonBean == null) {
            return;
        }
        String className = singletonBean.getClass().getName();
        DefaultListableBeanFactory beanFactory = null;
        if (beanFactory == null) {
            beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        }
        BeanDefinition beanDefinition = new GenericBeanDefinition();

        beanDefinition.setBeanClassName(className);
        beanFactory.registerSingleton(className, singletonBean);
    }
}
