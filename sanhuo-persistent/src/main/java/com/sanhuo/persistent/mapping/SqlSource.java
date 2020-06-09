package com.sanhuo.persistent.mapping;

import com.sanhuo.persistent.session.Configuration;
import lombok.Data;

import java.util.List;
import java.util.Map;

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
     * @param parameterObject
     * @return
     */
    BoundSql getBoundSql(Object parameterObject);
}
