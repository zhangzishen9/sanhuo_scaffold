package com.sanhuo.persistent.mapper;

import com.sanhuo.persistent.mapper.annotation.Mapper;
import com.sanhuo.persistent.mapper.proxy.MapperProxyFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * MapperScanHandler
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 21:57
 */
public class MapperScanHandler extends ClassPathBeanDefinitionScanner {

    public MapperScanHandler(BeanDefinitionRegistry registry, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter(new AnnotationTypeFilter(Mapper.class));
        //调用spring的扫描
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        //动态生成代理类
        registerMapper(beanDefinitionHolders);
        return beanDefinitionHolders;
    }

    /**
     * 动态生成代理类
     *
     * @param beanDefinitionHolders
     */
    private void registerMapper(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        beanDefinitionHolders.stream().forEach(beanDefinitionHolder -> {
            GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            //获取接口
            String mapper = genericBeanDefinition.getBeanClassName();
            //构造函数的参数
            genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapper);
            // 修改BeanClass
            genericBeanDefinition.setBeanClass(MapperProxyFactory.class);
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
