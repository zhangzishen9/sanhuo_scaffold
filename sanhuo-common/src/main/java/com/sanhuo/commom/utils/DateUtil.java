package com.sanhuo.commom.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    public static final String WEEK_FIRST = "fistday_week";
    public static final String WEEK_LAST = "lastday_week";
    public static final String dateTime = "yyyy-MM-dd HH:mm:ss";
    public static final String date = "yyyy-MM-dd";

    public static String getNowString(String formatType) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.format(new Date());
    }

    public static Date getNowDate(String formatType) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatType);
        return sdf.parse(sdf.format(new Date()));
    }

    public static Date addDay(Integer dayNum) {
        Date date = new Date(); //取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, dayNum); //把日期往后增加一天,整数  往后推,负数往前移动
        return calendar.getTime();
    }

    /**
     * 获取今天
     *
     * @return
     * @throws ParseException
     */
    public static Date getToday() throws ParseException {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(sdf.format(date));
    }

    /**
     * 获取本周第一天和最后一天
     *
     * @return
     * @throws ParseException
     */
    public static Map<String, Date> getWeek() throws ParseException {
        Map<String, Date> map = new HashMap<>();
        String firstDay, lastDay;
        Calendar curStartCal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat parse = new SimpleDateFormat(dateTime);
        Calendar cal = (Calendar) curStartCal.clone();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        firstDay = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        lastDay = sdf.format(cal.getTime());
        map.put(WEEK_FIRST, parse.parse(firstDay + " 00:00:00"));
        map.put(WEEK_LAST, parse.parse(lastDay + " 23:59:59"));
        return map;
    }

}
