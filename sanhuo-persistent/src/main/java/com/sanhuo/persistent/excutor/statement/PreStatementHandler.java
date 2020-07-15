package com.sanhuo.persistent.excutor.statement;

import com.mysql.cj.protocol.Resultset;
import com.sanhuo.persistent.excutor.parameter.ParameterHandler;
import com.sanhuo.persistent.excutor.result.ResultSetHandler;
import com.sanhuo.persistent.logging.Log;
import com.sanhuo.persistent.mapping.MappedStatement;
import com.sanhuo.persistent.session.BoundSql;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.persistent.session.ResultHandler;
import com.sanhuo.persistent.session.RowBounds;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * <p>
 * 预语句处理器 这里只用pstmt 所以不用接口了 直接来一个类实现
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/17:21:02
 */
public class PreStatementHandler {

    private MappedStatement mappedStatement;

    private Configuration configuration;

    private BoundSql boundSql;

    private RowBounds rowBounds;

    private ParameterHandler parameterHandler;

    private ResultSetHandler resultSetHandler;

    public PreStatementHandler(MappedStatement mappedStatement, Configuration configuration, BoundSql boundSql, RowBounds rowBounds) {
        this.mappedStatement = mappedStatement;
        this.configuration = configuration;
        this.boundSql = boundSql;
        this.rowBounds = rowBounds;
        this.parameterHandler = new ParameterHandler(boundSql, configuration);
        this.resultSetHandler = new ResultSetHandler(mappedStatement);
    }

    /**
     * 生成
     *
     * @param connection
     * @return
     * @throws SQLException
     */
    public PreparedStatement init(Connection connection) throws SQLException {
        return connection.prepareStatement(boundSql.getSql());
    }

    /**
     * 设置sql执行参数
     *
     * @param pstmt
     * @param params
     * @throws SQLException
     */
    public void setParams(Log log, PreparedStatement pstmt, Object... params) throws SQLException {
        parameterHandler.setParams(log, pstmt, params);
    }


    public <E> List<E> getResult(PreparedStatement pstmt) throws SQLException {
        return resultSetHandler.getResultSet(pstmt);

    }
}
