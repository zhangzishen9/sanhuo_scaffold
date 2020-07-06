package com.sanhuo.persistent.mapping;

import com.sanhuo.persistent.session.BoundSql;

/**
 * <p>
 * SQL源码
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/11:20:55
 */
public interface SqlSource {


    /**
     * 解析后的sql对象
     *
     * @return
     */
    BoundSql getBoundSql(Object... paramete);
}
