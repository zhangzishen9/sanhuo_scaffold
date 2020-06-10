package com.sanhuo.commom.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 基于ApplicationContext容器管理工具
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 19:34
 */
@Order(1)
public class SpringContextManager implements ApplicationContextAware, BeanFactoryAware {

    private static ApplicationContext applicationContext;
    private static BeanFactory beanFactory;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 从容器里获取bean
     *
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 注册单例bean到ioc容器里
     *
     * @param <T>
     */
    public static <T> void registerSingleton(Object singletonBean) {
        if (singletonBean == null) {
            return;
        }
        String className = singletonBean.getClass().getName();
        DefaultListableBeanFactory beanFactory = null;
        if (applicationContext != null) {
            beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        } else {            beanFactory = (DefaultListableBeanFactory) beanFactory;
        }
        beanFactory.registerSingleton(className, singletonBean);
    }


}
