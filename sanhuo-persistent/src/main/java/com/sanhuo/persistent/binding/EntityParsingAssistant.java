package com.sanhuo.persistent.binding;

import com.sanhuo.commom.utils.StringUtil;
import com.sanhuo.persistent.binding.annotation.Column;
import com.sanhuo.persistent.binding.annotation.Identifier;
import com.sanhuo.persistent.binding.annotation.Table;
import com.sanhuo.persistent.binding.property.ColumnProperty;
import com.sanhuo.persistent.binding.property.TableProperty;
import com.sanhuo.persistent.reflection.Reflector;
import com.sanhuo.persistent.reflection.meta.MetaObject;
import com.sanhuo.persistent.session.Configuration;
import com.sanhuo.persistent.type.JdbcType;
import com.sanhuo.persistent.type.TypeHandler;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 实体解析助手类
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/23:13:13
 */
@Data
public class EntityParsingAssistant {

    private TableProperty tableProperty;

    private Class entity;

    private List<Field> fields;

    private Configuration configuration;

    /**
     * 是否已经解析过主键
     */

    public EntityParsingAssistant(TableProperty tableProperty, Class entity, Configuration configuration) {
        this.tableProperty = tableProperty;
        this.entity = entity;
        this.tableProperty.setEntity(entity);
        this.fields = Reflector.getClassField(entity);
        this.configuration = configuration;
    }

    /**
     * 解析实体
     *
     * @return
     */
    public TableProperty parse() {
        //解析表名
        this.parseTableName();
        //解析表字段
        this.parseFields();
        return tableProperty;
    }

    /**
     * 解析表名
     */
    private void parseTableName() {
        Table table = (Table) entity.getAnnotation(Table.class);
        String tableName;
        // @Table注解不为空和@Table.value不为空 以value的值为表名
        if (table != null && StringUtil.isNotBlank(table.value())) {
            tableName = table.value();
        } else {
            //类名驼峰转下横线 PersonInfo -> person_info 为表名
            tableName = StringUtil.uder2Camel(entity.getSimpleName());
        }
        this.tableProperty.setTableName(tableName);
    }

    /**
     * 解析字段
     */
    private void parseFields() {
        //实体的字段映射
        Map<String, ColumnProperty> columns = new HashMap<>(fields.size());
        fields.stream().forEach(field -> {
            String fieldName = field.getName();
            Class javaType = field.getType();
            ColumnProperty columnProperty = ColumnProperty.builder().fieldName(fieldName)
                    .field(field)
                    .javaType(javaType).build();
            columns.put(fieldName, columnProperty);
            //判断是否是主键字段,是的话进行相应处理
            this.parsePrimaryKey(columnProperty);
            //对字段的属性进行处理
            this.parseColumn(columnProperty);

        });
        this.tableProperty.setColumns(columns);
    }

    /**
     * 解析主键
     *
     * @param columnProperty
     */
    private void parsePrimaryKey(ColumnProperty columnProperty) {
        Identifier identifier = columnProperty.getField().getAnnotation(Identifier.class);
        if (identifier != null) {
            this.tableProperty.setPrimaryKeyColumn(columnProperty.getFieldName());
        }
        columnProperty.setPrimaryKey(identifier != null);
        //todo 主键增长方式
    }


    /**
     * 解析字段属性
     *
     * @param columnProperty
     */
    private void parseColumn(ColumnProperty columnProperty) {
        Field field = columnProperty.getField();
        Column column = field.getAnnotation(Column.class);
        if (column == null) {
            //todo 处理
        }
        //数据库列名
        String columnName = StringUtil.isNotBlank(column.value()) ? column.value() : StringUtil.camel2Under(columnProperty.getFieldName());
        //数据库列类型
        JdbcType jdbcType = column.type() == JdbcType.NULL ? this.configuration.getTypeParsingRegistry().getJdbcType(columnProperty.getJavaType()) : column.type();
        //类型转换器
        TypeHandler typeHandler
                = column.type() == JdbcType.NULL
                ? this.configuration.getTypeHandlerRegistry().getHandler(columnProperty.getJavaType())
                : this.configuration.getTypeHandlerRegistry().getHandler(columnProperty.getJavaType(), jdbcType);

        //长度
        Integer length = column.length();
        //是否为空
        Boolean notNull = column.notNull();
        //填充
        columnProperty.setColumnName(columnName);
        columnProperty.setTypeHandler(typeHandler);
        columnProperty.setJdbcType(jdbcType);
        columnProperty.setLength(length);
        columnProperty.setNotNull(notNull);
    }
}
