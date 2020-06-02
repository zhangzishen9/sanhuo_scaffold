package com.sanhuo.persistent.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * 类型转换器的父接口
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/13:20:54
 */
public interface TypeHandler<T> {

    //设置参数
    void setParameter(PreparedStatement ps, int i, T parameter) throws SQLException;

    //取得结果,供普通select用
    T getResult(ResultSet rs, String columnName) throws SQLException;

    //取得结果,供普通select用
    T getResult(ResultSet rs, int columnIndex) throws SQLException;


}
