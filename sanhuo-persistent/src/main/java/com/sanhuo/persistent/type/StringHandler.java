package com.sanhuo.persistent.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * 字符串类型转换
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/21:20:06
 */
public class StringHandler implements TypeHandler<String> {
    @Override
    public void setParameter(PreparedStatement ps, int i, String parameter) throws SQLException {
        //todo 判空
        ps.setString(i, parameter);
    }

    @Override
    public String getResult(ResultSet rs, String columnName) throws SQLException {
        //todo 判空 没有这个columnName怎么处理
        return rs.getString(columnName);
    }

    @Override
    public String getResult(ResultSet rs, int columnIndex) throws SQLException {
        //todo 判空
        return rs.getString(columnIndex);
    }

}
