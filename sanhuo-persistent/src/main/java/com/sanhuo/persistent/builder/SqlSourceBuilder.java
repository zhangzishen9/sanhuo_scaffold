package com.sanhuo.persistent.builder;

import ch.qos.logback.classic.db.SQLBuilder;
import com.sanhuo.persistent.mapping.DynamicSqlSource;
import com.sanhuo.persistent.mapping.ParameterMapping;
import com.sanhuo.persistent.mapping.SqlSource;
import com.sanhuo.persistent.mapping.StaticSqlSource;
import com.sanhuo.persistent.session.Configuration;
import lombok.Getter;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 解析sql的builder
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/11:20:44
 */
public class SqlSourceBuilder {

    private static final String OPEN_TOKEN = "#{";

    private static final String CLOSE_TOKEN = "}";

    private static final String SUB_TOKEN = "?";

    private final Configuration configuration;

    public SqlSourceBuilder(Configuration configuration) {
        this.configuration = configuration;

    }

    /**
     * 解析Sql
     *
     * @param sql
     */
    public SqlSource parse(String sql, List<ParameterMapping> parameterMappings, Annotation sqlProvider) {
        //参数的name为key
        Map<String, ParameterMapping> parameterMap = parameterMappings.stream()
                .collect(Collectors.toMap(ParameterMapping::getName, Function.identity()));
        ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
        //静态还是动态
        SqlSource sqlSource = sqlProvider == null ? new StaticSqlSource(sql, parameterMap, handler) :
                new DynamicSqlSource(sqlProvider, parameterMap, handler);
        return sqlSource;
    }

    //参数映射记号处理器，静态内部类
    public static class ParameterMappingTokenHandler {

        @Getter
        private final Map<Integer, ParameterMapping> params = new LinkedHashMap<>();

        public String parse(String sql, Map<String, ParameterMapping> parameterMap) {
            //最终的sql
            StringBuilder builder = new StringBuilder();
            //参数对应的位置
            if (sql != null && sql.length() > 0) {
                char[] src = sql.toCharArray();
                int offset = 0;
                int start = sql.indexOf(OPEN_TOKEN, offset);
                //这里是循环解析参数，参考GenericTokenParserTest,比如可以解析${first_name} ${initial} ${last_name} reporting.这样的字符串,里面有3个 ${}
                int index = 1;
                while (start > -1) {
                    int end = sql.indexOf(CLOSE_TOKEN, start);
                    if (end == -1) {
                        builder.append(src, offset, src.length - offset);
                        offset = src.length;
                    } else {
                        builder.append(src, offset, start - offset);
                        offset = start + OPEN_TOKEN.length();
                        String content = new String(src, offset, end - offset);
                        //得到一对大括号里的字符串后，调用handler.handleToken,比如替换变量这种功能
                        builder.append(handleToken(content, index++, parameterMap));
                        offset = end + CLOSE_TOKEN.length();
                    }
                    start = sql.indexOf(OPEN_TOKEN, offset);
                }
                if (offset < src.length) {
                    builder.append(src, offset, src.length - offset);
                }
            }
            return builder.toString();
        }


        /**
         * 处理#{xxx} 这样的参数 ,并返回替代符号
         *
         * @param content
         * @return
         */
        private String handleToken(String content, int index, Map<String, ParameterMapping> parameterMap) {
            this.parseParameter(content, index, parameterMap);
            return SUB_TOKEN;
        }


        /**
         * 解析参数 构建parameter并放进去sqlsource
         *
         * @param content
         * @param index
         */
        private void parseParameter(String content, int index, Map<String, ParameterMapping> parameterMap) {
            if (parameterMap.containsKey(content)) {
                this.params.put(index, parameterMap.get(content));
            }
        }
    }


}
