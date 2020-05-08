package com.sanhuo.persistent;

import com.sanhuo.commom.manager.SpringManager;
import com.sanhuo.persistent.binding.MapperScanHandler;
import com.sanhuo.persistent.binding.MapperScanRegister;
import com.sanhuo.persistent.session.SqlSessionFactory;
import com.sanhuo.persistent.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * 在springboot启动后执行
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 19:14
 */
@Component
@Order(value = 1)
public class SanHuoPersistentInit implements CommandLineRunner, ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    @Autowired
    private ApplicationContext applicationContext;

    private ResourceLoader resourceLoader;

    private final String PACKAGE = "value";

    @Override
    public void run(String... args) {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //创建sqlSessionFactory
        SqlSessionFactory factory = builder.builder();
        //把sqlSessionFactory注册到容器里(单例)
        SpringManager.registerSingleton(factory);

        System.out.println(1);
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }



    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //创建configuration
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        //创建sqlSessionFactory
        SqlSessionFactory factory = builder.builder();
        //把sqlSessionFactory注册到容器里(单例)
        SpringManager.registerSingleton(factory);
        //扫描mapper并注入到容器内,
        MapperScanRegister.registMapper(annotationMetadata,beanDefinitionRegistry,resourceLoader);
    }

}
