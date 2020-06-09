package com.sanhuo.commom.basic;

import java.util.*;

/**
 * <p>
 * object工具类
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/24:11:15
 */
public class ObjectUtil {

    /**
     * 所有的基础类型和对应的包装类型
     */
    private static final List<Class> PRIMITIVE_CLASS_LIST = new ArrayList<>(17);

    /**
     * 基础类型和包装类型的映射
     */
    private static final Map<Class, Class> PRIMITIVE_MAPPED_MAP = new HashMap<>(9);

    static {
        /**
         * 所有的基础类型和对应的包装类型
         */
        PRIMITIVE_CLASS_LIST.add(String.class);
        PRIMITIVE_CLASS_LIST.add(Double.class);
        PRIMITIVE_CLASS_LIST.add(Integer.class);
        PRIMITIVE_CLASS_LIST.add(Boolean.class);
        PRIMITIVE_CLASS_LIST.add(Float.class);
        PRIMITIVE_CLASS_LIST.add(Long.class);
        PRIMITIVE_CLASS_LIST.add(Character.class);
        PRIMITIVE_CLASS_LIST.add(Short.class);
        PRIMITIVE_CLASS_LIST.add(Byte.class);
        PRIMITIVE_CLASS_LIST.add(double.class);
        PRIMITIVE_CLASS_LIST.add(int.class);
        PRIMITIVE_CLASS_LIST.add(boolean.class);
        PRIMITIVE_CLASS_LIST.add(float.class);
        PRIMITIVE_CLASS_LIST.add(long.class);
        PRIMITIVE_CLASS_LIST.add(char.class);
        PRIMITIVE_CLASS_LIST.add(short.class);
        PRIMITIVE_CLASS_LIST.add(byte.class);
        /**
         * 基础类型和包装类型的映射
         */
        PRIMITIVE_MAPPED_MAP.put(String.class, String.class);
        PRIMITIVE_MAPPED_MAP.put(double.class, Double.class);
        PRIMITIVE_MAPPED_MAP.put(int.class, Integer.class);
        PRIMITIVE_MAPPED_MAP.put(boolean.class, Boolean.class);
        PRIMITIVE_MAPPED_MAP.put(float.class, Float.class);
        PRIMITIVE_MAPPED_MAP.put(long.class, Long.class);
        PRIMITIVE_MAPPED_MAP.put(char.class, Character.class);
        PRIMITIVE_MAPPED_MAP.put(short.class, Short.class);
        PRIMITIVE_MAPPED_MAP.put(byte.class, Byte.class);
    }

    /**
     * 获取基础类型的包装类型
     *
     * @param basicPrimitiveClass
     * @return
     */
    public static Class getPackageClass(Class basicPrimitiveClass) {
        if (PRIMITIVE_MAPPED_MAP.containsKey(basicPrimitiveClass)) {
            return PRIMITIVE_MAPPED_MAP.get(basicPrimitiveClass);
        } else {
            //todo 抛异常
        }
        return null;
    }

    /**
     * 判断是否是基本类型
     *
     * @param type
     * @return
     */
    public static boolean isPrimitive(Class type) {
        String classType = type.getTypeName();
        return isPrimitive(classType);

    }

    /**
     * 判断是否是基本类型
     *
     * @param typeName
     * @return
     */
    public static boolean isPrimitive(String typeName) {
        for (Class primitive : PRIMITIVE_CLASS_LIST) {
            if (primitive.getTypeName().equals(typeName)) {
                return true;
            }
        }
        return false;
    }
}
