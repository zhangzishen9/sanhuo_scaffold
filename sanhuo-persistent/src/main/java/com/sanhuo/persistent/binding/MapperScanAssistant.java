package com.sanhuo.persistent.binding;

import com.sanhuo.persistent.binding.annotation.Entity;
import com.sanhuo.persistent.binding.annotation.Mapper;
import com.sanhuo.persistent.binding.property.TableProperty;
import com.sanhuo.persistent.builder.config.annotation.MapperAnnotationBuilder;
import com.sanhuo.persistent.session.Configuration;

import java.lang.annotation.Annotation;

/**
 * mapper扫描时处理一下相关事宜的助理类
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 21:29
 */
public class MapperScanAssistant {

    private final Configuration configuration;

    public MapperScanAssistant(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * 验证mapper映射的实体是否有效(是否继承了Entity和Table注解),顺便解析entity里的属性到configuration里
     */
    public Boolean verifyMappedEntityValid(Class target) {
        Mapper mapperAnnotation
                = (Mapper) target.getAnnotation(Mapper.class);
        if (mapperAnnotation != null) {
            //获取映射的实体类
            Class entity = mapperAnnotation.value();
            //判断实体类上是否有@Entity这个注解
            Annotation entityAnnotation
                    = entity.getAnnotation(Entity.class);
            if (entityAnnotation != null) {

                /**
                 * 解析mapper里面的方法
                 */

                //加入mapper和实体的映射关系到缓存里
                this.configuration.addMappedEntity(target, entity);

                //解析mapper映射的实体
                this.handlerEntity(entity);

                //解析mapper里面的方法
                MapperAnnotationBuilder mapperAnnotationBuilder
                        = new MapperAnnotationBuilder(this.configuration, target);
                mapperAnnotationBuilder.parse();
                return true;
            } else {
                //todo 抛异常
            }
        }
        return false;
    }

    /**
     * 解析实体
     *
     * @param entity
     */
    public void handlerEntity(Class entity) {
        this.configuration.addEntityParsing(entity, TableProperty.init(entity, this.configuration));
    }


}
