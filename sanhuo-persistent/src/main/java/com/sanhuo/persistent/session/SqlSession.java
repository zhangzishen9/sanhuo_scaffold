package com.sanhuo.persistent.session;

import com.sanhuo.persistent.excutor.SimpleExecutor;
import com.sanhuo.persistent.mapping.MappedStatement;
import lombok.Getter;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用来执行SQL，获取映射器，管理事务
 *
 * @author sanhuo
 * @date 2020/3/1 0001 下午 14:39
 */
public class SqlSession {

    @Getter
    private Configuration configuration;

    private Connection connection;

    private Executor executor;

    public SqlSession(Configuration configuration) {
        this.configuration = configuration;
        try {
            this.connection = this.configuration.getDataSource().getConnection();
            this.executor = new SimpleExecutor(configuration, connection);
        } catch (SQLException e) {
            //todo 处理
            e.printStackTrace();
        }
    }


    public List execute(MappedStatement ms,RowBounds rowBounds,Object... params) throws SQLException {
        return this.executor.query(ms,rowBounds,params);
    }

}
