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
public class BooleanHandler implements TypeHandler<Boolean> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Boolean parameter) throws SQLException {
        //todo 判空
        ps.setInt(i, parameter ? 1 : 0);
    }

    @Override
    public Boolean getResult(ResultSet rs, String columnName) throws SQLException {
        //todo 判空
        return rs.getInt(columnName) == 1;
    }

    @Override
    public Boolean getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getInt(columnIndex) == 1;
    }
}
