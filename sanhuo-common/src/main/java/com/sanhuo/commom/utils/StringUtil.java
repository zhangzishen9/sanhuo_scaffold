package com.sanhuo.commom.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
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
     * 判断输入的string是否为空
     *
     * @param string
     * @return
     */
    public static Boolean isBlank(String string) {
        return string == null || EMPTY_STRING.equals(string.trim());
    }

    public static Boolean isNotBlank(String string) {
        return !(string == null || EMPTY_STRING.equals(string.trim()));
    }

    public static String UUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
