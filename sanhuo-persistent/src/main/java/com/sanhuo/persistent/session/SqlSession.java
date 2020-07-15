package com.sanhuo.persistent.session;

import com.sanhuo.persistent.excutor.SimpleExecutor;
import com.sanhuo.persistent.logging.Log;
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

    /**
     * 数据库链接
     */
    private Connection connection;

    /**
     * 执行器
     */
    private Executor executor;


    /**
     * 日志
     *
     * @param configuration
     */
    private Log log;

    public SqlSession(Configuration configuration) {
        this.configuration = configuration;
        // 创建日志
        this.log = this.configuration.getLog(SqlSession.class);

        try {
            this.connection = this.configuration.getDataSource().getConnection();
            this.executor = new SimpleExecutor(configuration, connection, this.log);
        } catch (SQLException e) {
            //todo 处理
            e.printStackTrace();
        }
    }

    /**
     * 关闭SqlSession
     */
    public void close() throws SQLException {
        // 关闭数据库链接
        this.connection.close();
    }

    public List execute(MappedStatement ms, RowBounds rowBounds, Object... params) throws SQLException {
        return this.executor.query(ms, rowBounds, params);
    }

}
