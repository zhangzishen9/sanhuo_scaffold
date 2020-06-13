package com.sanhuo.persistent.binding.property;

import com.sanhuo.persistent.type.TypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 结果映射
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/25:20:57
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultMapping {
    /**
     * 属性
     */
    private List<Result> columns;

    /**
     * 结果对应的bean
     */
    private Class<?> type;

    /**
     * 是否是列表
     */
    private Boolean isCollection;

    /**
     * List/Set
     */
    private Class collectionType;

    /**
     * 结果映射的每行
     */
    @Data
    @Builder
    @AllArgsConstructor
    public static class Result {
        /**
         * 对应的数据库字段
         */
        private String columnName;
        /**
         * 对应的类字段
         */
        private String fieldName;
        /**
         * 类型转换
         */
        private TypeHandler typeHandler;
        /**
         * 列索引 ,columnName和fieldName为空时才用到
         */
        private Integer columnIndex;

        public Result(TypeHandler typeHandler, Integer columnIndex) {
            this.typeHandler = typeHandler;
            this.columnIndex = columnIndex;
        }
    }

}
