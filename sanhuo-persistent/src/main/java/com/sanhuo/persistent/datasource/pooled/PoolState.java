package com.sanhuo.persistent.datasource.pooled;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 池状态
 *
 * @author sanhuo
 * @date 2020/2/29 0029 下午 14:32
 */
public class PoolState {

    protected PooledDataSource dataSource;

    /**
     * 空闲的连接
     */

    protected final List<PooledConnection> idleConnections = new LinkedList<>();
    /**
     * 活动的连接
     */

    protected final List<PooledConnection> activeConnections = new ArrayList<>();
    /**
     * 请求连接的次数
     */
    protected long requestCount = 0;
    /**
     * 总请求时间
     */
    protected long accumulatedRequestTime = 0;
    /**
     * 连接从池里拿出来的总时间( 执行sql的时间?)
     */
    protected long accumulatedCheckoutTime = 0;
    /**
     * 过期的连接数
     */
    protected long claimedOverdueConnectionCount = 0;
    /**
     * 过期连接累计的从池里拿出来的总时间( 执行sql的时间?)
     */
    protected long accumulatedCheckoutTimeOfOverdueConnections = 0;
    /**
     * 总等待时间
     */
    protected long accumulatedWaitTime = 0;
    /**
     * 要等待的次数
     */
    protected long hadToWaitCount = 0;
    /**
     * 坏的连接次数
     */
    protected long badConnectionCount = 0;

    public PoolState(PooledDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
