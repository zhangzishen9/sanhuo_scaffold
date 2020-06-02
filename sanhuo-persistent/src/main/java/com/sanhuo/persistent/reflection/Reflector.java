package com.sanhuo.persistent.reflection;

import com.sanhuo.persistent.common.Constant;
import com.sanhuo.persistent.exception.ExceptionMessageConstant;
import com.sanhuo.persistent.exception.ExceptionUtil;
import com.sanhuo.persistent.exception.ReflectionException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 反射器,一个类的所有属性+所有方法+所有构造器
 */
public class Reflector {


    /**
     * 获取无参构造器
     */
    public static Constructor getNoArgConstructor(Class clazz) {
        Constructor noArgConstructor = null;
        try {
            noArgConstructor = clazz.getConstructor(null);
        } catch (NoSuchMethodException e) {
            //没有无惨构造器,不调用exceptionUtil的throwException()方法,怕递归爆栈..
            throw new ReflectionException(
                    ExceptionUtil.getExceptionMessage(ExceptionMessageConstant.NO_ARG_CONSTRUCTOR_UN_EXIST, clazz.getSimpleName()));
        }
        return noArgConstructor;
    }

    /**
     * 获取对应参数的构造器
     */
    public static Constructor getConstructor(Class clazz, Class... constructorParams) {
        Constructor constructor = null;
        try {
            constructor = clazz.getConstructor(constructorParams);
        } catch (NoSuchMethodException e) {
            //没有对应参数的构造器
            StringBuilder params = new StringBuilder();
            for (Class constructorParam : constructorParams) {
                params.append(constructorParam.getSimpleName()).append(Constant.COMMA);
            }
            ExceptionUtil.throwException(ReflectionException.class, ExceptionMessageConstant.NO_ARG_CONSTRUCTOR_UN_EXIST, clazz.getSimpleName(),
                    clazz.getSimpleName(), params.substring(0, params.lastIndexOf(Constant.COMMA)).toString());
        }
        return constructor;
    }


    /**
     * 获取class里面的所有方法,包括父类的
     */
    public static Method[] getClassMethods(Class<?> clazz) {
        Map<String, Method> methodsMap = new HashMap<>();
        Class<?> currentClazz = clazz;
        //遍历 类+ 父类 的所有方法
        while (currentClazz != null) {
            //添加类自己的方法
            addMethods(methodsMap, clazz.getDeclaredMethods());
            //添加当前类的实现的所有接口的方法
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                addMethods(methodsMap, anInterface.getMethods());
            }
            //当前类替换为父类
            currentClazz = currentClazz.getSuperclass();
        }
        Collection<Method> methods = methodsMap.values();
        return methods.toArray(new Method[methods.size()]);
    }


    /**
     * 获取类所有字段
     */
    public static List<Field> getClassField(Class<?> clazz) {
        List<Field> fields = new LinkedList<>();
        Class<?> currentClazz = clazz;
        //遍历 类+ 父类 的所有字段
        while (currentClazz != null) {
            //添加当前类的字段
            fields.addAll(Arrays.asList(currentClazz.getDeclaredFields()));
            Class<?>[] interfaces = clazz.getInterfaces();
            for (Class<?> anInterface : interfaces) {
                //添加当前类的实现的所有接口的字段
                fields.addAll(Arrays.asList(anInterface.getDeclaredFields()));
            }
            //当前类替换为父类
            currentClazz = currentClazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 获取方法签名
     *
     * @return [ 返回值 # 方法名:参数1,参数2 ]
     */
    public static String getSignature(Method method) {
        StringBuilder sb = new StringBuilder();
        //方法的返回值
        Class<?> returnType = method.getReturnType();
        if (returnType != null) {
            sb.append(returnType.getName()).append('#');
        }
        //方法名
        sb.append(method.getName());
        //方法参数
        Class<?>[] parameters = method.getParameterTypes();
        for (int i = 0; i < parameters.length; i++) {
            if (i == 0) {
                sb.append(':');
            } else {
                sb.append(',');
            }
            sb.append(parameters[i].getName());
        }
        return sb.toString();
    }

    /**
     * 获取类所有字段的名称
     */
    public static List<String> getClassFieldsName(Class<?> clazz) {
        return getClassField(clazz).stream().map(field -> field.getName()).collect(Collectors.toList());
    }


    private static void addMethods(Map<String, Method> methodsMap, Method[] methods) {
        for (Method method : methods) {
            //取得签名  -> [ 返回值 # 方法名:参数1,参数2 ]的格式
            String signature = getSignature(method);
            if (!methodsMap.containsKey(signature)) {
                method.setAccessible(true);
                //存放到map里,key为 签名 value 为 Method
                methodsMap.put(signature, method);
            }
        }
    }


}
