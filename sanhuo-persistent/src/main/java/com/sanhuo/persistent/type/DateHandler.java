package com.sanhuo.persistent.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * <p>
 * 日期类型转换
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/21:20:23
 */
public class DateHandler implements TypeHandler<Date> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Date parameter) throws SQLException {
        //todo 判空
        ps.setDate(i, new java.sql.Date(parameter.getTime()));
    }

    @Override
    public Date getResult(ResultSet rs, String columnName) throws SQLException {
        //todo 判空
        return new Date(rs.getDate(columnName).getTime());
    }

    @Override
    public Date getResult(ResultSet rs, int columnIndex) throws SQLException {
        //todo 判空
        return rs.getDate(columnIndex);
    }
}
