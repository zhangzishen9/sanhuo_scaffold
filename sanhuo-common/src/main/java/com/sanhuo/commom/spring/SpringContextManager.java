package com.sanhuo.commom.spring;

import org.springframework.beans.BeansException;
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
@Component
@Order(value = 1)
public class SpringContextManager implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
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
        if (beanFactory == null) {
            beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        }
        beanFactory.registerSingleton(className, singletonBean);
    }


}
