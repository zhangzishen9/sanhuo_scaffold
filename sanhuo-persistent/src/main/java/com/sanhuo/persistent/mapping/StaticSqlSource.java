package com.sanhuo.persistent.mapping;

import com.sanhuo.persistent.builder.SqlSourceBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * <p>
 * 静态Sql
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/8:20:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaticSqlSource implements SqlSource {
    /**
     * #{}解析为?后的sql
     */
    private String sql;

    /**
     * #{}里的参数列表
     */
    private Map<String, ParameterMapping> params;
    /**
     * Sql参数解析器
     */
    private SqlSourceBuilder.ParameterMappingTokenHandler handler;


    @Override
    public BoundSql getBoundSql(Object parameterObject) {
        String newSql = handler.parse(this.sql, this.params);
        return new BoundSql(newSql, handler.getParams());
    }
}
