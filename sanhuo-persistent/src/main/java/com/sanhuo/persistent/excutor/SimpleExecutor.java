package com.sanhuo.persistent.excutor;

import com.sanhuo.persistent.cache.CacheKey;
import com.sanhuo.persistent.excutor.parameter.ParameterHandler;
import com.sanhuo.persistent.excutor.statement.PreStatementHandler;
import com.sanhuo.persistent.logging.Log;
import com.sanhuo.persistent.mapping.MappedStatement;
import com.sanhuo.persistent.session.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * SimpleExecutor
 *
 * @author sanhuo
 * @date 2020/2/23 0023 下午 17:26
 */
public class SimpleExecutor extends BaseExecutor {

    /**
     * 日志
     */
    private Log log;

    public SimpleExecutor(Configuration configuration, Connection connection, Log log) {
        super(configuration, connection);
        this.log = log;

    }

    @Override
    protected <E> List<E> doQuery(MappedStatement ms, RowBounds rowBounds, BoundSql boundSql, Object... params) throws SQLException {
        Configuration configuration = this.configuration;
        PreparedStatement pstmt = null;
        try {
            PreStatementHandler preStatementHandler =
                    new PreStatementHandler(ms, this.configuration, boundSql, rowBounds);
            pstmt = preStatementHandler.init(this.connection);
            //设置参数
            preStatementHandler.setParams(log, pstmt, params);
            //返回结果
            return preStatementHandler.getResult(pstmt);
        } catch (Exception e) {
            e.printStackTrace();
            //todo 处理
        }
        return null;
    }

}
