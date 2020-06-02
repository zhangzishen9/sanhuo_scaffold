package com.sanhuo.commom.manager;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 容器管理工具
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 19:34
 */
@Component
@Order(value = 1)
public class SpringManager implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
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
