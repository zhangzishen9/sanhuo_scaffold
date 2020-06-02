package com.sanhuo.persistent.binding;

import com.sanhuo.commom.manager.SpringManager;
import com.sanhuo.persistent.session.SqlSessionFactory;
import com.sanhuo.persistent.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import com.sanhuo.persistent.EnableSanHuoPersistent;

import javax.annotation.PostConstruct;

/**
 * 相当于在springboot启动的时候就去扫描对应的mapper
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 21:29
 */
public class MapperScanRegister {

    private static final String PACKAGE = "value";



    public static void registerMapper(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry, ResourceLoader resourceLoader) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableSanHuoPersistent.class.getName()));
        if (annotationAttributes != null) {
            String[] basePackages = annotationAttributes.getStringArray(PACKAGE);
            //类扫描实现
            MapperScanHandler scanHandle = new MapperScanHandler(beanDefinitionRegistry, false);
            scanHandle.setResourceLoader(resourceLoader);
            scanHandle.doScan(basePackages);

        }
    }

}
