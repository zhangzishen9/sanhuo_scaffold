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
@Data
public class SqlSource {
    /**
     * #{}解析为?后的sql
     */
    private String sql;
    /**
     * #{}里的参数列表
     */
    private Map<Integer,ParameterMapping> params;
}
