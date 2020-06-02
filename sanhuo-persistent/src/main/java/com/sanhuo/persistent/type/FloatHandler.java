package com.sanhuo.persistent.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * <p>
 * 浮点转换器
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/21:20:32
 */
public class FloatHandler implements TypeHandler<Float> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Float parameter) throws SQLException {
        ps.setFloat(i,parameter);
    }

    @Override
    public Float getResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getFloat(columnName);
    }

    @Override
    public Float getResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getFloat(columnIndex);
    }
}
