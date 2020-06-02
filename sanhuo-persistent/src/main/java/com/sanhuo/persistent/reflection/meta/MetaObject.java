package com.sanhuo.persistent.reflection.meta;

import com.sanhuo.persistent.datasource.unpooled.UnPooledDataSource;
import com.sanhuo.persistent.exception.ExceptionMessageConstant;
import com.sanhuo.persistent.exception.ExceptionUtil;
import com.sanhuo.persistent.exception.ReflectionException;
import com.sanhuo.persistent.reflection.Reflector;
import com.sanhuo.persistent.reflection.invoker.GetterInvoker;
import com.sanhuo.persistent.reflection.invoker.Invoker;
import com.sanhuo.persistent.reflection.invoker.SetterInvoker;
import com.sanhuo.persistent.reflection.property.PropertyNamer;
import lombok.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 反射后得到的类
 */
@Data
public class MetaObject {
    /**
     * 对象本身
     */
    private Object originalObject;
    /**
     * 对象的Class
     */
    private Class<?> originalClass;
    /**
     * get方法
     */
    private Map<String, Invoker> getMethods;
    /**
     * set方法
     */
    private Map<String, Invoker> setMethods;
    /**
     * get方法的属性类型
     */
    private Map<String, Class<?>> getTypes;
    /**
     * set方法的属性类型
     */
    private Map<String, Class<?>> setTypes;
    /**
     * 所有的字段
     */
    private List<Field> fields;

    {
        getMethods = new LinkedHashMap<>();
        setMethods = new LinkedHashMap<>();
        getTypes = new LinkedHashMap<>();
        setTypes = new LinkedHashMap<>();
        fields = new LinkedList<>();
    }

    /**
     * 将对象转为元对象
     */
    public static MetaObject init(Object object) {
        //新建一个元对象
        Class<?> originalClass = object.getClass();
        MetaObject metaObject = new MetaObject();
        metaObject.setOriginalClass(originalClass);
        metaObject.setOriginalObject(object);
        metaObject.setFields(Reflector.getClassField(originalClass));
        //获取对象的所有方法
        Method[] methods = Reflector.getClassMethods(originalClass);
        metaObject.filterGetMethods(methods);
        metaObject.filterSetMethods(methods);
        return metaObject;
    }

    /**
     * 设置属性
     *
     * @param fieldName 属性名
     * @param value     属性值
     */
    public void setValue(String fieldName, Object value) {
        if (!setMethods.containsKey(fieldName) || !setTypes.containsKey(fieldName)) {
            return;
        }
        Invoker setter = setMethods.get(fieldName);
        Class fieldType = setTypes.get(fieldName);
        //属性的真实属性和value的属性不一致
        if (fieldType != value.getClass()) {
            ExceptionUtil.throwException(ReflectionException.class,
                    ExceptionMessageConstant.FIELD_TYPE_UN_MATCH, fieldName);
        }
        try {
            //set
            setter.invoke(originalObject, new Object[]{value});
        } catch (Exception e) {
            ExceptionUtil.throwException(ReflectionException.class,
                    ExceptionMessageConstant.SET_PROPRETRY_ERROR, fieldName);
        }

    }

    /**
     * 获取属性
     */
    public Object getValue(String fieldName) {
        if (!getMethods.containsKey(fieldName) || !getTypes.containsKey(fieldName)) {
            return null;
        }
        Invoker getter = getMethods.get(fieldName);
        Class returnType = getTypes.get(fieldName);
        Object value = null;
        try {
            //get
            value = getter.invoke(originalObject, new Object[]{});
            if (value == null) {
                return null;
            }
            //get出来的值得类型和真实类型不一致
            if (returnType != value.getClass()) {
                ExceptionUtil.throwException(ReflectionException.class,
                        ExceptionMessageConstant.FIELD_TYPE_UN_MATCH, fieldName);
            }
        } catch (Exception e) {
            ExceptionUtil.throwException(ReflectionException.class,
                    ExceptionMessageConstant.GET_PROPRETRY_ERROR, fieldName);
        }
        return value;
    }

    /**
     * 筛选出get方法
     */
    private void filterGetMethods(Method[] methods) {
        for (Method method : methods) {
            //方法名
            String name = method.getName();
            // 筛选出方法名是getxxx()的方法;
            if (PropertyNamer.isGetter(name)) {
                //get方法的参数列表长度为0
                if (method.getParameterTypes().length == 0) {
                    //该属性的属性名
                    String fieldName = PropertyNamer.methodToProperty(name);
                    //存放到map里面
                    Field getField = fields.stream().filter(field -> field.getName().equals(fieldName)).findAny().orElse(null);
                    //该类不存在该字段,只有方法
                    //TODO 看看有没有更好的处理
                    if (getField == null) {
                        continue;
                    }
                    getMethods.put(fieldName, GetterInvoker.init(getField));
                    //type为get方法的返回值
                    getTypes.put(fieldName, method.getReturnType());
                }
            }
        }
    }

    /**
     * 筛选出set方法
     */
    private void filterSetMethods(Method[] methods) {
        for (Method method : methods) {
            //方法名
            String name = method.getName();
            //筛选出方法名是setxxx()的方法
            if (PropertyNamer.isSetter(name)) {
                //set方法的参数列表长度为1
                if (method.getParameterTypes().length == 1) {
                    //该属性的属性名
                    String fieldName = PropertyNamer.methodToProperty(name);
                    //存放到map里面
                    Field setField = fields.stream().filter(field -> field.getName().equals(fieldName)).findAny().orElse(null);
                    //该类不存在该字段,只有方法
                    if (setField == null) {
                        continue;
                    }
                    setMethods.put(fieldName, SetterInvoker.init(setField));
                    //type为set方法的参数
                    setTypes.put(fieldName, method.getParameterTypes()[0]);
                }
            }
        }
    }


}
