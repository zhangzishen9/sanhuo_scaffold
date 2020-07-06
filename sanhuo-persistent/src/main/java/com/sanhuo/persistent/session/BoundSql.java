package com.sanhuo.persistent.session;

import com.sanhuo.persistent.mapping.ParameterMapping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * <p>
 * 绑定的SQL,是从SqlSource而来，将动态内容都处理完成得到的SQL语句字符串，其中包括?,还有绑定的参数
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/8:20:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoundSql {
    /**
     * 解析后的sql
     */
    private String sql;

    /**
     * 参数映射
     */
    private Map<Integer, ParameterMapping> params;
}
