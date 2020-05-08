package com.sanhuo.persistent.reflection.property;

import com.sanhuo.persistent.exception.ReflectionException;
import com.sanhuo.persistent.reflection.ReflectorConstant;

import java.util.Locale;

/**
 * get/set方法的属性 处理类
 */
public class PropertyNamer implements ReflectorConstant {

    /**
     * 从方法名获取属性名
     *
     * @param name get/set的方法名
     * @return 方法对应的属性名
     */

    public static String methodToProperty(String name) {
        //去掉get|set
        if (isProperty(name)) {
            name = name.substring(3);
        } else {
            throw new ReflectionException("Error parsing property name '" + name + "'.  Didn't start with 'get' or 'set'.");
        }
        //如果只有1个字母-->转为小写
        //如果大于1个字母，第二个字母非大写-->转为小写
        //String uRL -->String getuRL() {
        if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    //是否是属性
    public static boolean isProperty(String name) {
        //必须以get|set开头
        return name.startsWith(GET) || name.startsWith(SET);
    }

    //是否是getter
    public static boolean isGetter(String name) {
        return name.startsWith(GET) && name.length() > GET.length();
    }


    //是否是setter
    public static boolean isSetter(String name) {
        return name.startsWith(SET) && name.length() > SET.length() ;
    }

}
