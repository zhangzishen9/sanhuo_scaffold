package com.sanhuo.persistent.mapper;

import com.sanhuo.persistent.mapper.annotation.MapperScan;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;

/**
 * MapperScanHandler
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 21:29
 */
public class MapperScanRegister implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    private final String PACKAGE = "value";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        //要扫描的包
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(MapperScan.class.getName()));
        if (annotationAttributes != null) {
            String[] basePackages = annotationAttributes.getStringArray(PACKAGE);
            //类扫描实现
            MapperScanHandler scanHandle = new MapperScanHandler(beanDefinitionRegistry, false);
            scanHandle.setResourceLoader(resourceLoader);
            scanHandle.doScan(basePackages);

        }
    }
}
