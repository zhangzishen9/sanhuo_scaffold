package com.sanhuo.persistent.type;

import com.sanhuo.persistent.binding.annotation.Mapper;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.JDBCType;
import java.util.*;

/**
 * 类型处理器 处理javaType和JdbcType之间的转换
 */
public class TypeHandlerRegistry {
    /**
     * 一个java类型对应一个jdbc类型
     */
    private final static Map<Class<?>, TypeHandler<?>> JAVA_TYPE_HANDLER_MAP = new HashMap<>();
    /**
     * 一个java类型对应多个jdbc类型
     */
    private final static Map<Class<?>, Map<JdbcType, TypeHandler<?>>> JAVA_JDBC_TYPE_HANDLER_MAP = new HashMap<>();

    public TypeHandlerRegistry() {
        //String
        register(String.class, new StringHandler());
        register(String.class, JdbcType.VARCHAR, new StringHandler());
        //Integer
        register(Integer.class, new IntegerHandler());
        //float
        register(Float.class, new FloatHandler());
        //bigdecimal
        register(BigDecimal.class, new BigDecimalTypeHandle());
        //date
        register(Date.class, new DateHandler());
        //long
        register(Long.class, new LongHandler());
        //boolean
        register(Boolean.class, new BooleanHandler());
    }

    /**
     * 一个java类型对应一个jdbc类型的注册
     *
     * @param type
     * @param handler
     * @param <T>
     */
    public <T> void register(Class<T> type, TypeHandler<? extends T> handler) {
        this.JAVA_TYPE_HANDLER_MAP.put(type, handler);
    }

    /**
     * 一个java类型对应多个jdbc类型的注册
     * 例如String -> varchar / String -> longtext /String ->char
     *
     * @param type
     * @param jdbcType
     * @param handler
     * @param <T>
     */
    public <T> void register(Class<T> type, JdbcType jdbcType, TypeHandler<? extends T> handler) {
        Map<JdbcType, TypeHandler<?>> jdbcTypeTypeHandlerMap = this.JAVA_JDBC_TYPE_HANDLER_MAP.get(type);
        if (jdbcTypeTypeHandlerMap == null) {
            jdbcTypeTypeHandlerMap = new HashMap<>();
        }
        jdbcTypeTypeHandlerMap.put(jdbcType, handler);
        this.JAVA_JDBC_TYPE_HANDLER_MAP.put(type, jdbcTypeTypeHandlerMap);
    }


    public TypeHandler<?> getHandler(Class<?> type) {
        return this.JAVA_TYPE_HANDLER_MAP.get(type);
    }

    public TypeHandler<?> getHandler(Class<?> type, JdbcType jdbcType) {
        return this.JAVA_JDBC_TYPE_HANDLER_MAP.get(type).get(jdbcType);
    }
}
