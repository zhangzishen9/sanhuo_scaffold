package com.sanhuo.persistent.datasource.pooled;


import com.sanhuo.persistent.datasource.unpooled.UnPooledDataSource;
import lombok.Data;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 池化数据源
 */
@Data
public class PooledDataSource implements DataSource {
    /**
     * 当前数据源
     */
    private UnPooledDataSource dataSource;

    /**
     * 最大使用连接数
     */
    protected Integer maxActiveConnections;

    /**
     * 最大空闲连接数
     */

    protected Integer maxIdleConnections;

    /**
     * 从池里取出的最大允许时间( 执行sql的时间?)
     */
    protected Integer maxCheckoutTime;

    /**
     * 等待时间
     */

    protected Integer poolTimeToWait;
    /**
     * 数据池
     */
    private final PoolState state = new PoolState(this);
    /**
     * 当前的数据源的hashcode
     */
    private int currentConnectionTypeCode;

    public PooledDataSource() {
        //初始化非池化数据源
        dataSource = new UnPooledDataSource();
        currentConnectionTypeCode = assembleConnectionTypeCode();
        maxActiveConnections = 10;
        maxIdleConnections = 5;
        maxCheckoutTime = 20000;
        poolTimeToWait = 2000;
    }

    /**
     * 从池中取出连接
     */
    private PooledConnection popConnection() throws SQLException {
        PooledConnection connection = null;
        //是否等待连接
        Boolean countedWait = false;
        //请求连接开始时间
        long currentTime = System.currentTimeMillis();

        int localBadConnectionCount = 0;
        //拿不到连接则不停尝试
        while (connection == null) {
            //从state里拿连接 所以要加锁 防止并发问题
            synchronized (state) {
                if (!state.idleConnections.isEmpty()) {
                    //有空闲的连接

                    //删除空闲列表的第一个(取出来)
                    connection = state.idleConnections.remove(0);

                } else {
                    //没有空闲的连接

                    if (state.activeConnections.size() < maxActiveConnections) {
                        //当前使用的连接的数量比最大使用连接数少的话,新增一个
                        connection = new PooledConnection(dataSource.getConnection(), this);
                    } else {
                        //当前使用数应该到了最大使用数了,则不能再新增

                        //取出当前使用最久的一个(第一个)
                        PooledConnection oldestActiveConnection = state.activeConnections.get(0);
                        long longestCheckoutTime = oldestActiveConnection.getCheckoutTime();
                        if (longestCheckoutTime > maxCheckoutTime) {
                            //存在时间过长,则将连接标记为过期

                            //过期连接数 +1
                            state.claimedOverdueConnectionCount++;
                            //过期连接存在时间 +
                            state.accumulatedCheckoutTimeOfOverdueConnections += longestCheckoutTime;
                            //总连接存在时间
                            state.accumulatedCheckoutTime += longestCheckoutTime;

                            //将该连接从使用连接中取出(删除这个最老的连接)(只是删除pooledConnection 并没有断开真实的connection)
                            //所以只是不让一个操作持续太久?说明本次操作需要回滚 失效
                            state.activeConnections.remove(oldestActiveConnection);

                            //如果不是自动提交,回滚一下
                            if (!oldestActiveConnection.getOriginConnection().getAutoCommit()) {
                                oldestActiveConnection.getOriginConnection().rollback();
                            }
                            //新建一个连接
                            connection = new PooledConnection(oldestActiveConnection.getOriginConnection(), this);
                            //旧代理连接失效
                            oldestActiveConnection.invalidate();
                        } else {
                            //如果这次操作的时间不够长,等待
                            try {
                                if (!countedWait) {
                                    //等待的次数 +1
                                    state.hadToWaitCount++;
                                    countedWait = true;
                                }
                                long waitTime = System.currentTimeMillis();
                                //wait 等待其他connection完成操作后唤醒
                                state.wait(poolTimeToWait);
                                //记录时间 +
                                state.accumulatedWaitTime += System.currentTimeMillis() - waitTime;

                            } catch (InterruptedException e) {
                                break;
                            }
                        }
                    }
                    // 拿到连接了
                    if (connection != null) {
                        if (connection.getValid()) {
                            //连接有效

                            //如果不是自动提交 回滚一次 防止上次操作的还在
                            if (!connection.getOriginConnection().getAutoCommit()) {
                                connection.getOriginConnection().rollback();
                            }
                            //设置所属数据源的hashcode
                            connection.setConnectionCode(assembleConnectionTypeCode());
                            //记录拿出来的时间
                            connection.setCheckoutTime(System.currentTimeMillis());
                            connection.setLastUserTime(System.currentTimeMillis());

                            //把当前的poolConnection放到正在使用的数组里
                            state.activeConnections.add(connection);
                            //请求连接次数 ++
                            state.requestCount++;
                            //总的请求连接时间
                            state.accumulatedRequestTime += System.currentTimeMillis() - currentTime;
                        }
                    } else {
                        //拿不到连接
                        //TODO 报错
                        //如果没拿到，统计信息：坏连接+1
                        state.badConnectionCount++;
                        localBadConnectionCount++;

                        //TODO 如果好几次都拿不到，就放弃了，抛出异常
                        if (localBadConnectionCount > (maxIdleConnections + 3)) {

                        }
                    }
                }
            }
        }

        if (connection == null) {
            //TODO 抛异常
        }
        return connection;
    }

    /**
     * 把连接放回池里
     */
    void pushConnection(PooledConnection connection) throws SQLException {
        //加锁
        synchronized (state) {
            //从活跃连接池里删除该连接
            state.activeConnections.remove(connection);
            //当前代理连接是否有效
            if (connection.getValid()) {
                //空闲的连接比较少
                if (state.idleConnections.size() < maxIdleConnections
                        && connection.getConnectionCode() == currentConnectionTypeCode) {
                    //记录checkout时间
                    state.accumulatedCheckoutTime += connection.getCheckoutTime();

                    //如果不是自动提交 回滚一次 防止上次操作的还在
                    if (!connection.getOriginConnection().getAutoCommit()) {
                        connection.getOriginConnection().rollback();
                    }

                    //new 一个connection 加入空闲池
                    PooledConnection newConnection = new PooledConnection(connection.getOriginConnection(), this);
                    state.idleConnections.add(newConnection);
                    newConnection.setCreateTime(connection.getCreateTime());
                    newConnection.setLastUserTime(connection.getLastUserTime());
                    //旧连接设置无效
                    connection.invalidate();
                    //通知其他线程可以抢夺connection
                    state.notify();
                } else {
                    //空闲池满了

                    //记录checkout时间
                    state.accumulatedCheckoutTime += connection.getCheckoutTime();

                    //如果不是自动提交 回滚一次 防止上次操作的还在
                    if (!connection.getOriginConnection().getAutoCommit()) {
                        connection.getOriginConnection().rollback();
                    }

                    //直接将connection关闭
                    connection.getOriginConnection().close();
                    //旧连接设置无效
                    connection.invalidate();
                }
            } else {
                state.badConnectionCount++;
            }
        }
    }

    protected Boolean pingConnection(PooledConnection connection) {
        Boolean result = true;
        try {
            //真是连接是否关闭 关闭了就是无效
            result = !connection.getOriginConnection().isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            result = false;
        }
        //TODO 侦测
        return result;

    }

    /**
     * 计算当前连接所属数据源的hashcode
     */
    private int assembleConnectionTypeCode() {
        return ("" + dataSource.getUrl() + dataSource.getUsername() + dataSource.getPassword()).hashCode();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return popConnection().getProxyConnection();
    }

    @Override
    public Connection getConnection(String username, String password) {
        return null;
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
