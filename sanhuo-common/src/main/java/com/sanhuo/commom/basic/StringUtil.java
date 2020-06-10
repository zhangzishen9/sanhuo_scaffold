package com.sanhuo.commom.basic;

import java.util.UUID;

/**
 * @author zzs
 * @version 1.0
 * @describe 字符串工具类
 * @createTime 2018.1.5
 */
public class StringUtil {

    /**
     * 空字符串
     */
    public static final String EMPTY_STRING = "";
    /**
     * 下划线字符串
     */
    private static final String UNDERLINE = "_";

    /**
     * 判断输入的string是否为空
     *
     * @param string
     * @return
     */
    public static Boolean isBlank(String string) {
        return string == null || EMPTY_STRING.equals(string.trim());
    }

    /**
     * 判断输入的string是否为空
     *
     * @param string
     * @return
     */
    public static Boolean isNotBlank(String string) {
        return !(string == null || EMPTY_STRING.equals(string.trim()));
    }

    public static void main(String[] args) {
        System.out.println(camel2Under("orderId"));
        System.out.println(uder2Camel("order_id"));
    }

    /**
     * 驼峰转下划线
     *
     * @param camelCaseName
     * @return
     */
    public static String camel2Under(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append(UNDERLINE);
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    /**
     * 下划线转驼峰
     *
     * @param underscoreName
     * @return
     */
    public static String uder2Camel(String underscoreName) {
        StringBuilder result = new StringBuilder();
        if (underscoreName != null && underscoreName.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < underscoreName.length(); i++) {
                char ch = underscoreName.charAt(i);
                if (UNDERLINE.charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    /**
     * 16位uuid
     *
     * @return
     */
    public static String UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
