package com.sanhuo.commom.utils;

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
     * 判断是否是基本类型
     *
     * @param target
     * @return
     */
    public static boolean isPrimitive(Object target) {
        String classType = ((Object) target).getClass().getTypeName();
        return isPrimitive(classType);

    }

    /**
     * 判断是否是基本类型
     *
     * @param classType
     * @return
     */
    public static boolean isPrimitive(String classType) {
        return String.class.getName().equals(classType) ||
                Double.class.getName().equals(classType) ||
                Integer.class.getName().equals(classType) ||
                Boolean.class.getName().equals(classType) ||
                Float.class.getName().equals(classType) ||
                Long.class.getName().equals(classType) ||
                Character.class.getName().equals(classType) ||
                Short.class.getName().equals(classType) ||
                Byte.class.getName().equals(classType);
    }
}
