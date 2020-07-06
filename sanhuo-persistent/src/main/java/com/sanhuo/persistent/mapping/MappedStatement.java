package com.sanhuo.persistent.mapping;

import com.sanhuo.persistent.binding.property.ResultMapping;
import com.sanhuo.persistent.binding.property.SqlType;
import com.sanhuo.persistent.session.BoundSql;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 一个该对象对应一条sql语句
 * </p>
 *
 * @author sanhuo
 * @createTime 2020/5/8:20:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MappedStatement {
    /**
     * 来自哪个Mapper的接口
     */
    private Class resource;
    /**
     * 节点的id属性加命名空间,如：com.sanhuo.example.dao.UserMapper.findById
     */
    private String id;
    /**
     * 对应的sql源码对象
     */
    private SqlSource sqlSource;

    /**
     * 结果映射
     */
    private ResultMapping resultMapping;

    /**
     * sql类型
     */
    private SqlType sqlType;

    //TODO 缓存相关

    public BoundSql getBoundSql(Object... params) {
        return this.sqlSource.getBoundSql(params);

    }
}
