package com.sanhuo.persistent.binding;

import com.sanhuo.commom.manager.SpringBasicManager;
import com.sanhuo.commom.manager.SpringManager;
import com.sanhuo.persistent.binding.annotation.Mapper;
import com.sanhuo.persistent.binding.proxy.MapperProxyFactory;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.persistent.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.Set;

/**
 * 扫描mapper并放到ioc容器
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 21:57
 */
public class MapperScanHandler extends ClassPathBeanDefinitionScanner {

    private final Configuration configuration;

    private final MapperScanAssistant mapperScanAssistant;


    public MapperScanHandler(BeanDefinitionRegistry registry, Configuration configuration, boolean useDefaultFilters) {
        super(registry, useDefaultFilters);
        this.configuration = configuration;
        this.mapperScanAssistant = new MapperScanAssistant(this.configuration);
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
            //mapper类
            Class mapperClass = null;
            try {
                mapperClass = Class.forName(genericBeanDefinition.getBeanClassName());
            } catch (ClassNotFoundException e) {
                //todo
                e.printStackTrace();
            }
            //校验mapper是否合法,合法才生成代理类
            if (mapperScanAssistant.verifyMappedEntityValid(mapperClass)) {
                //获取接口
                String mapper = genericBeanDefinition.getBeanClassName();
                //构造函数的参数
                genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapper);
                // 修改BeanClass
                genericBeanDefinition.setBeanClass(MapperProxyFactory.class);
                genericBeanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);

            }
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
