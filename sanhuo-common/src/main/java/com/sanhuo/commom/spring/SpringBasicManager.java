package com.sanhuo.commom.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 基于BeanFactory的容器管理工具
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/3:21:06
 */
@Component
@Order(1)
public class SpringBasicManager implements BeanFactoryAware {

    private static DefaultListableBeanFactory beanFactory;


    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
    }

    /**
     * 从容器里获取bean
     *
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
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
        beanFactory.registerSingleton(className, singletonBean);
    }
}
