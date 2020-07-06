package com.sanhuo.persistent.mapping;

import com.sanhuo.persistent.builder.SqlSourceBuilder;
import com.sanhuo.persistent.session.BoundSql;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <p>
 * 动态sql源码
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/6/8:21:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DynamicSqlSource implements SqlSource {

    /**
     * SqlProvider类
     */
    private Annotation providerClass;
    /**
     * #{}里的参数列表
     */
    private Map<String, ParameterMapping> params;
    /**
     * SQL参数解析器
     */
    private SqlSourceBuilder.ParameterMappingTokenHandler handler;

    @Override
    public BoundSql getBoundSql(Object... parameter) {
        return null;
    }
}
