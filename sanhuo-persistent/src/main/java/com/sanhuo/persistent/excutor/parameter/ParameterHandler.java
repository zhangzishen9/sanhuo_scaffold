package com.sanhuo.persistent.excutor.parameter;

import com.sanhuo.persistent.binding.property.ResultMapping;
import com.sanhuo.persistent.binding.property.TableProperty;
import com.sanhuo.persistent.mapping.MappedStatement;
import com.sanhuo.persistent.mapping.ParameterMapping;
import com.sanhuo.persistent.session.BoundSql;
import com.sanhuo.persistent.session.Configuration;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 参数处理器
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/13:14:42
 */
public class ParameterHandler {


    private BoundSql boundSql;

    private Configuration configuration;


    public ParameterHandler(BoundSql boundSql, Configuration configuration) {
        this.boundSql = boundSql;
        this.configuration = configuration;
    }

    public void setParams(PreparedStatement pstmt, Object... params) throws SQLException {
        Map<Integer, ParameterMapping> paramMappings = this.boundSql.getParams();
        for (Map.Entry<Integer, ParameterMapping> paramMapping : paramMappings.entrySet()) {
            paramMapping.getValue().getTypeHandler().setParameter(pstmt, paramMapping.getKey(), params[paramMapping.getKey() - 1]);

        }
    }
}
