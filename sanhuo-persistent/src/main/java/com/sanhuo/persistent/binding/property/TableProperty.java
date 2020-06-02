package com.sanhuo.persistent.binding.property;

import com.sanhuo.persistent.binding.EntityParsingAssistant;
import com.sanhuo.persistent.session.Configuration;
import lombok.Data;

import java.util.Map;

/**
 * <p>
 * 表属性
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/21:21:54
 */
@Data
public class TableProperty {
    /**
     * 实体class
     */
    private Class entity;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 主键名称(java类属性名)
     */
    private String primaryKeyColumn;
    /**
     * 对应的属性 java类属性名为key
     */
    private Map<String, ColumnProperty> columns;

    /**
     * 隐藏构造函数
     */
    private TableProperty() {
    }

    /**
     * 初始化
     *
     * @param entity
     * @return
     */
    public static TableProperty init(Class entity, Configuration configuration) {
        return new EntityParsingAssistant(new TableProperty(), entity, configuration).parse();
    }
}
