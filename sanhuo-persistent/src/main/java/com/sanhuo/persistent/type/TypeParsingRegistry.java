package com.sanhuo.persistent.type;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * javaType <-> jdbcType默认转换
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/23:14:11
 */
public class TypeParsingRegistry {

    private static final Map<Class, JdbcType> typeMappedMap = new HashMap<>();

    public TypeParsingRegistry() {
        //todo 只有一些基本用到的 剩余的用到再加
        this.register(String.class, JdbcType.VARCHAR);
        this.register(Integer.class, JdbcType.INTEGER);
        this.register(Float.class, JdbcType.FLOAT);
        this.register(Double.class, JdbcType.DOUBLE);
        this.register(BigDecimal.class, JdbcType.DECIMAL);
        this.register(Date.class, JdbcType.DATE);
        this.register(Enum.class, JdbcType.TINYINT);
        this.register(Boolean.class, JdbcType.TINYINT);
    }

    private void register(Class javaType, JdbcType jdbcType) {
        this.typeMappedMap.put(javaType, jdbcType);
    }

    /**
     * 根据javatype获取默认对应的jdbctype;
     *
     * @param javaType
     * @return
     */
    public JdbcType getJdbcType(Class javaType) {
        return typeMappedMap.containsKey(javaType) ? typeMappedMap.get(javaType) : null;
    }
}
