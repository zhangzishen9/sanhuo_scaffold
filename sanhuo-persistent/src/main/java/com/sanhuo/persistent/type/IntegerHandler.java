package com.sanhuo.persistent.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * 整形转换
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/21:20:26
 */
public class IntegerHandler implements TypeHandler<Integer>{
    @Override
    public void setParameter(PreparedStatement ps, int i, Integer parameter) throws SQLException {
        //todo 判空
        ps.setInt(i,parameter);
    }

    @Override
    public Integer getResult(ResultSet rs, String columnName) throws SQLException {
        //todo 判空
        return rs.getInt(columnName);
    }

    @Override
    public Integer getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getInt(columnIndex);
    }
}
