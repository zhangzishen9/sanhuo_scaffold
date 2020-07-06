package com.sanhuo.persistent.reflection;

import com.sanhuo.commom.basic.ObjectUtil;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 对象工程
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/9:21:57
 */
public class ObjectFactory {

    public ObjectFactory() {
        //初始化ObjectUtil
        try {
            Class.forName(ObjectUtil.class.getName(), true, this.getClass().getClassLoader());
        } catch (Exception e) {
            //todo 处理
        }

    }

    /**
     * 实例化Class
     *
     * @param type
     * @return
     */
    public Object create(Class<?> type) {
        Object newInstance = null;
        try {
            if (ObjectUtil.isPrimitive(type)) {
                //如果是基本类型则实例化其的包装类型
                Class PackAgeClass = ObjectUtil.getPackageClass(type);
                newInstance = PackAgeClass.newInstance();
            } else {
                //不是基本类型直接实例化clazz
                newInstance = type.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
            //todo 处理
        }
        return newInstance;
    }

    /**
     * 以指定的构造函数实例化Class,最后都是调用Constructor.newInstance();
     *
     * @param type
     * @return
     */
    public Object create(Class<?> type, List<Class<?>> constructorArgTypes, List<Object> constructorArgs) {
        Object newInstance = null;
        Class finalType = type;
        try {
            //构造函数
            Constructor constructor;
            if (ObjectUtil.isPrimitive(type)) {
                //如果是基本类型则实例化其的包装类型
                finalType = ObjectUtil.getPackageClass(type);
            }
            //如果没有传入constructor，调用空构造函数，核心是调用Constructor.newInstance
            if (constructorArgTypes == null || constructorArgs == null) {
                constructor = finalType.getDeclaredConstructor();
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                return constructor.newInstance();
            }
            //如果传入constructor，调用传入的构造函数
            constructor = type.getDeclaredConstructor(constructorArgTypes.toArray(new Class[constructorArgTypes.size()]));
            if (!constructor.isAccessible()) {
                constructor.setAccessible(true);
            }
            return constructor.newInstance(constructorArgs.toArray(new Object[constructorArgs.size()]));
        } catch (Exception e) {
            //todo 处理
        }
        return newInstance;
    }
}
