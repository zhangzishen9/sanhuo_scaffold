package com.sanhuo.persistent.builder;

import ch.qos.logback.classic.db.SQLBuilder;
import com.sanhuo.persistent.mapping.ParameterMapping;
import com.sanhuo.persistent.mapping.SqlSource;
import com.sanhuo.persistent.session.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 解析sql
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
    public SqlSource parse(String sql, List<ParameterMapping> parameterMappings) {
        ParameterMappingTokenHandler parse
                = new ParameterMappingTokenHandler(parameterMappings);
        return parse.parse(sql);
    }

    //参数映射记号处理器，静态内部类
    private static class ParameterMappingTokenHandler {
        /**
         * method的入参
         */
        private final Map<String, ParameterMapping> parameterMappingMap;
        /**
         * sql源码类
         */
        private final SqlSource sqlSource;

        public ParameterMappingTokenHandler(List<ParameterMapping> parameterMappings) {
            this.sqlSource = new SqlSource();
            this.parameterMappingMap = parameterMappings.stream().collect(
                    Collectors.toMap(parameterMapping -> parameterMapping.getName(), Function.identity()));
        }


        public SqlSource parse(String sql) {
            return parseSql(sql);
        }


        /**
         * 解析sql语句的#{}参数
         */
        private SqlSource parseSql(String sql) {
            StringBuilder builder = new StringBuilder();
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
                        builder.append(handleToken(content, index++));
                        offset = end + CLOSE_TOKEN.length();
                    }
                }
                start = sql.indexOf(OPEN_TOKEN, offset);
                if (offset < src.length) {
                    builder.append(src, offset, src.length - offset);
                }
            }
            String parsedSql = builder.toString();
            sqlSource.setSql(parsedSql);
            return this.sqlSource;
        }


        /**
         * 处理#{xxx} 这样的参数 ,并返回替代符号
         *
         * @param content
         * @return
         */
        private String handleToken(String content, int index) {
            this.parseParameter(content, index);
            return SUB_TOKEN;
        }


        /**
         * 解析参数 构建parameter并放进去sqlsource
         *
         * @param content
         * @param index
         */
        private void parseParameter(String content, int index) {
            if (this.parameterMappingMap.containsKey(content)) {
                Map<Integer, ParameterMapping> params
                        = sqlSource.getParams() == null ? new HashMap<>() : sqlSource.getParams();
                params.put(index, this.parameterMappingMap.get(content));
            }
        }
    }


}
