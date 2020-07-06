package com.sanhuo.commom.basic;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author zzs
 * @version 1.0
 * @describe 集合工具类
 * @createTime 2018.1.5
 */
public class CollectionUtil {
    /**
     * 默认连接符
     */
    public static final String CONNCETER = ",";


    /**
     * 根据连接符 把集合转成字符串
     *
     * @param collcetion
     * @param connector  连接符,如无默认为 ","
     * @return
     */
    public static String concatCollection2str(Collection<String> collcetion, String... connector) {
        String conn = isEmpty(connector) ? CollectionUtil.CONNCETER : connector[0];
        StringBuilder stringBuilder = new StringBuilder();

        for (String str : collcetion) {
            if (stringBuilder.length() > 0) {
                stringBuilder.append(conn);
            }
            stringBuilder.append(str);
        }

        return stringBuilder.toString();
    }

    /**
     * 判断返回类型是不是集合
     *
     * @param type
     * @return
     */
    public static boolean isCollection(Class type) {
        return Collection.class.isAssignableFrom(type);
    }


    /**
     * 判断是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null) || (array.length <= 0);
    }

    public static boolean isEmpty(Object list) {
        Collection<Object> collection = (Collection) list;
        return (collection == null) || (collection.size() <= 0);
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return (collection == null) || (collection.size() <= 0);
    }


}
