package com.sanhuo.app.http.invoke.type;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangzs
 * @description 类型处理器注册
 * @date 2022/10/9 10:24
 **/
public class TypeHandlerRegister {
    private static final Map<Class, TypeHandler> TYPE_HANDLER_REGIST_MAP = new ConcurrentHashMap<>();
    private static final InnerObjectHandler INNER_OBJECT_HANDLER = new InnerObjectHandler();

    static{
        TYPE_HANDLER_REGIST_MAP.put(String.class,new StringHandler());
        TYPE_HANDLER_REGIST_MAP.put(BigDecimal.class,new BigdecimalHandler());
        TYPE_HANDLER_REGIST_MAP.put(Date.class,new DateHandler());
        TYPE_HANDLER_REGIST_MAP.put(Byte.class,new StringHandler());
        TYPE_HANDLER_REGIST_MAP.put(Long.class,new LongHandler());
        TYPE_HANDLER_REGIST_MAP.put(Short.class,new ShortHandler());
        TYPE_HANDLER_REGIST_MAP.put(Double.class,new DoubleHandler());
        TYPE_HANDLER_REGIST_MAP.put(Boolean.class,new BooleanHandler());
        TYPE_HANDLER_REGIST_MAP.put(Character.class,new CharHandler());
        TYPE_HANDLER_REGIST_MAP.put(Integer.class,new IntegerHandler());
        TYPE_HANDLER_REGIST_MAP.put(Float.class,new FloatHandler());
    }

    public static TypeHandler get(Class target) {
        return TYPE_HANDLER_REGIST_MAP.getOrDefault(target,INNER_OBJECT_HANDLER);
    }
}
