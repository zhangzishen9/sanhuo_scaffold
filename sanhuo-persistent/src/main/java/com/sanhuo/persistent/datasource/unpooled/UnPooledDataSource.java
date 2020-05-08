package com.sanhuo.persistent.datasource.unpooled;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

/**
 * 非池化数据源,继承了sql的Datasource
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnPooledDataSource implements DataSource {

    /**
     * 当前配置id
     */
    protected String id;
    /**
     * 数据库驱动
     */
    protected String driver;
    /**
     * 用户名
     */
    protected String username;
    /**
     * 密码
     */
    protected String password;
    /**
     * 数据库连接
     */
    protected String url;

    /**
     * 是否自动提交(事务) 默认自动提交(不打开事务)
     */
    private Boolean autoCommit = true;
    /**
     * 事务级别(RR)
     */
    private final int transactionIsolation = Connection.TRANSACTION_REPEATABLE_READ;


    @Override
    public Connection getConnection() throws SQLException {
        return doConnection();
    }

    @Override
    public Connection getConnection(String username, String password) {
        return null;
    }

    private Connection doConnection() throws SQLException {
        initDriver();
        Connection connection = DriverManager.getConnection(url, username, password);
        configurationConnection(connection);
        return connection;
    }

    /**
     * 加载驱动
     */
    private void initDriver() {
        synchronized (this.getClass()) {
            try {
            /*
                initialize 这个参数传了 true，那么给定的类如果之前没有被初始化过，那么会被初始化,第三个参数为类加载器
                Class.forName 最终执行了Driver里的static方法快
                ->MySql驱动就自动向DriverManager类注册了，使得我们能通过DriverManager获得对MySqL的连接。
             */
                Class.forName(this.driver, true, this.getClass().getClassLoader());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 配置连接的信息
     */
    private void configurationConnection(Connection connection) throws SQLException {
        if (autoCommit != null && autoCommit != connection.getAutoCommit()) {
            connection.setAutoCommit(autoCommit);
        }
        //事务级别默认为RR
        connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
