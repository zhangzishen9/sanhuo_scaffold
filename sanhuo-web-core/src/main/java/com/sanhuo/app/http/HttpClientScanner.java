package com.sanhuo.app.http;

import com.sanhuo.app.http.annotation.HttpClient;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * 相当于在springboot启动的时候就去扫描对应的http_proxy
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 21:29
 */
public class HttpClientScanner extends ClassPathBeanDefinitionScanner {


    private static final boolean USE_DEFAULT_FILTERS = false;

    public HttpClientScanner(BeanDefinitionRegistry registry) {
        super(registry, USE_DEFAULT_FILTERS);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(HttpClient.class));
        //调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        //动态生成代理类
        registerProxy(beanDefinitionHolders);
        return beanDefinitionHolders;
    }

    /**
     * 动态生成代理类
     *
     * @param beanDefinitionHolders
     */
    private void registerProxy(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        beanDefinitionHolders.stream().forEach(beanDefinitionHolder -> {
            GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            //mapper类
            Class httpClientTarget = null;
            try {
                httpClientTarget = Class.forName(genericBeanDefinition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                //todo
                e.printStackTrace();
            }
            //构造函数的参数
            genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(httpClientTarget);
            // 修改BeanClass
            genericBeanDefinition.setBeanClass(HttpClientProxyFactory.class);
            genericBeanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);

        });
    }


    /**
     * 只返回接口
     *
     * @param beanDefinition
     * @return
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

}
