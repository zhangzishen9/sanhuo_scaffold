package com.sanhuo.persistent.datasource.pooled;

import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.*;

/**
 * 真正的connection的代理类
 *
 * @author sanhuo
 * @date 2020/2/29 0029 下午 14:31
 */
@Data
public class PooledConnection implements InvocationHandler {
    /**
     * 连接关闭的方法
     */
    private static final String CLOSE = "close";
    /**
     * 连接的字节码 用于代理
     */
    private static final Class<?>[] PROXY_CLASS = new Class[]{Connection.class};
    /**
     * 对应的数据源
     */
    private PooledDataSource dataSource;
    /**
     * 真正的连接
     */
    private Connection originConnection;
    /**
     * 代理的连接
     */
    private Connection proxyConnection;
    /**
     * 连接创建时间
     */
    private long createTime;
    /**
     * 最后一次被使用的时间
     */
    private long lastUserTime;
    /**
     * 从池里取出来的时间
     */
    private long checkoutTime;

    public long getCheckoutTime() {
        return System.currentTimeMillis() - checkoutTime;
    }

    /**
     * 通过数据库url username password 算出来的 hash值 表示连接所在的连接池
     */
    private int connectionCode;

    /**
     * 当前连接是否有效
     */
    private Boolean valid;

    public PooledConnection(Connection connection, PooledDataSource dataSource) {
        this.dataSource = dataSource;
        this.originConnection = connection;
        createTime = System.currentTimeMillis();
        lastUserTime = System.currentTimeMillis();
        this.valid = true;
        this.proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(), PROXY_CLASS, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //调用的方法名
        String methodName = method.getName();
        //判断是不是关闭的方法
        if (CLOSE.hashCode() == methodName.hashCode() && CLOSE.equals(methodName)) {
            dataSource.pushConnection(this);
            return null;
        } else {
            if (!Object.class.equals(method.getDeclaringClass())) {
                //其他方法调用之前要检查connection是否还是合法的
                checkConnection();
            }
            //其他的方法，则交给真正的connection去调用
            return method.invoke(originConnection, args);
        }
    }

    /**
     * 失效
     */
    public void invalidate() {
        this.valid = false;
    }

    /**
     * 判断连接是否有效
     *
     * @return
     */
    public boolean isValid() {
        return valid && originConnection != null && dataSource.pingConnection(this);
    }

    /**
     * 判断连接是否有效
     *
     * @throws SQLException
     */
    private void checkConnection() throws SQLException {
        if (!valid) {
            throw new SQLException("Error accessing PooledConnection. Connection is invalid.");
        }
    }

}
