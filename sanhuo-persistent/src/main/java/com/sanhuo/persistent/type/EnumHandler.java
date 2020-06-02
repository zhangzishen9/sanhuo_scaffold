package com.sanhuo.persistent.type;

import com.sanhuo.persistent.enums.DataSourceType;

import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * 枚举转换器
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/21:20:38
 */
public class EnumHandler<E extends Enum> implements TypeHandler<E> {

    private final E[] enums;

    public EnumHandler(Class<E> type) {
        //枚举什么都没有 或者尾空要怎么 处理
        this.enums = type.getEnumConstants();
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, E parameter) throws SQLException {
        ps.setInt(i, parameter.ordinal());
    }

    @Override
    public E getResult(ResultSet rs, String columnName) throws SQLException {
        //TODO 找不到报错处理
        int ordinal = rs.getInt(columnName);
        return enums[ordinal];
    }

    @Override
    public E getResult(ResultSet rs, int columnIndex) throws SQLException {
        //TODO 找不到报错处理
        int ordinal = rs.getInt(columnIndex);
        return enums[ordinal];
    }
}
